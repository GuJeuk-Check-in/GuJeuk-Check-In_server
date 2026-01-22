UPDATE user
SET organ_id = (SELECT MIN(id) FROM organ)
WHERE organ_id IS NULL;

UPDATE log
SET organ_id = (SELECT MIN(id) FROM organ)
WHERE organ_id IS NULL;

UPDATE purpose
SET organ_id = (SELECT MIN(id) FROM organ)
WHERE organ_id IS NULL;
