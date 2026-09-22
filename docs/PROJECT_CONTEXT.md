# GuJeuk 프로젝트 현재 상황

> 최종 갱신 및 EC2 확인: 2026-09-22 15:33 KST
> 확인한 작업 브랜치: `chore/#119-ChoreUnuseDelete` (`322b00d` 기준)
> 목적: 현재 코드·운영 구성·배포 시 주의할 차이를 파악하기 위한 기준 문서

## 1. 먼저 확인할 현재 상태

- 현재 운영 대상은 **AWS EC2 `3.37.79.125`**다. 체크인 운영·스테이징, 게임, 모니터링이 같은 EC2에서 실행된다.
- 홈서버, Oracle HAProxy, Cloudflare Tunnel, Primary/Replica 승격 절차는 과거 구성이다. 현재 AWS 운영 절차로 사용하지 않는다. 과거 기록은 이 파일의 Git 이력에서 확인한다.
- `develop` push는 EC2 스테이징, `main` push는 EC2 운영으로 자동 배포된다. PR은 Java 컴파일만 수행한다.
- 저장소의 최신 코드, EC2에 저장된 파일, 실행 중 컨테이너의 이미지·환경변수는 서로 다를 수 있다. 아래의 **구성 차이**를 먼저 읽는다.
- 아래 정보는 확인 시점의 스냅샷이다. 배포·DB·네트워크 작업 전에 실제 상태를 다시 확인한다.

## 2. 서비스와 코드

시설 출입 관리용 Spring Boot 백엔드다. 운영자별로 이용자·방문 목적·거주지·출입 기록을 관리한다.

| 항목 | 현재 코드 기준 |
|---|---|
| 기술 | Java 17, Spring Boot 3.5.6, Gradle, MySQL 8, Redis 7.2 |
| 주요 구성 | Spring Security/JWT, JPA, QueryDSL, Flyway, Apache POI |
| 패키지 | `src/main/java/com/example/gujeuck_server` |
| 도메인 | `organ`, `user`, `purpose`, `residence`, `log`, `common` |
| 공통 기능 | readiness 확인, 체크인 퍼널 이벤트 기록·조회 |
| 게임 | 별도 `GuJeuk-Game-FE` 저장소의 `server/`에서 관리 |

주요 API:

- `/organ/**`: 기관 로그인, 토큰 재발급, 회원 관리, Excel, 방문 통계·이용 현황.
- `/user/**`: 이용자 등록·체크인 및 간편 등록·출입 기록.
- `/purpose/**`, `/residence/**`: 방문 목적·거주지 관리와 순서 변경.
- `/log/**`: 방문 기록 생성·조회·수정·삭제.
- `/common/analytics/check-in-funnel`: 퍼널 이벤트 등록. `/events`는 이벤트 조회. 현재 `SecurityConfig`에서 `ORGAN` 역할을 요구한다.
- `GET /common/health/ready`: DB 연결 유효성 검사. 정상은 200, 실패는 503. Redis나 모든 업무 기능의 정상 여부를 보장하는 검사는 아니다.

인증 여부는 `global/config/SecurityConfig.java`를 기준으로 판단한다. `/user/**`, `/organ/create`, `/organ/login`, `/purpose/all`, `/residence/all`, `/common/health/ready` 등은 공개 경로다. `/organ/excel/user`는 현재 코드에서 인증이 필요하다. 모든 API가 로그인 제외 전부 인증 필요하다고 단정하지 않는다.

API의 최종 기준은 컨트롤러·DTO·SecurityConfig다. `apiDocument.md` 등 설명 문서와 차이가 있으면 코드를 확인한다. 예전 `/public/organs`를 배포 health check로 사용하지 않는다.

## 3. 접속 주소와 경로

| 용도 | 주소/경로 |
|---|---|
| 운영 API | `https://aws-api.oijwef098234.com` |
| 스테이징 API | `https://aws-stag.oijwef098234.com` |
| 게임 API | `https://game-api.oijwef098234.com` |
| 운영 프론트 | `https://gujeuk-check-in-fe.pages.dev` |
| 스테이징 프론트 | `https://gujeuk-check-in-develop.pages.dev` |
| 관리자 화면 | 운영 프론트의 `/organ/login` |
| 이용자 화면 | 운영 프론트의 `/check-in` |
| SSH | `ssh ubuntu@3.37.79.125` |
| 체크인 운영 | `/home/ubuntu/gujeuk-aws/prod` |
| 체크인 스테이징 | `/home/ubuntu/gujeuk-aws/stag` |
| Caddy | `/home/ubuntu/gujeuk-aws/proxy` |
| 게임 | `/home/ubuntu/gujuck-game` |
| 모니터링 | `/home/ubuntu/monitoring` |

