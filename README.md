# GuJeuk Check-In
### 구즉 청소년 문화의 집 방문 관리 서비스

> 방문 등록부터 월별 실적 정리까지, 청소년 문화의 집의 운영을 돕습니다.

## 모노레포 개발 구조 (#135)

이 저장소는 기관별 앱을 독립된 Gradle 모듈로 관리합니다. 아래 기존 기능·실행 설명은 구즉에 해당합니다.

```text
apps/
├── gujeuk-api/     # 기존 구즉 코드·설정·마이그레이션·Dockerfile
└── beopdong-api/   # 법동 독립 실행 앱·로컬 MySQL 연결 설정
shared/common/     # 빈 공통 라이브러리, 앱 의존 연결 없음
ops/gujeuk/        # 구즉 배포·기존 EC2/Caddy 설정
ops/beopdong/      # 법동 운영·스테이징 App·MySQL 추가 설정
```

```bash
# 전체 빌드
./gradlew clean build

# 구즉 개별 빌드·실행 (기존 환경변수 필요)
./gradlew :apps:gujeuk-api:bootJar
./gradlew :apps:gujeuk-api:bootRun

# 법동 개별 빌드·실행 (법동 local env 필요)
./gradlew :apps:beopdong-api:build
./gradlew :apps:beopdong-api:bootRun

# 구즉 이미지: 반드시 저장소 루트에서 실행
# 실행 JAR만 Docker 컨텍스트에 포함
docker build -f apps/gujeuk-api/Dockerfile -t gujeuk-check-in-server:local .
```

구즉 JAR은 `apps/gujeuk-api/build/libs/gujeuk-api.jar`에 생성됩니다. 루트의 `bootJar`·`bootRun`은 구즉 작업으로 연결됩니다. 두 앱은 서로 의존하지 않으며, 공통 코드는 실제 공유 필요가 생길 때만 추출합니다.

법동 로컬 실행은 저장소 밖 `~/env/법동_local.env`를 사용합니다. 연결 대상은 `BeopDong`이고 기본 설정에서 스키마를 자동 변경하지 않습니다. 자세한 실행·테스트 방법은 [법동 모듈 안내](apps/beopdong-api/README.md)를 참고합니다.

기존 구즉 CI/CD는 새 모듈 경로에서 구즉만 패키징·배포합니다. 법동 전용 경로만 변경한 push는 구즉 배포를 실행하지 않습니다. 법동도 develop/main push에서 각각 스테이징/운영 App·MySQL을 배포하도록 설정했습니다. 각 환경의 기존 구즉 Compose에 합치고 Redis를 공유하며, 최초 서버 env·메모리 확보는 별도로 필요합니다. 실제 운영 서버·DB 설정은 이번 초기 구성으로 변경하지 않습니다.

## 운영 문서

- [현재 프로젝트 상황 및 Codex 작업 기준](docs/PROJECT_CONTEXT.md)

## 프로젝트 소개

**GuJeuk Check-In은 구즉 청소년 문화의 집에서 사용하는 방문자 체크인 및 시설 이용 관리 서비스입니다.** 이 저장소는 서비스의 백엔드를 관리합니다.

청소년 문화의 집은 청소년들이 놀이, 학습, 동아리 활동 등을 위해 방문하는 공간입니다. 시설에서는 방문 목적과 이용 인원을 기록하고, 이를 월별 운영 실적으로 정리해야 합니다.

이 프로젝트는 **방문자가 남긴 기록을 담당자의 실적 관리까지 연결하기 위해** 시작했습니다. 방문자는 현장의 태블릿으로 체크인하고, 담당자는 관리자 웹에서 회원과 이용기록을 관리하며 통계와 엑셀 자료를 활용합니다.

기관 방문과 담당자·이용자의 피드백을 바탕으로 요구사항을 정리했으며, 실제 운영 과정에서 발견한 불편과 오류를 개선하고 있습니다.

## 해결하려는 문제

- **반복되는 방문 정보 입력**
  기존 회원 정보를 활용해 재방문 시 입력 부담을 줄입니다.

- **번거로운 이용기록 관리**
  방문자가 등록한 기록을 한 눈에 확인 및 조회가 가능하며 추가 및 수정도 가능합니다.

- **월말 실적 정리의 부담**
  누적된 방문기록을 이용 인원과 연령군·성별 통계로 정리하고, 엑셀로 제공합니다.

## 주요 기능

