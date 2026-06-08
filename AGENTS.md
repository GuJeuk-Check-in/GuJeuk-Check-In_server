# AGENTS.md

이 파일은 Codex (Codex.ai/code)가 이 저장소의 코드 작업 시 참고하는 가이드를 제공합니다.

## 작업 시작 전 필수 확인

모든 작업을 시작하기 전에 다음 문서를 순서대로 읽습니다.

1. `docs/PROJECT_CONTEXT.md`
   - 현재 서비스 상태
   - 운영 인프라와 배포 구조
   - 사용 금지 기능
   - 보안 위험
   - 다음 우선 작업
2. `docs/HOME_SERVER_OPERATIONS.md`
   - 상세 구축 이력
   - 운영 명령
   - 장애 원인과 복구 절차

문서 적용 원칙:

- `PROJECT_CONTEXT.md`의 현재 지침이 과거 작업 이력보다 우선합니다.
- API, 서버 전원, 네트워크, 배포 상태처럼 변할 수 있는 정보는 실제로 확인한 뒤 판단합니다.
- 실제 상태가 문서와 다르면 실제 상태를 우선하고 관련 문서를 갱신합니다.
- 인프라·배포·네트워크·DB·운영 명령을 변경한 경우 `docs/PROJECT_CONTEXT.md`와 `docs/HOME_SERVER_OPERATIONS.md`도 함께 갱신합니다.

## 운영 안전 지침

- 운영 Wi-Fi를 커스텀 `wifi` 명령으로 변경하지 않습니다. 기존 Netplan과 충돌하여 원격 접속과 API가 끊긴 이력이 있습니다.
- `rtcwake -m off` 기반 예약 종료·자동 부팅을 사용하지 않습니다. Samsung 550XED에서 실제 자동 부팅에 실패했습니다.
- `rtcwake -m mem`의 `deep` 모드는 정상 resume 대신 콜드 부팅이 발생했으므로 사용하지 않습니다.
- 예약 복귀가 필요하면 실제 검증된 `/usr/local/sbin/gujeuk-rtcwake schedule <seconds>`의 `freeze` 모드만 사용합니다. `shutdown`에 `0`을 입력하면 완전 종료되며 자동 복귀하지 않습니다.
- 원격 전원 작업은 완전 종료보다 `reboot`를 우선합니다.
- CI/CD 배포 과정에서 cloudflared를 직접 시작하거나 종료하지 않습니다.
- `502`, `530/1033`, `000` 장애를 구분하여 진단합니다.
- DB dump 복원 전 백업을 만들고 `docker compose exec -T`를 사용합니다.
- 운영 데이터 삭제 전 대상과 건수를 조회하고 사용자의 명시적 확인을 받습니다.
- secret, webhook, token, 비밀번호를 출력하거나 문서·커밋에 기록하지 않습니다.
- `backups/prod-2026-06-02/gujeuk-check-in-server.prod.env`는 실제 운영 secret 형태의 값을 포함하므로 내용을 출력하지 않습니다.
- 저장소 코드와 홈서버 런타임 파일이 완전히 일치한다고 가정하지 않습니다. 서버 작업 전 실제 파일과 cron 상태를 확인합니다.

## 프로젝트 개요

GuJeuk-Check-In Server는 시설 출입 관리를 위한 Spring Boot 백엔드입니다. 사용자 등록, 출입 로그 기록, 관리자 운영을 처리하며 멀티 테넌트를 지원합니다 (각 관리자가 자신의 시설을 관리).

## 빌드 및 실행 명령어

```bash
# 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun

# 모든 테스트 실행
./gradlew test

# 특정 테스트 실행
./gradlew test --tests "TestClassName"
./gradlew test --tests "com.example.gujeuck_server.domain.user.service.LoginServiceTest"

# QueryDSL Q-classes 생성 (컴파일 시 자동 생성됨)
./gradlew compileJava

# JAR 빌드
./gradlew bootJar
```

