-- 폐기된 String userId 컬럼(user.user_id) 제거.
-- 환경마다 컬럼 존재 여부가 달라(local/신규 DB에는 없음, develop/prod에는 남아있음)
-- MySQL은 DROP COLUMN IF EXISTS를 지원하지 않으므로 information_schema로 확인 후 조건부로 드롭한다. (멱등)
SET @user_id_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'user'
      AND COLUMN_NAME = 'user_id'
);

SET @ddl := IF(@user_id_exists > 0,
    'ALTER TABLE `user` DROP COLUMN user_id',
    'DO 0');

PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
