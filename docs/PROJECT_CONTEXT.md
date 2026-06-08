# GuJeuk 프로젝트 현재 상황

> 최종 갱신: 2026-06-08 KST
> 목적: 새로운 Codex 대화에서도 현재 서비스·배포·운영 상황을 빠르게 파악하기 위한 기준 문서

## 1. 문서 사용 방법

- 이 문서는 **현재 상태와 앞으로의 판단 기준**을 요약한다.
- 상세 구축 이력과 장애 대응 방법은 `docs/HOME_SERVER_OPERATIONS.md`를 참고한다.
- 라이브 서버 상태처럼 변할 수 있는 정보는 문서만 믿지 말고 실제로 확인한다.
- 라이브 확인 결과가 문서와 다르면 실제 상태를 우선하고 이 문서를 갱신한다.

## 2. 서비스 개요

GuJeuk-Check-In Server는 시설 출입 관리용 Spring Boot 백엔드다.

주요 기능:

- 운영자 계정과 JWT 인증
- 이용자 등록·조회·수정
- 방문 목적과 거주지 분류
- 출입 로그 생성·조회·수정
- 관리자 Excel 내보내기
- 운영자별 데이터 분리를 적용한 멀티 테넌트 구조

기술 스택:

- Java 17
- Spring Boot 3.5.6
- Gradle
- MySQL 8
- Redis 7.2
- Spring Security + JWT
- QueryDSL
- Docker / Docker Compose

## 3. 저장소와 브랜치

저장소:

```text
GuJeuk-Check-in/GuJeuk-Check-In_server
```

로컬 작업 경로:

```text
/Users/bagtaesu/Desktop/git/GuJeuk-Check-In_server
```

현재 인프라 작업 브랜치:

```text
main
```

GitHub 기본 브랜치:

```text
develop
```

주의:

- `main`과 `develop`의 CI/CD 구성이 서로 다르다.
- 최신 홈서버 운영 자동화는 주로 `main`에 있다.
- `develop`에는 Docker Hub, EC2 운영 배포, 홈서버 스테이징 배포 구조가 별도로 남아 있다.
- 브랜치 정책을 통합하기 전에는 한 브랜치의 설정을 다른 브랜치에도 당연히 적용된 것으로 가정하지 않는다.

## 4. 운영 인프라

홈서버:

```text
Samsung 550XED
Ubuntu Server
사용자: ubuntu
```

홈서버 배포 경로:

```text
/home/ubuntu/git/gujeuk-check-in-server
```

Docker 서비스:

| 서비스 | 컨테이너 | 역할 |
|---|---|---|
| Spring Boot | `gujeuk-app` | API, 호스트 포트 8080 |
| MySQL | `gujeuk-mysql` | 운영 데이터베이스 |
| Redis | `gujeuk-redis` | JWT 토큰 저장 |

외부 주소:

| 용도 | 주소 |
|---|---|
| 운영 API | `https://api.taisu.site` |
| 스테이징 API 라우트 | `https://api-stag.taisu.site` |
| Cloudflare SSH | `ssh.taisu.site` |

Cloudflare ingress:

```text
api.taisu.site      -> http://localhost:8080
api-stag.taisu.site -> http://localhost:8081
ssh.taisu.site      -> ssh://localhost:22
```

HTTPS는 Nginx·Certbot이 아니라 Cloudflare에서 처리한다.

## 5. 원격 접속

Mac의 `~/.ssh/config`에 다음 alias가 구성되어 있다.

```sshconfig
Host gujeuk-home
  HostName ssh.taisu.site
  User ubuntu
  ProxyCommand cloudflared access ssh --hostname %h
```

접속 명령:

```bash
ssh gujeuk-home
```

주의:

- `gujeuk-home`은 Mac에서 사용하는 alias다.
- 홈서버 내부에서 `ssh gujeuk-home`을 실행하는 용도가 아니다.
- 서버 전원, 인터넷 또는 cloudflared가 꺼지면 이 경로로 접속할 수 없다.

## 6. CI/CD

`main` push 기준 흐름:

1. GitHub-hosted runner에서 JDK 17 설정
2. `./gradlew clean build -x test`
3. 홈서버 self-hosted runner가 checkout
4. `/home/ubuntu/git/gujeuk-check-in-server`에 `rsync`
5. 서버의 `.env`, 백업, import 파일 보존
6. `docker compose build app`
7. `docker compose up -d app`
8. 로컬 API와 공개 API health check
9. Discord에 한국어 성공·실패 메시지 전송

Self-hosted runner:

```text
경로: /home/ubuntu/actions-runner/gujeuk-check-in-server
label: gujeuk-home-server
```

중요:

- 배포 중 앱이 교체되면서 몇 초간 `502`가 발생할 수 있다.
- CI/CD에서 cloudflared를 재시작하면 GitHub Runner가 자식 프로세스를 정리하면서 `530`과 SSH 단절이 발생할 수 있다.
- 따라서 CI/CD는 앱만 배포하고 Cloudflare Tunnel 생명주기를 직접 제어하지 않는다.

## 7. 모니터링과 Discord

홈서버 로컬 모니터는 다음을 검사한다.

- `gujeuk-app`
- `gujeuk-mysql`
- `gujeuk-redis`
- `http://localhost:8080/purpose/all`
- `https://api.taisu.site/purpose/all`
- 배터리 잔량과 충전 상태

Discord 알림:

- 서버 부팅
- 서버 종료
- API 장애와 복구
- 배터리 10% 이하
- CI 빌드 성공·실패
- 홈서버 배포 성공·실패

한계:

- 홈서버 자체가 꺼지거나 인터넷이 끊기면 로컬 모니터도 알림을 보낼 수 없다.
- 외부 장애 감지를 위해 UptimeRobot, Better Stack 또는 별도 클라우드 모니터가 필요하다.

## 8. 현재 라이브 상태

2026-06-08 KST 확인 결과:

```text
https://api.taisu.site/purpose/all -> HTTP 200
ssh gujeuk-home                    -> 정상 접속
운영·스테이징 Docker 컨테이너      -> 모두 실행 중
```

RTC 예약 복귀 수정 결과:

- 기존 `rtcwake -m off`는 RTC 알람은 설정됐지만 S5 완전 종료에서 펌웨어가 깨우지 못했다.
- 커널은 `rtc_cmos: RTC can wake from S4`로 보고하며 S5 기상을 보장하지 않는다.
- `rtcwake -m mem` + `deep`은 예약 시각에 기상했지만 정상 resume 대신 콜드 부팅이 발생했다.
- 최종적으로 `rtcwake -m freeze`를 적용했다.
- 60초 실제 테스트에서 Boot ID가 유지됐고 커널 로그에 `PM: suspend entry (s2idle)`와 `PM: suspend exit`가 기록됐다.
- 공개 API는 복귀 후 약 29초 안에 HTTP 200으로 정상화됐다.

현재 동작:

- `shutdown`에 `0`보다 큰 시간을 입력하면 완전 종료가 아니라 저전력 `freeze` 절전에 들어간다.
- 지정 시간이 지나면 같은 부팅 세션으로 자동 복귀한다.
- `shutdown`에 `0`을 입력하면 완전히 종료되며 자동 복귀하지 않는다.
- 절전 중에는 API와 SSH가 중단되고 복귀 후 Tunnel 재연결까지 수십 초가 걸릴 수 있다.
- 예약 절전은 `nohup` 분리 작업으로 실행되어 SSH가 끊겨도 유지된다.
- 복귀 후 local/public API를 최대 5분간 확인한 뒤 Discord에 한국어 복귀 완료 알림을 전송한다.

이 상태는 변할 수 있으므로 다음 작업 전 반드시 다시 확인한다.

```bash
curl -I https://api.taisu.site/purpose/all
ssh gujeuk-home
```

## 9. 반드시 지킬 운영 안전 규칙

### Wi-Fi 변경 금지

커스텀 `wifi` 명령은 Netplan 충돌과 네트워크 단절을 발생시켰다.

현재 정책:

- `wifi`, `/wifi`, `gujeuk-wifi-admin`으로 운영 네트워크를 변경하지 않는다.
- 현재 SSID 확인만 기본 명령으로 수행한다.

```bash
iw dev wlo1 link | grep SSID
networkctl status wlo1 --no-pager
```

네트워크 변경이 꼭 필요하면:

- 서버 물리 콘솔에서 작업
- 기존 Netplan 백업
- 복구 경로 확보
- Cloudflare SSH만 믿고 원격 변경하지 않기

### RTC 예약 복귀

다음 방식은 사용하지 않는다.

```bash
rtcwake -m off
rtcwake -m mem
```

현재 정책:

- S5 완전 종료 후 RTC 자동 부팅은 이 장비에서 지원되지 않는다.
- `deep` 절전은 콜드 부팅을 일으켰으므로 사용하지 않는다.
- 예약 복귀는 설치된 `freeze` 모드 명령만 사용한다.
- 장시간 완전 종료가 필요하면 자동 복귀를 기대하지 않는다.
- 완전 종료 후 자동 부팅이 필요하면 BIOS AC Power Recovery, 유선 Wake-on-LAN 또는 별도 하드웨어를 검토한다.

```bash
shutdown
# Hours에 0보다 큰 값: freeze 절전 후 예약 복귀
# Hours에 0: 완전 종료, 자동 복귀 없음
```

### Cloudflare Tunnel