현재 호스트명은 `ip-172-31-2-113`이다. SSH 직접 접속을 확인했다. 프론트 주소는 현재 사용하는 주소이며, 이번 확인에서 브라우저의 모든 화면을 테스트한 것은 아니다.

추가 접속 참고 문서는 [AWS_EC2_ACCESS.md](AWS_EC2_ACCESS.md)다. 해당 문서의 IAM·SSM·보안 그룹 설명은 과거 확인 기록도 포함하므로 현재 권한과 네트워크 상태를 확인한다. SSH가 안 된다는 이유만으로 다른 관리자의 보안 그룹 허용 규칙을 지우지 않는다.

## 4. EC2 컨테이너·네트워크·저장소

확인 시 실행 중인 컨테이너는 11개다.

| 컨테이너 | 역할 | 호스트 → 컨테이너 포트 | Docker 네트워크 |
|---|---|---|---|
| `gujeuk-aws-caddy` | HTTPS 및 도메인별 프록시 | 80:80, 443:443 TCP/UDP | `proxy_default` |
| `gujeuk-app-prod` | 체크인 운영 | 8080:8080 | `gujeuk-aws-prod_default` |
| `gujeuk-mysql-prod` | 운영 MySQL 8 | 호스트 게시 없음, 내부 3306 | 운영 네트워크 |
| `gujeuk-redis-prod` | 운영 Redis 7.2 | 호스트 게시 없음, 내부 6379 | 운영 네트워크 |
| `gujeuk-app-stag` | 체크인 스테이징 | 8081:8080 | `gujeuk-aws-stag_default` |
| `gujeuk-mysql-stag` | 스테이징 MySQL 8 | 호스트 게시 없음, 내부 3306 | 스테이징 네트워크 |
| `gujeuk-redis-stag` | 스테이징 Redis 7.2 | 호스트 게시 없음, 내부 6379 | 스테이징 네트워크 |
| `gujuck-game-app` | 게임 서버 | 8095:8090 | 운영 네트워크 |
| `monitor-bot` | 상태 확인·지표 제공·Discord 연동 | 127.0.0.1:8090 → 8090 | `monitoring` + 운영 네트워크 |
| `prometheus` | 지표 수집 | 127.0.0.1:9090 → 9090 | `monitoring` |
| `grafana` | 대시보드·경보 | 127.0.0.1:3000 → 3000 | `monitoring` |

데이터 연결:

- 운영 앱: `mysql:3306/gujeuk_prod`, `redis:6379`.
- 스테이징 앱: `mysql:3306/gujeuk_stag`, `redis:6379`.
- 두 환경의 `mysql`, `redis`는 이름이 같아도 네트워크가 달라 각각의 컨테이너에 연결된다.
- 게임 앱: 운영 MySQL 컨테이너 안의 별도 DB `gujuck_game`. 체크인 운영 MySQL을 중지·삭제하면 게임에도 영향이 있다.
- 운영 볼륨: `gujeuk-aws-prod-mysql-data`, `gujeuk-aws-prod-redis-data`.
- 스테이징 볼륨: `gujeuk-aws-stag-mysql-data`, `gujeuk-aws-stag-redis-data`.
- Caddy 볼륨: `gujeuk-aws-caddy-data`, `gujeuk-aws-caddy-config`.
- 모니터링 볼륨: `monitoring_prometheus_data`, `monitoring_grafana_data`.

Compose 프로젝트/볼륨 이름을 임의로 변경하면 기존 DB 대신 새 빈 볼륨으로 기동될 수 있다. 앱 포트 8080·8081·8095는 전체 인터페이스에 게시되어 있으나 외부 직접 접근 가능 여부는 보안 그룹·방화벽 확인이 별도로 필요하다.

## 5. Cloudflare와 Caddy

공개 API DNS 및 응답 헤더에서 Cloudflare 경유를 확인했다. EC2의 Caddy는 도메인에 따라 호스트 포트로 전달한다. 운영·스테이징 사이의 부하 분산이나 자동 장애 전환 구성이 아니다.

| 요청 도메인 | Caddy upstream |
|---|---|
| `aws-api.oijwef098234.com` | `host.docker.internal:8080` |
| `aws-stag.oijwef098234.com` | `host.docker.internal:8081` |
| `game-api.oijwef098234.com` | `host.docker.internal:8095` |

