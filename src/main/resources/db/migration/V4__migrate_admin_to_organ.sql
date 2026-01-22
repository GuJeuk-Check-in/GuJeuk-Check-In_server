INSERT INTO organ (id, organ_name, password)
SELECT
    a.id,
    '구즉 청소년 문화의집' AS organ_name,
    a.password
FROM admin a
         LEFT JOIN organ o ON o.id = a.id
WHERE o.id IS NULL;