- 배포 workflow에서 cloudflared를 시작·종료하지 않는다.
- `502`는 앱 origin 문제, `530/1033`은 Tunnel connector 문제로 구분한다.
- `000`은 HTTP 응답을 받지 못한 네트워크·DNS·timeout 계열 문제로 본다.

### 데이터베이스

- dump 복원 전 반드시 현재 DB를 백업한다.
- 복원 파이프에서는 `docker compose exec -T`를 사용한다.
- 사용자 또는 로그 삭제 전 대상 조건과 건수를 먼저 조회한다.
- 운영 데이터 삭제는 사용자의 명시적 확인 없이 실행하지 않는다.

## 10. 데이터베이스 상태 이력

최종으로 적용한 dump:

```text
projectsilmoo_gujeuk_prod_synced_20260603_153552.sql.zip
```

복원 직후 확인한 건수:

| 테이블 | 건수 |
|---|---:|
| `admin` | 1 |
| `organ` | 5 |
| `purpose` | 12 |
| `residence` | 13 |
| `user` | 533 |
| `log` | 3293 |

이 수치는 복원 직후의 이력이며 현재 데이터 건수로 단정하지 않는다.

## 11. CORS

환경 변수 기반 허용 origin:

- `PROD_BASE_URL`
- `STAG_BASE_URL`
- `VERCEL_URL`
- `TEST_URL`

추가 요청으로 허용한 origin:

```text
http://localhost:5174
https://gujeuk-check-in-fe.pages.dev
```

origin은 쉼표로 여러 개를 전달할 수 있고 후행 `/`는 코드에서 제거한다.

## 12. 보안 긴급 사항

다음 파일은 Git에 추적되고 있으며 실제 운영 secret 형태의 값을 포함한다.

```text
backups/prod-2026-06-02/gujeuk-check-in-server.prod.env
```

Codex 작업 규칙:

- 이 파일 내용을 출력하지 않는다.
- secret 값을 대화, 로그, 문서, 커밋 메시지에 노출하지 않는다.
- 제거 작업 전 사용자와 이력 재작성 범위를 합의한다.

필요 조치:

1. 파일을 Git 추적에서 제거
2. `.gitignore`에 백업 `.env` 패턴 추가
3. Git 이력에서 secret 제거 검토
4. JWT secret 교체
5. MySQL 비밀번호 교체
6. Discord webhook 재발급

## 13. 서버와 저장소의 구성 드리프트

저장소의 `ops/home-server`에 없는 서버 전용 스크립트가 존재한다.

예:

- `cloudflared-supervisor.sh`
- `discord-notify`
- `monitor-gujeuk-api`
- `notify-server-started`
- `server-test`
- `server-battery`
- Runner watchdog 스크립트

따라서:

- 저장소 파일만으로 현재 서버를 완전히 재현할 수 없다.
- 인프라 작업 전 서버의 실제 파일과 cron을 확인한다.
- 새 운영 스크립트는 가능하면 저장소 `ops/home-server`에 함께 반영한다.

## 14. 자주 사용하는 확인 명령

외부 API:

```bash
curl -I https://api.taisu.site/purpose/all
```

서버 접속:

```bash
ssh gujeuk-home
```

서버 상태:

```bash
/home/ubuntu/bin/health
```

Docker:

```bash
cd /home/ubuntu/git/gujeuk-check-in-server
docker compose ps
docker compose logs --tail=200 app
```

Cloudflare:

```bash
pgrep -af cloudflared
tail -n 100 /home/ubuntu/.cloudflared/cloudflared.log
tail -n 100 /home/ubuntu/.cloudflared/watchdog.log
```

## 15. 다음 우선 작업

1. 홈서버를 물리적으로 켠 뒤 API·SSH·Docker 상태 재확인
2. Git에 추적된 운영 secret 제거와 credential rotation
3. `main`과 `develop` CI/CD 정책 통합
4. 홈서버 외부 uptime monitor 도입
5. 정기 DB 백업과 외부 저장소 복제
6. 서버 전용 스크립트를 저장소로 이전
7. 실패한 Wi-Fi·RTC 기능을 운영 명령에서 제거하거나 명확히 비활성화
8. CI에서 테스트를 다시 활성화할 수 있는 환경 구성

## 16. 문서 갱신 규칙

다음 변경이 발생하면 이 문서를 함께 갱신한다.

- 운영 URL 변경
- 서버 경로 변경
- Docker 서비스 추가·삭제
- 브랜치·CI/CD 정책 변경
- Cloudflare route 변경
- 새로운 장애 원인과 해결책 확인
- 사용 금지 기능의 재검증
- DB dump 복원
- credential rotation

상세 명령과 장애 이력은 `docs/HOME_SERVER_OPERATIONS.md`에 기록하고, 이 문서에는 현재 판단에 필요한 핵심만 유지한다.
