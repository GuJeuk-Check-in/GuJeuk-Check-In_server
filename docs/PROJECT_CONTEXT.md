# GuJeuk 프로젝트 현재 상황

> 최종 갱신: 2026-06-15 KST
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
- 운영자별 월간·연간 누계 방문 실적 통계
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

현재 기능 작업 브랜치 예시:

```text
feature/#73-month-hwp-file
```

GitHub 기본 브랜치:

```text
develop
```

주의:

- 권장 흐름은 `feature/* -> develop -> main`이다.
- `develop` push는 홈서버 스테이징 스택으로 자동 배포된다.
- `main` push는 홈서버 운영 스택으로 자동 배포된다.
- `develop -> main` 머지 전 스테이징 검증을 끝낸다.

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
/home/ubuntu/git/gujeuk-check-in-server-stag
/home/ubuntu/git/monitoring
```

Docker 서비스:

| 서비스 | 컨테이너 | 역할 |
|---|---|---|
| Spring Boot | `gujeuk-app` | API, 호스트 포트 8080 |
| MySQL | `gujeuk-mysql` | 운영 데이터베이스 |
| Redis | `gujeuk-redis` | JWT 토큰 저장 |

운영 데이터 volume 기본값:

```text
MySQL -> gujeuk-check-in-server_mysql_data
Redis -> gujeuk-check-in-server_redis_data
```

주의:

- 운영 `docker-compose.yml` 기본 volume 이름을 임의로 바꾸면 기존 운영 DB 대신 새 빈 volume으로 기동될 수 있다.
- DB 연결 장애처럼 보여도 실제로는 "다른 빈 MySQL volume"에 붙은 상황일 수 있으니 volume 이름부터 확인한다.

통합 모니터링은 별도 Compose 프로젝트 `gujeuk-monitoring`으로 실행한다.

| 서비스 | 컨테이너 | 역할 |
|---|---|---|
| Grafana | `monitoring-grafana` | 대시보드와 로그 조회, localhost 3000 |
| Prometheus | `monitoring-prometheus` | 메트릭과 경보 규칙 |
| Loki | `monitoring-loki` | Docker와 systemd 로그 저장 |
| Alloy | `monitoring-alloy` | Docker 및 journal 로그 수집 |
| node_exporter | `monitoring-node-exporter` | 호스트 CPU·메모리·디스크·네트워크 |
| cAdvisor | `monitoring-cadvisor` | 컨테이너별 자원 사용량 |
| blackbox_exporter | `monitoring-blackbox-exporter` | 로컬·공개 URL HTTP probe |
| home-metrics | `monitoring-home-metrics` | 배터리, AC, Compose 상태 |

외부 주소:

| 용도 | 주소 |
|---|---|
| 운영 API | `https://api.taisu.site` |
| 스테이징 API 라우트 | `https://api-stag.taisu.site` |
| Focus Mate | `https://focus.taisu.site` |
| GuJeuk Prototype | `https://prototype.taisu.site` |
| 통합 모니터링 | `https://monitor.taisu.site` |
| Cloudflare SSH | `ssh.taisu.site` |

Cloudflare ingress:

