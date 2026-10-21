SET @log_table_exists := (
    SELECT COUNT(*)
    FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
);

SET @client_record_id_column_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
      AND COLUMN_NAME = 'client_record_id'
);

SET @ddl := IF(
    @log_table_exists > 0 AND @client_record_id_column_exists = 0,
    'ALTER TABLE `log` ADD COLUMN client_record_id VARCHAR(64) NULL',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @uk_log_client_record_id_exists := (
    SELECT COUNT(*)
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
      AND INDEX_NAME = 'uk_log_client_record_id'
);

SET @ddl := IF(
    @log_table_exists > 0 AND @uk_log_client_record_id_exists = 0,
    'ALTER TABLE `log` ADD UNIQUE KEY `uk_log_client_record_id` (client_record_id)',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
