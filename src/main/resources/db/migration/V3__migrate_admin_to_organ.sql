/*
    기존 admin 데이터 개념을 organ으로 마이그레이션
 */
INSERT INTO organ (id, organ_name, password)
SELECT
    a.id,
    '구즉 청소년 문화의집' AS organ_name,
    a.password
FROM admin a
         LEFT JOIN organ o ON o.id = a.id
WHERE o.id IS NULL;