- **방문자 체크인**: 신규 회원 등록 및 기존 회원 방문 기록
- **회원 관리**: 회원 목록·상세 조회 및 정보 수정
- **시설 이용기록 관리**: 월별 조회, 수동 추가, 수정·삭제
- **실적 집계**: 당월·연간 누계 이용 인원과 연령군·성별 통계
- **엑셀 다운로드**: 월별 이용기록 및 회원 명단 추출
- **현장 설정**: 방문 목적·거주지 항목과 표시 순서 관리
- **관리자 인증**: 기관 계정 로그인 및 인증 관리
- **장애 기록 재전송**: 장애 중 임시 저장한 체크인 기록의 재전송 처리

## 기술 스택

| 구분 | 기술 |
| --- | --- |
| 언어 | Java 17 |
| 프레임워크 | Spring Boot 3.5.6 |
| 인증·인가 | Spring Security, JWT |
| 데이터 접근 | Spring Data JPA, QueryDSL 5 |
| 데이터베이스 | MySQL 8, Redis 7.2 |
| DB 변경 관리 | Flyway |
| 엑셀 생성 | Apache POI |
| 배포 | AWS EC2, Docker Compose, Caddy |
| CI/CD | GitHub Actions, GitHub Container Registry |
| 모니터링 설정 | Datadog APM 및 컨테이너 service/env 라벨 |

실행 중인 모니터링 구성과 확인 시점은 [운영 문서](docs/PROJECT_CONTEXT.md)를 참고합니다.

## 설치 및 실행

### 사전 준비

- JDK 17
- Docker 및 Docker Compose

아래 과정은 애플리케이션, MySQL, Redis를 로컬 컨테이너로 실행하는 방법입니다.

### 1. 저장소 복제

```bash
git clone https://github.com/GuJeuk-Check-in/GuJeuk-Check-In_server.git
cd GuJeuk-Check-In_server
```

### 2. 환경변수 설정

예시 파일을 복사합니다.

```bash
cp .env.example .env.local
```

`.env.local`의 값을 로컬 환경에 맞게 수정합니다.

| 변수 | 설정 예시 |
| --- | --- |
| `MYSQL_DATABASE` | `gujeuk_local` |
| `MYSQL_USER` | 로컬 DB 사용자명 |
| `MYSQL_PASSWORD` | 로컬 DB 사용자 비밀번호 |
| `MYSQL_ROOT_PASSWORD` | 로컬 DB root 비밀번호 |
| `DB_URL` | `jdbc:mysql://mysql:3306/gujeuk_local?serverTimezone=Asia/Seoul` |
| `JWT_SECRET_KEY` | JWT 서명에 사용할 로컬 개발용 키 |
| `REDIS_HOST` | `redis` |
| `REDIS_PORT` | `6379` |
| `PROD_BASE_URL`, `STAG_BASE_URL`, `TEST_URL` | 로컬 프론트 주소인 `http://localhost:5173` |
| `APP_IMAGE` | `gujeuk-check-in-server:local` |
| `APP_PORT` | `8080` |
| `APP_CONTAINER_NAME` | `gujeuk-local-app` |
| `MYSQL_CONTAINER_NAME` | `gujeuk-local-mysql` |
| `REDIS_CONTAINER_NAME` | `gujeuk-local-redis` |
| `MYSQL_VOLUME_NAME` | `gujeuk-local-mysql-data` |
| `REDIS_VOLUME_NAME` | `gujeuk-local-redis-data` |

운영 환경의 계정이나 비밀번호 대신 로컬 전용 값을 사용합니다.

## 3. 서비스 접속

스테이징 환경에서 서비스를 확인할 수 있습니다.

- [방문자 체크인](https://gujeuk-check-in-develop.pages.dev/check-in)
- [관리자 로그인](https://gujeuk-check-in-develop.pages.dev/organ/login)
- [백엔드 API](https://aws-stag.oijwef098234.com)

> 스테이징은 개발·검증용 환경으로, 업데이트나 점검 중에는 접속이 제한될 수 있습니다. 관리자 기능은 로그인이 필요합니다.

## 프로젝트 구조

```text
apps/gujeuk-api/src/main/java/com/example/burserker/gujeuk/
├── domain/
│   ├── user/          # 회원 등록 및 체크인
│   ├── log/           # 시설 이용기록
│   ├── organ/         # 기관 인증·회원 관리·통계·엑셀
│   ├── purpose/       # 방문 목적
│   ├── residence/     # 거주지
│   └── common/        # 상태 확인 및 체크인 이벤트
├── global/            # 공통 설정·보안·예외 처리
└── infrastructure/    # 엑셀 생성 등

ops/                   # 배포 및 모니터링 설정
docs/                  # 프로젝트 문서
```

**TEAM Burserker · GuJeuk Check-In**
