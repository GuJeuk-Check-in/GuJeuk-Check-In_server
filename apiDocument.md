# GuJeuk Check-In Server API 명세서

## 목차
1. [User API](#user-api)
2. [Admin API](#admin-api)
3. [Log API](#log-api)
4. [Purpose API](#purpose-api)
5. [Error Code 목록](#error-code-목록)
6. [Enum 정의](#enum-정의)

---

## User API

### 1. 회원가입
| 항목 | 내용 |
|------|------|
| **Domain** | User |
| **기능** | 새로운 사용자 회원가입 |
| **HTTP Method** | `POST` |
| **API Path** | `/user/sign-up` |
| **토큰 필요** | X |

**Request Body**
```json
{
  "name": "string",
  "gender": "MALE|FEMALE",
  "phone": "string",
  "maleCount": 0,
  "femaleCount": 0,
  "birthYMD": "string",
  "residence": "string",
  "privacyAgreed": true,
  "purpose": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| name | string | O | 이름 |
| gender | string | O | 성별 (MALE, FEMALE) |
| phone | string | O | 전화번호 |
| maleCount | int | O | 남자 동행인 수 |
| femaleCount | int | O | 여자 동행인 수 |
| birthYMD | string | O | 생년월일 |
| residence | string | O | 거주지 |
| privacyAgreed | boolean | O | 개인정보 동의 |
| purpose | string | O | 방문목적 |

**Response (201 Created)**
```json
{
  "userId": "string"
}
```

| 필드 | 타입 | 설명 |
|------|------|------|
| userId | string | 생성된 사용자 ID |

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 401 | USER_EXIST | 유저가 이미 존재합니다 |
| 400 | BAD_REQUEST | 요청 데이터 검증 실패 |

---

### 2. 로그인 (체크인)
| 항목 | 내용 |
|------|------|
| **Domain** | User |
| **기능** | 기존 사용자 로그인 (방문 체크인) |
| **HTTP Method** | `POST` |
| **API Path** | `/user/login` |
| **토큰 필요** | X |

**Request Body**
```json
{
  "userId": "string",
  "purpose": "string",
  "maleCount": 0,
  "femaleCount": 0
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| userId | string | O | 사용자 ID |
| purpose | string | O | 방문목적 |
| maleCount | int | O | 남자 동행인 수 |
| femaleCount | int | O | 여자 동행인 수 |

**Response (200 OK)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | USER_NOT_FOUND | 해당 유저가 존재하지 않습니다 |
| 400 | BAD_REQUEST | 요청 데이터 검증 실패 |

---

## Admin API

### 1. 관리자 로그인
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 관리자 계정 로그인 |
| **HTTP Method** | `POST` |
| **API Path** | `/admin/login` |
| **토큰 필요** | X |

**Request Body**
```json
{
  "password": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| password | string | O | 비밀번호 |

**Response (200 OK)**
```json
{
  "accessToken": "string",
  "refreshToken": "string"
}
```

| 필드 | 타입 | 설명 |
|------|------|------|
| accessToken | string | JWT Access Token |
| refreshToken | string | JWT Refresh Token |

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | ADMIN_NOT_FOUND | 해당 관리자 계정이 존재하지 않습니다 |
| 401 | PASSWORD_MISMATCH | 비밀번호가 일치하지 않습니다 |

---

### 2. 관리자 생성
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 새로운 관리자 계정 생성 |
| **HTTP Method** | `POST` |
| **API Path** | `/admin/create` |
| **토큰 필요** | X |

**Request Body**
```json
{
  "password": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| password | string | O | 비밀번호 |

**Response (200 OK)**
```json
{
  "accessToken": "string",
  "refreshToken": "string"
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 400 | BAD_REQUEST | 요청 데이터 검증 실패 |

---

### 3. 비밀번호 변경
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 관리자 비밀번호 변경 |
| **HTTP Method** | `PATCH` |
| **API Path** | `/admin/change` |
| **토큰 필요** | O (Access Token) |

**Request Body**
```json
{
  "oldPassword": "string",
  "newPassword": "string",
  "confirmNewPassword": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| oldPassword | string | O | 기존 비밀번호 |
| newPassword | string | O | 새 비밀번호 |
| confirmNewPassword | string | O | 새 비밀번호 확인 |

**Response (200 OK)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | ADMIN_NOT_FOUND | 해당 관리자 계정이 존재하지 않습니다 |
| 401 | PASSWORD_MISMATCH | 비밀번호가 일치하지 않습니다 |
| 401 | INVALID_PASSWORD_CONFIRM | 비밀번호 확인이 일치하지 않습니다 |
| 400 | SAME_OLD_PASSWORD | 기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다 |

---

### 4. 토큰 갱신
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | Access Token 재발급 |
| **HTTP Method** | `PATCH` |
| **API Path** | `/admin/re-issue` |
| **토큰 필요** | O (Refresh Token in Header) |

**Request Header**
```
Authentication: {refreshToken}
```

**Response (200 OK)**
```json
{
  "accessToken": "string",
  "refreshToken": "string"
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |
| 401 | EXPIRED_TOKEN | 만료된 토큰입니다 |
| 404 | REFRESH_TOKEN_NOT_FOUND | RefreshToken이 존재하지 않습니다 |

---

### 5. 모든 사용자 조회
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 전체 사용자 목록 조회 (페이지네이션) |
| **HTTP Method** | `GET` |
| **API Path** | `/admin/user/all` |
| **토큰 필요** | O (Access Token) |

**Query Parameters**
| 파라미터 | 타입 | 기본값 | 설명 |
|----------|------|--------|------|
| page | int | 0 | 페이지 번호 |
| size | int | 30 | 페이지 크기 |
| sort | string | id,DESC | 정렬 기준 |

**Response (200 OK)**
```json
{
  "totalCount": 100,
  "slice": {
    "content": [
      {
        "id": 1,
        "name": "string",
        "age": "20대",
        "gender": "MALE",
        "phone": "string",
        "birthYMD": "string",
        "residence": "string",
        "privacyAgreed": true,
        "count": 5
      }
    ],
    "hasNext": true,
    "number": 0,
    "size": 30,
    "numberOfElements": 30
  }
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 6. 거주지별 사용자 조회
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 거주지 기준 사용자 목록 조회 |
| **HTTP Method** | `GET` |
| **API Path** | `/admin/user` |
| **토큰 필요** | O (Access Token) |

**Query Parameters**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| residence | string | O | 거주지 |
| page | int | X | 페이지 번호 |
| size | int | X | 페이지 크기 |

**Response (200 OK)**
```json
{
  "totalCount": 50,
  "slice": {
    "content": [
      {
        "id": 1,
        "name": "string",
        "age": "20대",
        "gender": "MALE",
        "phone": "string",
        "birthYMD": "string",
        "residence": "string",
        "privacyAgreed": true,
        "count": 5
      }
    ],
    "hasNext": true,
    "number": 0,
    "size": 30,
    "numberOfElements": 30
  }
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 400 | INVALID_RESIDENCE | 존재하지 않는 거주지 이름입니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 7. 사용자 상세 조회
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 특정 사용자 상세 정보 조회 |
| **HTTP Method** | `GET` |
| **API Path** | `/admin/user/{id}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | long | 사용자 ID |

**Response (200 OK)**
```json
{
  "name": "string",
  "userId": "string",
  "phone": "string",
  "birthYMD": "string",
  "residence": "string"
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | USER_NOT_FOUND | 해당 유저가 존재하지 않습니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 8. 사용자 정보 수정
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 특정 사용자 정보 수정 |
| **HTTP Method** | `PATCH` |
| **API Path** | `/admin/user/{id}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | long | 사용자 ID |

**Request Body**
```json
{
  "name": "string",
  "userId": "string",
  "phone": "string",
  "birthYMD": "string",
  "residence": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| name | string | O | 이름 (최대 30자) |
| userId | string | O | 사용자 ID (최대 30자) |
| phone | string | O | 전화번호 (최대 11자) |
| birthYMD | string | O | 생년월일 |
| residence | string | O | 거주지 |

**Response (200 OK)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | USER_NOT_FOUND | 해당 유저가 존재하지 않습니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |
| 400 | BAD_REQUEST | 요청 데이터 검증 실패 |

---

### 9. 엑셀 파일 다운로드
| 항목 | 내용 |
|------|------|
| **Domain** | Admin |
| **기능** | 월별 방문 로그 엑셀 파일 다운로드 |
| **HTTP Method** | `GET` |
| **API Path** | `/admin/excel/{yearMonth}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| yearMonth | string | 년월 (예: "202501") |

**Response (200 OK)**
```
Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
Body: Excel 파일 (byte[])
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 507 | EXCEL_GENERATION_FAILED | 엑셀 파일 생성 중 오류가 발생했습니다 |
| 401 | INVALID_DATE | 유효하지 않은 날짜 형식입니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

## Log API

### 1. 방문 로그 생성
| 항목 | 내용 |
|------|------|
| **Domain** | Log |
| **기능** | 새로운 방문 로그 생성 |
| **HTTP Method** | `POST` |
| **API Path** | `/log` |
| **토큰 필요** | O (Access Token) |

**Request Body**
```json
{
  "name": "string",
  "age": "TEENS|TWENTIES|THIRTIES|FORTIES|FIFTIES|SIXTIES|SEVENTIES_PLUS",
  "phone": "string",
  "maleCount": 0,
  "femaleCount": 0,
  "purpose": "string",
  "visitDate": "string",
  "privacyAgreed": true,
  "visitTime": "string",
  "residence": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| name | string | O | 이름 (최대 30자) |
| age | string | O | 나이대 (Enum 참조) |
| phone | string | O | 전화번호 (최대 30자) |
| maleCount | int | O | 남자 동행인 수 |
| femaleCount | int | O | 여자 동행인 수 |
| purpose | string | O | 방문목적 |
| visitDate | string | O | 방문 날짜 |
| privacyAgreed | boolean | O | 개인정보 동의 (true여야 함) |
| visitTime | string | O | 방문 시간 |
| residence | string | O | 거주지 |

**Response (201 Created)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 409 | DUPLICATE_LOG | 이미 해당 시간의 로그가 존재합니다 |
| 400 | BAD_REQUEST | 요청 데이터 검증 실패 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 2. 방문 로그 수정
| 항목 | 내용 |
|------|------|
| **Domain** | Log |
| **기능** | 기존 방문 로그 수정 |
| **HTTP Method** | `PATCH` |
| **API Path** | `/log/{log-id}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| log-id | long | 로그 ID |

**Request Body**
```json
{
  "name": "string",
  "age": "TEENS|TWENTIES|THIRTIES|FORTIES|FIFTIES|SIXTIES|SEVENTIES_PLUS",
  "phone": "string",
  "maleCount": 0,
  "femaleCount": 0,
  "purpose": "string",
  "visitDate": "string",
  "privacyAgreed": true,
  "visitTime": "string",
  "residence": "string"
}
```

**Response (204 No Content)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | LOG_NOT_FOUND | 존재하지 않는 이용목록입니다 |
| 409 | DUPLICATE_LOG | 이미 해당 시간의 로그가 존재합니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 3. 방문 로그 삭제
| 항목 | 내용 |
|------|------|
| **Domain** | Log |
| **기능** | 방문 로그 삭제 |
| **HTTP Method** | `DELETE` |
| **API Path** | `/log/{log-id}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| log-id | long | 로그 ID |

**Response (204 No Content)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | LOG_NOT_FOUND | 존재하지 않는 이용목록입니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 4. 방문 로그 목록 조회
| 항목 | 내용 |
|------|------|
| **Domain** | Log |
| **기능** | 전체 방문 로그 목록 조회 (페이지네이션) |
| **HTTP Method** | `GET` |
| **API Path** | `/log` |
| **토큰 필요** | O (Access Token) |

**Query Parameters**
| 파라미터 | 타입 | 기본값 | 설명 |
|----------|------|--------|------|
| page | int | 0 | 페이지 번호 |
| size | int | 30 | 페이지 크기 |
| sort | string | visitDate,id,DESC | 정렬 기준 |

**Response (200 OK)**
```json
{
  "content": [
    {
      "id": 1,
      "name": "string",
      "maleCount": 0,
      "femaleCount": 0,
      "visitDate": "string"
    }
  ],
  "hasNext": true,
  "number": 0,
  "size": 30,
  "numberOfElements": 30
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 5. 방문 로그 상세 조회
| 항목 | 내용 |
|------|------|
| **Domain** | Log |
| **기능** | 특정 방문 로그 상세 조회 |
| **HTTP Method** | `GET` |
| **API Path** | `/log/{log-id}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| log-id | long | 로그 ID |

**Response (200 OK)**
```json
{
  "id": 1,
  "name": "string",
  "age": "TWENTIES",
  "phone": "string",
  "maleCount": 0,
  "femaleCount": 0,
  "purpose": "string",
  "visitDate": "string",
  "privacyAgreed": true
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | LOG_NOT_FOUND | 존재하지 않는 이용목록입니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

## Purpose API

### 1. 방문목적 생성
| 항목 | 내용 |
|------|------|
| **Domain** | Purpose |
| **기능** | 새로운 방문목적 생성 |
| **HTTP Method** | `POST` |
| **API Path** | `/purpose` |
| **토큰 필요** | O (Access Token) |

**Request Body**
```json
{
  "purpose": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| purpose | string | O | 방문목적 (1~30자) |

**Response (201 Created)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 409 | PURPOSE_ALREADY_EXIST | 이미 존재하는 방문 목적입니다 |
| 400 | BAD_REQUEST | 요청 데이터 검증 실패 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 2. 방문목적 수정
| 항목 | 내용 |
|------|------|
| **Domain** | Purpose |
| **기능** | 기존 방문목적 수정 |
| **HTTP Method** | `PATCH` |
| **API Path** | `/purpose/{id}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | long | 방문목적 ID |

**Request Body**
```json
{
  "purpose": "string"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| purpose | string | O | 방문목적 (1~30자) |

**Response (200 OK)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | PURPOSE_NOT_FOUND | 존재하지 않는 방문목적입니다 |
| 409 | PURPOSE_ALREADY_EXIST | 이미 존재하는 방문 목적입니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 3. 방문목적 삭제
| 항목 | 내용 |
|------|------|
| **Domain** | Purpose |
| **기능** | 방문목적 삭제 |
| **HTTP Method** | `DELETE` |
| **API Path** | `/purpose/{id}` |
| **토큰 필요** | O (Access Token) |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | long | 방문목적 ID |

**Response (204 No Content)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | PURPOSE_NOT_FOUND | 존재하지 않는 방문목적입니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

### 4. 방문목적 단건 조회
| 항목 | 내용 |
|------|------|
| **Domain** | Purpose |
| **기능** | 특정 방문목적 조회 |
| **HTTP Method** | `GET` |
| **API Path** | `/purpose/{id}` |
| **토큰 필요** | X |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | long | 방문목적 ID |

**Response (200 OK)**
```json
{
  "id": 1,
  "purpose": "string"
}
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | PURPOSE_NOT_FOUND | 존재하지 않는 방문목적입니다 |

---

### 5. 전체 방문목적 조회
| 항목 | 내용 |
|------|------|
| **Domain** | Purpose |
| **기능** | 모든 방문목적 목록 조회 |
| **HTTP Method** | `GET` |
| **API Path** | `/purpose/all` |
| **토큰 필요** | X |

**Response (200 OK)**
```json
[
  {
    "id": 1,
    "purpose": "string"
  },
  {
    "id": 2,
    "purpose": "string"
  }
]
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| - | - | 에러 없음 |

---

### 6. 방문목적 순서 변경
| 항목 | 내용 |
|------|------|
| **Domain** | Purpose |
| **기능** | 방문목적 표시 순서 변경 |
| **HTTP Method** | `PATCH` |
| **API Path** | `/purpose/move` |
| **토큰 필요** | O (Access Token) |

**Request Body**
```json
{
  "purposeId": [1, 3, 2, 4]
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| purposeId | long[] | O | 새로운 순서의 ID 배열 |

**Response (200 OK)**
```
없음 (void)
```

**Error List**
| 상태 코드 | 에러 이름 | 설명 |
|-----------|-----------|------|
| 404 | PURPOSE_NOT_FOUND | 존재하지 않는 방문목적입니다 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다 |

---

## Error Code 목록

### 인증 관련 에러 (Authentication)
| 에러 코드 | HTTP 상태 | 설명 |
|-----------|-----------|------|
| INVALID_TOKEN | 401 | 유효하지 않은 토큰입니다 |
| EXPIRED_TOKEN | 401 | 만료된 토큰입니다 |
| REFRESH_TOKEN_NOT_FOUND | 404 | RefreshToken이 존재하지 않습니다 |
| INVALID_ROLE | 401 | 유효하지 않은 역할입니다 |

### 사용자 관련 에러 (User)
| 에러 코드 | HTTP 상태 | 설명 |
|-----------|-----------|------|
| USER_NOT_FOUND | 404 | 해당 유저가 존재하지 않습니다 |
| USER_MISMATCH | 401 | 유저가 일치하지 않습니다 |
| INVALID_USER | 401 | 유효하지 않은 사용자입니다 |
| USER_EXIST | 401 | 유저가 이미 존재합니다 |
| COMPANION_NOT_FOUND | 404 | 존재하지 않는 동행인 ID입니다 |

### 관리자 관련 에러 (Admin)
| 에러 코드 | HTTP 상태 | 설명 |
|-----------|-----------|------|
| ADMIN_NOT_FOUND | 404 | 해당 관리자 계정이 존재하지 않습니다 |
| PASSWORD_MISMATCH | 401 | 비밀번호가 일치하지 않습니다 |
| INVALID_PASSWORD_CONFIRM | 401 | 비밀번호 확인이 일치하지 않습니다 |
| SAME_OLD_PASSWORD | 400 | 기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다 |

### 로그 관련 에러 (Log)
| 에러 코드 | HTTP 상태 | 설명 |
|-----------|-----------|------|
| LOG_NOT_FOUND | 404 | 존재하지 않는 이용목록입니다 |
| DUPLICATE_LOG | 409 | 이미 해당 시간의 로그가 존재합니다 |

### 방문목적 관련 에러 (Purpose)
| 에러 코드 | HTTP 상태 | 설명 |
|-----------|-----------|------|
| PURPOSE_NOT_FOUND | 404 | 존재하지 않는 방문목적입니다 |
| PURPOSE_ALREADY_EXIST | 409 | 이미 존재하는 방문 목적입니다 |

### 기타 에러
| 에러 코드 | HTTP 상태 | 설명 |
|-----------|---------|------|
| INVALID_RESIDENCE | 400     | 존재하지 않는 거주지 이름입니다 |
| EXCEL_GENERATION_FAILED | 507     | 엑셀 파일 생성 중 오류가 발생했습니다 |
| INVALID_DATE | 400     | 유효하지 않은 날짜 형식입니다 |
| BAD_REQUEST | 400     | 잘못된 요청입니다 |
| INTERNAL_SERVER_ERROR | 500     | 서버 내부 오류입니다 |

---

## Enum 정의

### Gender (성별)
| 값 | 설명 |
|----|------|
| MALE | 남성 |
| FEMALE | 여성 |

### Age (나이대)
| 값 | 라벨 |
|----|------|
| TEENS | 10대 |
| TWENTIES | 20대 |
| THIRTIES | 30대 |
| FORTIES | 40대 |
| FIFTIES | 50대 |
| SIXTIES | 60대 |
| SEVENTIES_PLUS | 70대 이상 |

---

## 공통 에러 응답 형식

모든 에러 응답은 다음 형식으로 반환됩니다:

```json
{
  "message": "에러 메시지",
  "status": 400,
  "timestamp": "2026-01-26T15:30:45.123456",
  "description": "상세 설명"
}
```

---

## 인증 방식

### JWT Token
- Access Token: API 요청 시 사용
- Refresh Token: Access Token 갱신 시 사용

### 토큰 전달 방식
```
Header:
  Authorization: Bearer {accessToken}

토큰 갱신 시:
  Authentication: {refreshToken}
```

### 토큰 필요 API 표시
- **O**: 토큰 필요 (Authorization 헤더에 Bearer 토큰 포함)
- **X**: 토큰 불필요 (공개 API)