- 저장소: `ops/aws/Caddyfile`, `ops/aws/docker-compose.proxy.yml`.
- 서버: `/home/ubuntu/gujeuk-aws/proxy/Caddyfile` → 컨테이너 `/etc/caddy/Caddyfile`에 마운트.
- `host.docker.internal`은 Compose의 `host-gateway` 설정으로 EC2 호스트를 가리킨다.
- Caddy가 원본 HTTPS를 처리한다. Cloudflare 관리 콘솔의 SSL 모드와 원본 연결 프로토콜은 이번에 확인하지 않았다.

**확인된 차이:** 현재 브랜치의 `Caddyfile`에서는 `api.taisu.site`, `api-stag.taisu.site`가 제거됐다. 하지만 EC2 파일과 실행 중인 Caddy 설정에는 두 도메인이 각각 운영·스테이징의 별칭으로 남아 있다. 이는 taisu로 다시 전달하는 설정이 아니다. 체크인 앱 CI/CD는 Caddyfile을 복사하거나 Caddy를 reload하지 않으므로, 앱 배포만으로 이 변경이 반영되지는 않는다.

## 6. CI/CD

기준: `.github/workflows/ci-cd.yml`.

| 이벤트 | 수행 작업 |
|---|---|
| `main`/`develop` 대상 PR | GitHub-hosted runner에서 `./gradlew compileJava` |
| `develop` push | JAR·이미지 빌드 → GHCR → EC2 스테이징 배포 |
| `main` push | JAR·이미지 빌드 → GHCR → EC2 운영 배포 |
| `workflow_dispatch` | 현재 조건상 JAR 빌드·artifact 업로드만 수행. 이미지 생성·배포 job은 push만 허용 |

배포 순서:

1. JDK 17로 `./gradlew bootJar -x test` 실행.
2. Dockerfile로 이미지 생성, GHCR에 `prod-${GITHUB_SHA}` 또는 `stag-${GITHUB_SHA}` 태그로 push.
3. SCP로 `ops/aws/docker-compose.aws.yml`을 EC2의 해당 환경 `docker-compose.yml`로 복사.
4. SSH로 배포 디렉터리에 접속. 기존 `.env`를 사용하고 `APP_IMAGE`는 이번 이미지 주소로 export.
5. `COMPOSE_PROJECT_NAME=gujeuk-aws-prod` 또는 `gujeuk-aws-stag` 설정.
6. `docker compose --env-file .env pull app`, 이어서 `docker compose --env-file .env up -d` 실행.
7. 로컬 및 공개 `/purpose/all`의 정상 응답을 각각 최대 40회, 3초 간격으로 확인. 요청 소요 시간은 별도다.
8. 로컬 확인 이후 사용하지 않는 dangling 이미지를 정리한다.

주의:

- 서버에서 앱 이미지를 빌드하거나 홈서버 self-hosted runner로 배포하는 구조가 아니다.
- `up -d`는 앱만 지정하지 않으므로 Compose 변경에 따라 다른 서비스도 영향을 받을 수 있다.
- 단일 앱 컨테이너 교체 방식이며, 무중단 배포나 자동 롤백은 구성되어 있지 않다.
- `.env`를 저장소에서 복사하는 단계는 없다. 서버 파일과 실행 컨테이너 환경변수를 구분한다.
- Caddy·게임·모니터링은 이 앱 배포 workflow로 자동 배포되지 않는다. `ops/aws/docker-compose.aws.yml`만 복사한다.
- push에서 `docs/**`, `**/*.md`, `monitor-bot/**`, `ops/monitoring/**`, `.github/ISSUE_TEMPLATE/**`만 바뀐 경우 workflow가 제외된다. PR은 이 경로 제외 조건을 적용하지 않는다.
- 현재 CI에 테스트 실행 단계와 Discord 배포 알림 단계는 없다. `src/test` 디렉터리도 현재 체크아웃에 없다.

## 7. 실행 이미지와 현재 코드의 차이

2026-09-22 15:33 KST 확인:

| 환경 | 실행 이미지 |
|---|---|
| 운영 | `ghcr.io/gujeuk-check-in/gujeuk-check-in-server:prod-bf732f25c2560c29d4b6ba50b4ecdd0e3ede87dd` |
| 스테이징 | `ghcr.io/gujeuk-check-in/gujeuk-check-in-server:stag-05ee8eb555f0551621f7f453d5decb82917faede` |
| 게임 | `ghcr.io/gujeuk-check-in/gujeuk-game-fe-server:ad07088` |

