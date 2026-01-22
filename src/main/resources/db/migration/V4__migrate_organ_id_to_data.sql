/*
    기존 테이블인 user, log, purpose에 organ_id 추가
 */
ALTER TABLE user ADD COLUMN organ_id BIGINT;
ALTER TABLE log ADD COLUMN organ_id BIGINT;
ALTER TABLE purpose ADD COLUMN organ_id BIGINT;

/*
    각 데이터에 organ_id 채우기
 */
UPDATE user SET organ_id = 1 WHERE organ_id IS NULL;
UPDATE log SET organ_id = 1 WHERE organ_id IS NULL;
UPDATE purpose SET organ_id = 1 WHERE organ_id IS NULL;