## 필수 환경 변수

```
DB_URL=jdbc:mysql://host:port/dbname
MYSQL_USER=database_username
MYSQL_PASSWORD=password
JWT_SECRET_KEY=your_secret_key
REDIS_HOST=localhost
REDIS_PORT=6379
PROD_BASE_URL=https://frontend.example.com
STAG_BASE_URL=https://staging-frontend.example.com
VERCEL_URL=https://alternate-frontend.example.com
TEST_URL=http://localhost:5173
```

## 아키텍처

### 기술 스택
- Java 17, Spring Boot 3.5.6
- MySQL 8 with Spring Data JPA
- QueryDSL 5.0.0 (복잡한 쿼리용)
- JWT 인증 (Redis 토큰 저장소)
- Apache POI (Excel 내보내기)

### 도메인 주도 패키지 구조

```
src/main/java/com/example/gujeuck_server/
├── domain/
│   ├── organ/          # 시설 운영자 계정, JWT 인증, Excel 내보내기
│   ├── user/           # 사용자 등록, 프로필
│   ├── purpose/        # 시설 목적/유형
│   ├── residence/      # 거주지 분류
│   └── log/            # 출입 기록
├── global/
│   ├── config/         # Security, JPA, QueryDSL 설정
│   ├── security/jwt/   # JwtTokenProvider, JwtTokenFilter
│   ├── entity/         # BaseIdEntity (공통 ID 필드)
│   └── error/          # 전역 예외 처리
└── infrastructure/
    └── excel/          # Excel 생성 유틸리티
```

### 도메인 모듈 패턴

각 도메인은 다음 구조를 따릅니다:
- `domain/` - JPA 엔티티와 enums
- `domain/repository/` - Spring Data repositories + QueryDSL 커스텀 저장소
- `service/` - 단일 목적 서비스 (예: CreateAdminService, LoginAdminService)
- `facade/` - 도메인 간 조정
- `presentation/` - 컨트롤러와 DTOs
- `exception/` - 도메인별 예외

### 주요 엔티티 및 관계

- **Organ** - 시설 운영자. 다수의 Users, Purposes, Residences, Logs를 가짐.
- **User** - 등록된 이용자. 하나의 Organ에 속함.
- **Purpose** - 시설 방문 목적/카테고리. 하나의 Organ에 속함.
- **Residence** - 거주지 분류. 하나의 Organ에 속함.
- **Log** - 출입 기록. User와 Organ을 참조.

### QueryDSL

Q-classes는 `src/main/generated/`에 생성됩니다. 커스텀 저장소 구현체는 복잡한 쿼리를 위해 QueryDSL을 사용합니다:
- `UserRepositoryCustom` / `UserRepositoryCustomImpl`
- `LogRepositoryCustom` / `LogRepositoryCustomImpl`
- `PurposeRepositoryCustom` / `PurposeRepositoryCustomImpl`
- `ResidenceRepositoryCustom` / `ResidenceRepositoryCustomImpl`

## API 엔드포인트

- `/organ/**` - 운영자 로그인, 비밀번호 변경, 사용자 관리, Excel 내보내기
- `/user/**` - 사용자 회원가입, 로그인
- `/purpose/**` - 시설 목적 CRUD
- `/residence/**` - 거주지 분류 CRUD
- `/log/**` - 출입 로그 CRUD

모든 엔드포인트는 JWT Bearer 토큰 인증을 사용합니다 (회원가입/로그인 제외).

## 코드 컨벤션

- 서비스는 작업별로 명명: `CreateXxxService`, `QueryXxxService`, `UpdateXxxService`, `DeleteXxxService`
- Facades는 도메인 간 로직을 조정
- DTOs는 `presentation/dto/request/`와 `presentation/dto/response/`의 record 타입
- 예외는 도메인별 기본 예외를 상속
- BaseIdEntity는 `@MappedSuperclass`를 통해 공통 `@Id` 필드를 제공
