# CLAUDE.md

이 파일은 Claude Code (claude.ai/code)가 이 저장소의 코드 작업 시 참고하는 가이드를 제공합니다.

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
./gradlew test --tests "com.example.gujeuck_server.domain.admin.service.LoginServiceTest"

# QueryDSL Q-classes 생성 (컴파일 시 자동 생성됨)
./gradlew compileJava

# JAR 빌드
./gradlew bootJar
```

## 필수 환경 변수

```
DB_URL=jdbc:mysql://host:port/dbname
DB_NAME=database_username
DB_PASSWORD=password
JWT_SECRET_KEY=your_secret_key
REDIS_HOST=localhost
REDIS_PORT=6379
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
│   ├── admin/          # 관리자 계정, JWT 인증, Excel 내보내기
│   ├── user/           # 사용자 등록, 프로필
│   ├── purpose/        # 시설 목적/유형
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

- **Admin** - 시설 소유자. 다수의 Users, Purposes, Logs를 가짐.
- **User** - 등록된 방문자. 하나의 Admin에 속함.
- **Purpose** - 시설 유형/카테고리. 하나의 Admin에 속함.
- **Log** - 출입 기록. User(익명 방문의 경우 선택적)와 Admin을 참조.

### QueryDSL

Q-classes는 `src/main/generated/`에 생성됩니다. 커스텀 저장소 구현체는 복잡한 쿼리를 위해 QueryDSL을 사용합니다:
- `UserRepositoryCustom` / `UserRepositoryCustomImpl`
- `LogRepositoryCustom` / `LogRepositoryCustomImpl`
- `PurposeRepositoryCustom` / `PurposeRepositoryCustomImpl`

## API 엔드포인트

- `/admin/**` - 관리자 로그인, 비밀번호 변경, 사용자 관리, Excel 내보내기
- `/user/**` - 사용자 회원가입, 로그인
- `/purpose/**` - 시설 목적 CRUD
- `/log/**` - 출입 로그 CRUD

모든 엔드포인트는 JWT Bearer 토큰 인증을 사용합니다 (회원가입/로그인 제외).

## 코드 컨벤션

- 서비스는 작업별로 명명: `CreateXxxService`, `QueryXxxService`, `UpdateXxxService`, `DeleteXxxService`
- Facades는 도메인 간 로직을 조정
- DTOs는 `presentation/dto/request/`와 `presentation/dto/response/`의 record 타입
- 예외는 도메인별 기본 예외를 상속
- BaseIdEntity는 `@MappedSuperclass`를 통해 공통 `@Id` 필드를 제공