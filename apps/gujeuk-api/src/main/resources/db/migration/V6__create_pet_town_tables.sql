CREATE TABLE IF NOT EXISTS pet_user (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS pet (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pet_user_id BIGINT NOT NULL UNIQUE,
    species VARCHAR(20) NOT NULL,
    name VARCHAR(30) NOT NULL,
    xp INT NOT NULL,
    points INT NOT NULL,
    streak INT NOT NULL,
    max_streak INT NOT NULL,
    visits INT NOT NULL,
    last_checkin_date DATE NULL,
    last_seen_at TIMESTAMP NOT NULL,
    hunger INT NOT NULL,
    happy INT NOT NULL,
    clean INT NOT NULL,
    energy INT NOT NULL,
    equipped_hat VARCHAR(30) NULL,
    equipped_shirt VARCHAR(30) NULL,
    CONSTRAINT fk_pet_user FOREIGN KEY (pet_user_id) REFERENCES pet_user(id)
);

CREATE TABLE IF NOT EXISTS pet_owned_hat (
    pet_id BIGINT NOT NULL,
    item_id VARCHAR(30) NOT NULL,
    CONSTRAINT fk_pet_owned_hat_pet FOREIGN KEY (pet_id) REFERENCES pet(id)
);

CREATE TABLE IF NOT EXISTS pet_owned_shirt (
    pet_id BIGINT NOT NULL,
    item_id VARCHAR(30) NOT NULL,
    CONSTRAINT fk_pet_owned_shirt_pet FOREIGN KEY (pet_id) REFERENCES pet(id)
);

CREATE TABLE IF NOT EXISTS pet_owned_prop (
    pet_id BIGINT NOT NULL,
    item_id VARCHAR(30) NOT NULL,
    CONSTRAINT fk_pet_owned_prop_pet FOREIGN KEY (pet_id) REFERENCES pet(id)
);

CREATE TABLE IF NOT EXISTS pet_owned_buff (
    pet_id BIGINT NOT NULL,
    item_id VARCHAR(30) NOT NULL,
    CONSTRAINT fk_pet_owned_buff_pet FOREIGN KEY (pet_id) REFERENCES pet(id)
);

CREATE TABLE IF NOT EXISTS pet_placed_prop (
    pet_id BIGINT NOT NULL,
    display_order INT NOT NULL,
    item_id VARCHAR(30) NOT NULL,
    CONSTRAINT fk_pet_placed_prop_pet FOREIGN KEY (pet_id) REFERENCES pet(id)
);
