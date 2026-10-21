-- 펫 타운(펫 게임) 도메인 제거에 따른 테이블 정리.
-- 자식 테이블 -> pet -> pet_user 순서로 삭제해야 외래키 제약에 걸리지 않는다.
DROP TABLE IF EXISTS pet_placed_prop;
DROP TABLE IF EXISTS pet_owned_buff;
DROP TABLE IF EXISTS pet_owned_prop;
DROP TABLE IF EXISTS pet_owned_shirt;
DROP TABLE IF EXISTS pet_owned_hat;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS pet_user;
