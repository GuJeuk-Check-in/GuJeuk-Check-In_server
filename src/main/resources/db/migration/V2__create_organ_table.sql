/*
    organ 테이블 생성
 */
CREATE TABLE organ (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       organ_name VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(300) NOT NULL);

/*
 organName UNIQUE 제약 조건
 */
ALTER TABLE organ
    ADD CONSTRAINT uk_organ_name UNIQUE (organ_name);
