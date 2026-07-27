# AWS EC2 접속 가이드

> 대상 인스턴스: `i-05e2a254f15e59ead` (`gujeuk-aws-prod-stag`), 서울 리전 `ap-northeast-2`
> Public IP: `3.37.79.125`

## 요약

이 인스턴스는 22번 포트(SSH)를 보안 그룹에서 특정 관리자 IP만 허용하도록 제한하고 있다. 게다가 **학교 네트워크처럼 아웃바운드 22번 포트 자체를 막는 네트워크**에서는 보안 그룹을 아무리 정확히 설정해도 접속이 안 된다 (2026-07-21 확인: AWS 보안 그룹·EC2 내부 방화벽·sshd 전부 정상인데도 특정 네트워크에서 `ssh` 자체가 타임아웃).

그래서 접속 방법은 두 가지다.

| 방법 | 되는 조건 | 추천도 |
|---|---|---|
| **SSM Session Manager** | AWS 계정 로그인만 되면 어떤 네트워크에서도 됨 (22번 포트 필요 없음) | 기본으로 사용 |
| 직접 SSH | 22번 포트가 막혀있지 않은 네트워크에서만 됨 (예: 휴대폰 핫스팟, 일부 가정용 네트워크) | 보조 수단 |

학교망처럼 22번이 막힌 곳에서는 **SSM만 된다.** 핫스팟처럼 22번이 열려있는 곳에서는 둘 다 된다.

---

## 방법 1: SSM Session Manager (기본, 어디서든 됨)

### 사전 준비 (최초 1회, PC마다 한 번)

AWS CLI가 이미 설치되어 있는지 먼저 확인:

```bash
aws --version
```

`command not found`가 뜨면 (즉 AWS CLI 자체가 없으면) 먼저 설치:

```bash
curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"
sudo installer -pkg AWSCLIV2.pkg -target /
```

그 다음 session-manager-plugin 설치:

```bash
brew install --cask session-manager-plugin
```

### 접속

```bash
aws login
aws ssm start-session --target i-05e2a254f15e59ead --region ap-northeast-2
```

`aws login`은 브라우저가 뜨면서 AWS 로그인을 요구한다. 로그인이 되어 있으면(세션 유효기간 내) 바로 스킵되고 붙는다.

`$` 프롬프트가 뜨면 접속 성공.

### 세션 진입 후

SSM 세션은 기본적으로 `ssm-user`(또는 root) 권한으로 들어간다. 배포 작업을 하려면 `ubuntu` 계정으로 전환:

```bash
sudo su - ubuntu
cd ~/gujeuk-aws/prod
```

### 종료

```bash
exit
```

### 필요 권한 (다른 사람이 접속하려면)

이 방식은 **AWS IAM 권한**만 있으면 네트워크와 무관하게 항상 된다. 접속하려는 사람은:
1. 이 AWS 계정(`927362559699`)에 로그인할 수 있어야 한다 (`aws login`으로 브라우저 인증).
2. 해당 계정에 `ssm:StartSession`, `ssm:TerminateSession`, `ssm:ResumeSession` 권한이 있어야 한다.

현재는 별도 IAM 사용자가 분리되어 있지 않고 루트 계정 임시 세션을 공유해서 쓰는 상태다. 팀원이 늘어나면 IAM 사용자 또는 IAM Identity Center로 전환해서 개인별 계정을 발급하는 게 맞다.

---

## 방법 2: 직접 SSH (22번 포트가 열려있는 네트워크에서만)

### 사전 준비

1. 개인키 필요: `gujeuk-aws-deploy` 키 페어에 대응하는 개인키 파일 (기본적으로 `~/.ssh/id_ed25519`). **이 파일이 없으면 이 방법 자체가 불가능하다** — 새로 만들 수 없고, 원래 키를 가진 사람에게 파일을 안전하게 전달받아야 한다.
2. AWS CLI 로그인:
   ```bash
   aws login
   ```
3. 보안 그룹에 본인의 현재 공인 IP를 등록 (기존에 등록된 IP는 자동으로 지우고 새로 등록):
   ```bash
   export AWS_PAGER=""

   MY_IP=$(curl -s https://api.ipify.org)
   OLD_CIDRS=$(aws ec2 describe-security-groups --group-ids sg-0c16acf4997f79b3f --region ap-northeast-2 \
     --query "SecurityGroups[0].IpPermissions[?FromPort==\`22\`].IpRanges[].CidrIp" --output text)
   for cidr in $OLD_CIDRS; do
     aws ec2 revoke-security-group-ingress --group-id sg-0c16acf4997f79b3f --protocol tcp --port 22 --cidr "$cidr" --region ap-northeast-2
   done
   aws ec2 authorize-security-group-ingress \
     --group-id sg-0c16acf4997f79b3f \
     --region ap-northeast-2 \
     --ip-permissions "IpProtocol=tcp,FromPort=22,ToPort=22,IpRanges=[{CidrIp=$MY_IP/32,Description=Admin-Mac-current-IP}]"
   ```
   주의: 이 보안 그룹 규칙은 **IP 하나만** 허용하는 구조라, 다른 사람이 등록하면 이전 사람 IP는 지워진다. 동시에 여러 명이 SSH로 붙을 수 없다 (SSM은 이 제약이 없음).

### 접속

```bash
ssh ubuntu@3.37.79.125
```

### 안 될 때

- `ssh: connect ... port 22: Operation timed out` → 현재 네트워크가 22번 포트를 막고 있을 가능성이 크다. 핫스팟으로 바꿔서 재시도. 계속 안 되면 방법 1(SSM)을 쓴다.
- 보안 그룹 IP가 실제 현재 IP와 다른지 확인:
  ```bash
  curl -s https://api.ipify.org
  aws ec2 describe-security-groups --group-ids sg-0c16acf4997f79b3f --region ap-northeast-2 \
    --query "SecurityGroups[0].IpPermissions[?FromPort==\`22\`]"
  ```

---

## 트러블슈팅 기록 (2026-07-21)

- 학교 네트워크에서 `ssh ubuntu@3.37.79.125` 계속 타임아웃.
- 확인 결과 보안 그룹, EC2 내부 `iptables`/`ufw`, `sshd`(socket activation으로 22번 정상 리스닝 중) 전부 문제없었음.
- 즉 AWS/EC2 측 설정 문제가 아니라 **로컬 네트워크가 아웃바운드 22번 포트를 차단**하는 것으로 결론.
- SSM Session Manager로는 동일 네트워크에서 즉시 접속 성공 (`session-manager-plugin` 설치 후).
- 결론: 네트워크를 가리지 않는 SSM을 기본 접속 수단으로 채택.
