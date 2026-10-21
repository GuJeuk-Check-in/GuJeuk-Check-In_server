SET @log_table_exists := (
    SELECT COUNT(*)
    FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
);

SET @visit_at_column_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
      AND COLUMN_NAME = 'visit_at'
);

SET @ddl := IF(
    @log_table_exists > 0 AND @visit_at_column_exists = 0,
    'ALTER TABLE `log` ADD COLUMN visit_at DATETIME NULL',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @uk_log_user_visit_exists := (
    SELECT COUNT(*)
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
      AND INDEX_NAME = 'uk_log_user_visit'
);

-- log.user_id 외래키가 uk_log_user_visit 인덱스를 백킹 인덱스로 사용 중이라
-- DROP INDEX를 단독으로 실행하면 1553 에러(needed in a foreign key constraint)가 발생한다.
-- 같은 ALTER TABLE 문에서 DROP과 ADD를 함께 실행해 최종 상태 기준으로 검증되게 한다.
SET @ddl := IF(
    @log_table_exists > 0 AND @uk_log_user_visit_exists > 0,
    'ALTER TABLE `log` DROP INDEX `uk_log_user_visit`, ADD UNIQUE KEY `uk_log_user_visit` (user_id, visit_at, purpose)',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
