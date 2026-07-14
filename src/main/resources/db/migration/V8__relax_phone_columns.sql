SET @user_phone_unique_index := (
    SELECT INDEX_NAME
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'user'
      AND COLUMN_NAME = 'phone'
      AND NON_UNIQUE = 0
      AND INDEX_NAME <> 'PRIMARY'
    LIMIT 1
);

SET @ddl := IF(
    @user_phone_unique_index IS NOT NULL,
    CONCAT('ALTER TABLE `user` DROP INDEX `', @user_phone_unique_index, '`'),
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @pet_user_phone_unique_index := (
    SELECT INDEX_NAME
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'pet_user'
      AND COLUMN_NAME = 'phone'
      AND NON_UNIQUE = 0
      AND INDEX_NAME <> 'PRIMARY'
    LIMIT 1
);

SET @ddl := IF(
    @pet_user_phone_unique_index IS NOT NULL,
    CONCAT('ALTER TABLE pet_user DROP INDEX `', @pet_user_phone_unique_index, '`'),
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @user_phone_column_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'user'
      AND COLUMN_NAME = 'phone'
);

SET @ddl := IF(
    @user_phone_column_exists > 0,
    'ALTER TABLE `user` MODIFY COLUMN phone VARCHAR(255) NULL',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @log_phone_column_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'log'
      AND COLUMN_NAME = 'phone'
);

SET @ddl := IF(
    @log_phone_column_exists > 0,
    'ALTER TABLE `log` MODIFY COLUMN phone VARCHAR(255) NULL',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @pet_user_phone_column_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'pet_user'
      AND COLUMN_NAME = 'phone'
);

SET @ddl := IF(
    @pet_user_phone_column_exists > 0,
    'ALTER TABLE pet_user MODIFY COLUMN phone VARCHAR(255) NULL',
    'DO 0'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
