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

ALTER TABLE `user` MODIFY COLUMN phone VARCHAR(255) NULL;
ALTER TABLE `log` MODIFY COLUMN phone VARCHAR(255) NULL;
ALTER TABLE pet_user MODIFY COLUMN phone VARCHAR(255) NULL;
