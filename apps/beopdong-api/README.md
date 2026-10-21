# 법동 API

Java 17 / Spring Boot 3.5.6 기반 독립 실행 모듈입니다. 현재 실행 클래스와 MySQL 연결 설정만 있으며, 업무 API·헬스체크·엔티티·마이그레이션·Redis·JWT는 없습니다.

## 로컬 실행

- 로컬 MySQL에 미리 만든 `BeopDong` 스키마를 사용합니다.
- 비밀값은 저장소 밖 `~/env/법동_local.env`에 보관합니다. 공개 양식은 `.env.local.example`입니다.
- IntelliJ Application 실행 설정에서 법동 main 모듈과 `BeopdongApplication`을 선택하고 위 env 파일을 연결합니다.
- 이 작업 환경에는 비공개 실행 설정 `Beopdong - Local DB`도 만들었습니다. `.idea`는 Git에 포함하지 않습니다.
- 구즉 로컬 계정으로 접속하되 DB 대상은 법동으로 구분합니다. Redis·JWT·CORS 값은 복사하지 않습니다.
- 로컬 포트는 env의 `SERVER_PORT=8082`, env가 없는 컨테이너 기본 포트는 8080입니다.
- `ddl-auto: none`이므로 기존 DB 테이블을 생성·수정하지 않습니다. 법동에 Flyway를 추가하지 않았으며 구즉 마이그레이션도 로드하지 않습니다.

```bash
./gradlew :apps:beopdong-api:bootJar
# 산출물: apps/beopdong-api/build/libs/beopdong-api.jar
```

## DB 연결 검증

`LocalDatabaseConnectionTest`는 `BEOPDONG_LOCAL_DB_TEST=true`를 명시한 경우에만 실행됩니다. 로컬 env도 함께 전달해야 합니다. IntelliJ에서 이 테스트를 실행할 때 같은 env 파일을 연결하고 `BEOPDONG_LOCAL_DB_TEST=true`를 추가하면 됩니다.

실제 앱 DataSource로 localhost의 법동 스키마에 연결되는지와 `SELECT DATABASE(), 1`이 성공하는지 검증합니다. 데이터 삽입·수정·삭제는 하지 않습니다. 일반 CI에서는 개발자의 MySQL과 비밀값 없이 테스트를 건너뜁니다.

## Docker 로컬 검증

저장소 루트에서 이미지를 빌드합니다.

```bash
docker build -f apps/beopdong-api/Dockerfile -t beopdong-api:local .
```

Mac의 MySQL에 접속할 때 컨테이너 안의 localhost는 Mac이 아니라 컨테이너 자신입니다. IDE env 파일은 localhost로 유지하고, Docker에서만 `DB_URL`의 호스트를 `host.docker.internal`로 덮어씁니다. `SERVER_PORT=8080`도 컨테이너 실행 시 지정합니다. 기존 env의 URL 옵션과 자격증명은 유지합니다.

2026-10-21에 local env를 사용한 전체 빌드·실제 DB 테스트 2개·실행 JAR·Docker 컨테이너 기동을 확인했습니다. 컨트롤러가 없어 `/`은 404를 반환합니다. 검증 전후 테이블 수는 0개로 동일합니다.

현재 CI는 빌드 검증만 합니다. EC2 컨테이너 생성·도메인 연결·자동 배포는 아직 구성하지 않았습니다. 목표 배포 범위는 법동 스테이징 App·MySQL 2개이며 운영 법동과 법동 Redis는 제외합니다.
