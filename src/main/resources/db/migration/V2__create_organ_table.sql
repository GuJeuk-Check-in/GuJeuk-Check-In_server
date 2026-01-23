/*
    organ 테이블 생성
 */
CREATE TABLE organ (
                       id BIGINT PRIMARY KEY,
                       organ_name VARCHAR(255) NOT NULL,
                       password VARCHAR(100) NOT NULL
);

/*
 organName UNIQUE 제약 조건
 */
ALTER TABLE organ
    ADD CONSTRAINT uk_organ_name UNIQUE (organ_name);
