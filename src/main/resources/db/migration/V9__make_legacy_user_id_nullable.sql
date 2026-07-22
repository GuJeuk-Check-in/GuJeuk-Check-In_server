-- Some restored production schemas still contain the retired user.user_id column
-- even though Flyway history records V7 as applied. Preserve existing values while
-- allowing current Hibernate inserts, which no longer write this column.
SET @legacy_user_id_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'user'
      AND COLUMN_NAME = 'user_id'
);

SET @ddl := IF(@legacy_user_id_exists > 0,
    'ALTER TABLE `user` MODIFY COLUMN user_id VARCHAR(30) NULL',
    'DO 0');

PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
