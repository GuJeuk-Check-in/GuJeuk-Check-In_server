# GuJeuk Check-In
### 구즉 청소년 문화의 집 방문 관리 서비스

> 방문 등록부터 월별 실적 정리까지, 청소년 문화의 집의 운영을 돕습니다.

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
| 모니터링 | Prometheus, Grafana, Discord |

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
src/main/java/com/example/gujeuck_server/
├── domain/
│   ├── user/          # 회원 등록 및 체크인
│   ├── log/           # 시설 이용기록
│   ├── organ/         # 기관 인증·회원 관리·통계·엑셀
│   ├── purpose/       # 방문 목적
│   ├── residence/     # 거주지
│   └── common/        # 상태 확인 및 체크인 이벤트
├── global/            # 공통 설정·보안·예외 처리
└── infrastructure/    # 엑셀 생성 등

monitor-bot/           # 모니터링 애플리케이션
ops/                   # 배포 및 모니터링 설정
docs/                  # 프로젝트 문서
```

**TEAM Burserker · GuJeuk Check-In**