운영과 스테이징의 이미지 버전은 다르다. 현재 작업 브랜치에는 #113 리팩터링, #117 Caddy 변경, #119 미사용 구성 정리 이력이 있지만 이를 모두 운영 반영 완료로 설명하지 않는다. 현재 브랜치의 #119 변경에는 `.square` 관련 구성 삭제가 포함된다.

## 8. 환경변수와 CORS

현재 코드의 CORS 변수는 다음 세 개다. 여러 origin은 쉼표로 구분하며 코드가 공백과 후행 `/`를 정리한다.

```dotenv
PROD_BASE_URL=https://gujeuk-check-in-fe.pages.dev
STAG_BASE_URL=https://gujeuk-check-in-develop.pages.dev
TEST_URL=http://localhost:5173,http://localhost:5174
```

- EC2 prod/stag `.env`는 위 주소로 정리돼 있고 `VERCEL_URL`은 없다. prod `TEST_URL`에는 쉼표 뒤 공백이 있으며 현재 코드에서는 trim 처리된다.
- 실행 중인 stag 컨테이너는 정리된 세 변수를 사용하고 `VERCEL_URL`이 없다.
- **실행 중인 prod 컨테이너에는 아직 예전 CORS 주소 목록과 `VERCEL_URL`이 남아 있다.** 이전 이미지로 기동한 컨테이너의 환경변수는 `.env` 수정만으로 바뀌지 않는다.
- 현재 코드와 정리된 환경변수를 함께 적용해야 한다. 이전 이미지가 요구하는 변수를 없앤 상태로 이전 이미지만 재기동하지 않는다.

앱 실행에는 DB 연결 정보, JWT secret, Redis 연결 정보가 필요하다. AWS Compose에는 이미지·컨테이너 이름·호스트 포트·볼륨 이름도 필요하다. `ops/aws/prod.env.example`, `stag.env.example`은 형식 참고용이며 실제 `.env`가 아니다.

`${VAR:?message}`는 필수값이 없거나 비어 있으면 Compose 실행을 실패시키는 문법이다. `APP_IMAGE`는 배포 쉘에서 전달할 수 있으므로 서버 `.env`만으로 실행 설정을 판단하지 않는다.

## 9. 모니터링

- 현재 구성은 `monitor-bot`, Prometheus, Grafana다. 과거 Loki·Alloy·node_exporter·cAdvisor 기반 홈서버 구성을 현재 EC2 구성으로 설명하지 않는다.
- monitor-bot은 Docker 소켓을 마운트하며 운영 네트워크와 모니터링 네트워크에 연결된다.
- 현재 소스의 기본 감시 대상은 운영 앱·MySQL·Redis와 Caddy다. 스테이징은 감시 대상에서 제외돼 있다.
- EC2 monitor-bot 환경변수의 운영 health URL은 `http://gujeuk-app-prod:8080/common/health/ready`다.
- Prometheus는 `monitor-bot:8090/actuator/prometheus`와 자신의 지표를 15초마다 수집한다.
- Grafana 데이터 소스는 `http://prometheus:9090`이다. Discord contact point와 경보 규칙이 있다.
- monitor-bot의 Discord 토큰/Webhook 설정 존재를 확인했으며 실제 알림 발송 테스트는 하지 않았다.
- Prometheus의 `alertmanagers`는 빈 목록이다. 별도 Alertmanager 컨테이너는 실행 중이지 않다.
- 이 구성은 운영과 같은 EC2에 있으므로 EC2 전체 장애 시 모니터링도 영향을 받는다.
- 호스트의 3000·8090·9090은 loopback에만 바인딩돼 있다. 필요하면 SSH 포워딩으로 접근한다.

```bash
ssh -N -L 13000:127.0.0.1:3000 ubuntu@3.37.79.125
# 로컬 브라우저에서 http://localhost:13000
```

## 10. DB와 배포 안전 기준

