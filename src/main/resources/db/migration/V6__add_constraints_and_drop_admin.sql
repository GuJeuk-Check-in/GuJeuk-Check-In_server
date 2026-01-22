ALTER TABLE user
    MODIFY organ_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_user_organ
    FOREIGN KEY (organ_id) REFERENCES organ(id);

ALTER TABLE log
    MODIFY organ_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_log_organ
    FOREIGN KEY (organ_id) REFERENCES organ(id);

ALTER TABLE purpose
    MODIFY organ_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_purpose_organ
    FOREIGN KEY (organ_id) REFERENCES organ(id);