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

SET @ddl := IF(
    @log_table_exists > 0 AND @uk_log_user_visit_exists > 0,
    'ALTER TABLE `log` DROP INDEX `uk_log_user_visit`',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @uk_log_user_visit_exists_after := (
    SELECT COUNT(*)
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
      AND INDEX_NAME = 'uk_log_user_visit'
);

SET @ddl := IF(
    @log_table_exists > 0 AND @uk_log_user_visit_exists_after = 0,
    'ALTER TABLE `log` ADD UNIQUE KEY `uk_log_user_visit` (user_id, visit_at, purpose)',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