```text
api.taisu.site      -> http://localhost:8080
api-stag.taisu.site -> http://localhost:8081
focus.taisu.site    -> http://localhost:8787
prototype.taisu.site -> http://localhost:8788
monitor.taisu.site  -> http://localhost:3000
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

브랜치별 자동 배포:

```text
feature/* -> PR -> develop -> 스테이징 배포
develop   -> 검증 -> PR -> main -> 운영 배포
```

CI/CD 흐름:

1. GitHub-hosted runner에서 JDK 17로 `bootJar -x test` 실행
2. 생성된 JAR를 artifact로 업로드
3. GitHub-hosted runner에서 해당 JAR를 포함한 배포용 Docker 이미지 생성
4. 이미지를 artifact tar로 업로드
5. 홈서버 self-hosted runner가 artifact를 다운로드
6. 브랜치에 따라 운영 또는 스테이징 배포 디렉터리에 소스 `rsync`
7. 홈서버는 `docker load`로 이미지만 적재
8. `deploy-stack.sh`가 MySQL/Redis 준비 상태 확인 후 앱만 재기동
9. 로컬 API와 공개 API health check (`/public/organs`)
10. Discord에 한국어 성공·실패 메시지 전송

브랜치별 배포 대상:

| 브랜치 | 배포 경로 | 공개 주소 | 포트 |
|---|---|---|---|
| `develop` | `/home/ubuntu/git/gujeuk-check-in-server-stag` | `https://api-stag.taisu.site` | `8081` |
| `main` | `/home/ubuntu/git/gujeuk-check-in-server` | `https://api.taisu.site` | `8080` |

Self-hosted runner:

```text
경로: /home/ubuntu/actions-runner/gujeuk-check-in-server
label: gujeuk-home-server
```

중요:

- 이전 구조는 홈서버에서 직접 `docker compose build app`를 수행해 CPU와 메모리를 오래 점유했다.
- 그 결과 배포 중 홈서버가 느려지거나 API 응답이 불안정해질 수 있었다.
- 현재 구조는 빌드를 GitHub-hosted runner로 옮겨 홈서버는 이미지 적재와 앱 재기동만 담당한다.
- 앱 컨테이너를 교체하는 수초 동안은 짧은 연결 재시도가 발생할 수 있다.
- CI/CD에서 cloudflared를 재시작하면 GitHub Runner가 자식 프로세스를 정리하면서 `530`과 SSH 단절이 발생할 수 있다.
- 따라서 CI/CD는 앱만 배포하고 Cloudflare Tunnel 생명주기를 직접 제어하지 않는다.

## 7. 모니터링과 Discord

Grafana 통합 모니터링:

- 배포 경로: `/home/ubuntu/git/monitoring`
- 외부 주소: `https://monitor.taisu.site`
- 호스트 CPU, 메모리, 디스크, 네트워크, 배터리와 AC 상태
- 모든 Docker Compose 프로젝트와 컨테이너 상태
- 로컬·공개 URL의 HTTP 상태와 응답 시간
- Docker 로그와 systemd journal 검색
- Prometheus 경보 상태
- Grafana 외 Prometheus, Loki, Alloy 포트는 localhost에만 바인딩

기본 대시보드:

- `홈서버 전체 현황`
- `프로젝트와 컨테이너`
- `통합 로그와 컨테이너`

`홈서버 전체 현황`과 `통합 로그와 컨테이너`에는 모든 Docker 컨테이너의 실행 상태, Healthcheck, 재시작 횟수, CPU와 메모리 사용량이 표시된다. 로그 검색 기본값은 전체 로그를 의미하는 정규식 `.*`이며 raw 값으로 Loki에 전달한다.

홈서버 로컬 모니터는 다음을 검사한다.

- `gujeuk-app`
- `gujeuk-mysql`
- `gujeuk-redis`
- `http://localhost:8080/public/organs`
- `https://api.taisu.site/public/organs`
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
- Grafana도 같은 홈서버에서 실행되므로 완전한 외부 uptime monitor를 대체하지 않는다.
- 외부 장애 감지를 위해 UptimeRobot, Better Stack 또는 별도 클라우드 모니터가 필요하다.

## 8. 현재 라이브 상태

2026-06-14 KST 배포 후 실제 확인 결과:

```text
https://api.taisu.site/purpose/all -> HTTP 200
https://api.taisu.site/residence/all -> HTTP 200
GET /organ/statistics/visits         -> Access Token 인증 후 HTTP 200
통계 API 토큰 없음                   -> HTTP 403
통계 API 미래 연월                   -> HTTP 400
https://monitor.taisu.site/api/health -> HTTP 200
ssh gujeuk-home                    -> 정상 접속
gujeuk-app                         -> 실행 중, 재시작 0회
gujeuk-mysql                       -> healthy, 재시작 0회
gujeuk-redis                       -> healthy, 재시작 0회
```

월별 방문 통계 API:

```text
GET /organ/statistics/visits?year={year}&month={month}
Authorization: Bearer {accessToken}
```

- 인증된 운영자의 데이터만 조회한다.
- `monthly`는 선택 월, `cumulative`는 선택 연도 1월 1일부터 선택 월 말일까지 집계한다.
- 청소년은 `AGE_9_13`, `AGE_14_16`, `AGE_17_19`, `AGE_20_24`, 기타는 `BABY`, `ADULT`로 분류한다.
- 상세 요청·응답 JSON은 `apiDocument.md`를 기준으로 한다.

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
https://gujeuk-check-in-develop.pages.dev
https://prototype.taisu.site
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

통합 모니터링:

```bash
cd /home/ubuntu/git/monitoring
./scripts/verify.sh
docker compose ps
docker compose logs --tail=200 grafana prometheus loki alloy
```

## 15. 운영 프론트와 사용자 API 상태

2026-06-10 KST 실제 확인 결과:

- `https://api.taisu.site/purpose/all`은 HTTP 200이며 운영 방문 목적 12개를 반환한다.
- `https://api.taisu.site/residence/all`은 HTTP 200이다.
- `https://gujeuk.dsmhs.kr` Origin의 CORS preflight와 API 응답 헤더는 정상이다.
- 빈 회원가입 요청과 잘못된 성별 enum 요청은 HTTP 400으로 응답하도록 수정·배포했다.
- 존재하지 않는 사용자 로그인은 HTTP 404로 정상 응답한다.
- 같은 사용자가 같은 분에 다시 체크인하면 DB 제약조건의 500 대신 HTTP 409를 반환하도록 수정했다.
- `https://gujeuk.dsmhs.kr` 프론트 주소는 점검 시 HTTP 503으로 정상 서비스되지 않았다.
- Cloudflare Pages 배포본은 `/` 라우트가 없어 흰 화면이며 관리자 화면만 포함한다.
- 현재 프론트 저장소에는 `/user/sign-up`, `/user/login` 사용자 체크인 화면과 API 호출 구현이 없다.

따라서 방문 목적 API 장애와 프론트 서비스 장애를 구분해야 한다. 현재 방문 목적 API는 정상이고, 사용자 회원가입·로그인 절차가 브라우저에서 제공되지 않는 주원인은 잘못되거나 불완전한 프론트 배포다.

## 16. 다음 우선 작업

1. 사용자 체크인 전용 프론트의 회원가입·로그인 화면과 API 연동 복구
2. `https://gujeuk.dsmhs.kr` 배포 대상과 DNS·호스팅 상태 복구
3. 프론트 루트 경로에 명시적인 시작 화면 또는 redirect 추가
4. Git에 추적된 운영 secret 제거와 credential rotation
5. `main`과 `develop` CI/CD 정책 통합
6. 홈서버 외부 uptime monitor 도입
7. 정기 DB 백업과 외부 저장소 복제
8. CI에서 테스트를 다시 활성화할 수 있는 환경 구성

## 17. 문서 갱신 규칙

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
