/*
    organ 테이블 생성
 */
CREATE TABLE organ (
                       id BIGINT PRIMARY KEY,
                       organ_name VARCHAR(255) NOT NULL,
                       password VARCHAR(100) NOT NULL
);