- 현재 `application.yml`은 Flyway 활성화, `baseline-version: 6`, `validate-on-migrate: false`, Hibernate `ddl-auto: update`를 함께 사용한다. 앱 기동이 DB 변경을 일으킬 수 있다.
- 마이그레이션 파일은 V1~V13이다. V13은 과거 체크인 DB의 펫 관련 테이블을 삭제하는 SQL이다. 현재 게임 DB 공유 구조와 혼동하지 말고 실행 대상 DB와 적용 이력을 확인한다.
- 과거 문서의 V14 시간 변환/backfill 파일은 현재 브랜치에 없다.
- 이번 갱신에서는 DB 내용·건수·Flyway 적용 이력·복제 상태를 조회하지 않았다. 과거 dump 복원 건수는 현재 수치로 사용하지 않는다.
- DB 변경·복원 전에 대상 환경·DB·볼륨을 확인하고 백업한다. 복원 파이프에서는 `docker compose exec -T`를 사용한다.
- 운영 데이터 삭제는 조건과 건수를 먼저 확인하고 사용자 승인 범위를 따른다. `docker compose down -v`처럼 볼륨을 삭제하는 명령을 정리 작업에 사용하지 않는다.
- Caddy는 운영·스테이징·게임이 함께 사용한다. 한 서비스 작업 때문에 공통 프록시를 임의로 중지하지 않는다.
- 홈서버의 Wi-Fi 변경, RTC 절전, DB 승격, cloudflared 재시작 명령을 AWS에 적용하지 않는다. `AGENTS.md`의 관련 장비별 지침은 과거 홈서버 작업 시의 안전 기록이다.
- `.env`, 토큰, 비밀번호, SSH 키, Webhook, DB dump를 출력·문서화·커밋하지 않는다. 컨테이너 전체 Env 대신 필요한 비민감 항목만 확인한다.
- 현재 `git ls-files backups` 결과는 비어 있고 `.gitignore`가 `backups/`, `*.env`, dump를 제외한다. 예전 문서의 “백업 secret 파일이 현재 추적 중”이라는 설명은 현재 체크아웃에 해당하지 않는다. 과거 Git 이력 정리와 자격증명 교체 완료 여부는 별도 확인이 필요하다.

## 11. 실제 확인 결과와 자주 쓰는 조회 명령

2026-09-22 15:33 KST에 다음 8개 GET 요청은 모두 HTTP 200이었다.

| 대상 | `/purpose/all` | `/common/health/ready` |
|---|---|---|
| EC2 `localhost:8080` | 200 | 200 |
| EC2 `localhost:8081` | 200 | 200 |
| `https://aws-api.oijwef098234.com` | 200 | 200 |
| `https://aws-stag.oijwef098234.com` | 200 | 200 |

이 결과는 전체 업무 기능, CORS, 인증, Excel, 게임의 통합 테스트를 의미하지 않는다. MySQL/Redis 컨테이너 4개는 Docker 상태에서 healthy였다.

```bash
# 로컬에서 실행
ssh ubuntu@3.37.79.125
curl -s -o /dev/null -w '%{http_code}\n' https://aws-api.oijwef098234.com/common/health/ready
curl -s -o /dev/null -w '%{http_code}\n' https://aws-stag.oijwef098234.com/common/health/ready

# EC2에서 실행: 상태와 실제 이미지 확인
docker ps --format 'table {{.Names}}\t{{.Image}}\t{{.Status}}\t{{.Ports}}'
docker inspect gujeuk-app-prod --format '{{.Config.Image}}'
docker inspect gujeuk-app-stag --format '{{.Config.Image}}'
```

로그가 필요하면 `docker logs --tail 100 <컨테이너명>`으로 범위를 제한하고, 개인정보·자격증명을 대화나 문서로 옮기지 않는다. Compose 명령은 해당 환경 디렉터리 및 배포 시 사용한 이미지·프로젝트 변수를 확인한 뒤 사용한다.

## 12. 후속 작업과 갱신 규칙

확인된 차이를 해소할 때 별도로 검토할 사항:

1. 운영 이미지와 현재 코드/CORS 환경변수의 차이를 운영 배포 전에 검토한다.
2. 저장소의 Caddy 도메인 제거를 서버에 반영할지는 기존 도메인 사용 여부를 확인하고 결정한다. 앱 배포가 Caddy를 갱신한다고 가정하지 않는다.
3. 실제 정기 백업, 외부 저장, 복구 검증 및 외부 장애 감시 상태는 별도 확인한다. 구성돼 있다고 단정하지 않는다.
4. 과거 자격증명 노출 이력에 대한 정리·교체 여부는 별도 확인한다.

인프라·URL·Compose·CI/CD·환경변수·DB 변경 시 이 문서를 갱신한다. **확인 날짜, 현재 코드, 실제 런타임, 미확인 사항을 구분**하고 과거 작업 이력을 현재 상태 아래 누적하지 않는다. 문서만 수정한 작업에서는 앱·DB·프록시를 재시작하거나 배포하지 않는다.
