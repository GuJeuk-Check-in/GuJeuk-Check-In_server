-- 방문 기록의 중복 판정을 visit_at(초 단위)으로 옮기기 위한 사전 작업.
--
-- 지금까지 visit_at 은 회원가입·체크인 경로에서만 채워졌고, 관리자가 직접
-- 입력한 기록(POST /log)은 비어 있었다. 그대로 visit_at 으로 비교하면
-- NULL 은 어떤 값과도 같지 않아 기존 기록 대부분이 검사에서 빠진다.

-- 콜론이 빠진 채로 저장된 시각("1005" -> "10:05")을 먼저 바로잡는다.
UPDATE log
SET visit_time = CONCAT(LEFT(LPAD(visit_time, 4, '0'), 2), ':', RIGHT(LPAD(visit_time, 4, '0'), 2))
WHERE visit_time REGEXP '^[0-9]{3,4}$';

-- 채워 넣을 값을 먼저 계산해 둔다.
-- log 를 갱신하면서 동시에 log 를 조회할 수 없어 별도 테이블을 쓴다.
CREATE TABLE log_visit_at_backfill (
    id BIGINT NOT NULL PRIMARY KEY,
    visit_at DATETIME NOT NULL
);

INSERT INTO log_visit_at_backfill (id, visit_at)
SELECT id, STR_TO_DATE(CONCAT(visit_date, ' ', visit_time), '%Y년%m월%d일 %H:%i')
FROM log
WHERE visit_at IS NULL
  AND STR_TO_DATE(CONCAT(visit_date, ' ', visit_time), '%Y년%m월%d일 %H:%i') IS NOT NULL;

-- uk_log_user_visit (user_id, visit_at, purpose) 가 이미 걸려 있다.
-- 같은 사람이 같은 분에 같은 목적으로 두 번 들어온 기록은 백필하면 값이 같아져
-- 제약에 걸린다. 초 정보가 없어 구분할 방법이 없으므로 가장 오래된 것만 채우고
-- 나머지는 NULL 로 남긴다. 데이터를 지우거나 초를 지어내지 않는다.
--
-- 갱신 대상 테이블을 서브쿼리에서 참조할 수 없어(MySQL 1093) 건너뛸 id 를
-- 따로 모아 둔다.
CREATE TABLE log_visit_at_skip (
    id BIGINT NOT NULL PRIMARY KEY
);

-- 백필끼리 겹치는 경우: 가장 오래된 것만 남긴다.
INSERT INTO log_visit_at_skip (id)
SELECT b.id
FROM log_visit_at_backfill b
JOIN log l ON l.id = b.id
JOIN (
    SELECT l2.user_id, l2.purpose, b2.visit_at, MIN(b2.id) AS keep_id
    FROM log_visit_at_backfill b2
    JOIN log l2 ON l2.id = b2.id
    WHERE l2.user_id IS NOT NULL
    GROUP BY l2.user_id, l2.purpose, b2.visit_at
    HAVING COUNT(*) > 1
) k ON k.user_id = l.user_id AND k.purpose = l.purpose AND k.visit_at = b.visit_at
WHERE b.id <> k.keep_id;

-- 이미 visit_at 이 있는 행과 겹치는 경우.
INSERT IGNORE INTO log_visit_at_skip (id)
SELECT b.id
FROM log_visit_at_backfill b
JOIN log l ON l.id = b.id
JOIN log existing
  ON existing.user_id = l.user_id
 AND existing.purpose = l.purpose
 AND existing.visit_at = b.visit_at
 AND existing.id <> l.id
WHERE l.user_id IS NOT NULL;

DELETE FROM log_visit_at_backfill WHERE id IN (SELECT id FROM log_visit_at_skip);

DROP TABLE log_visit_at_skip;

UPDATE log l
JOIN log_visit_at_backfill b ON b.id = l.id
SET l.visit_at = b.visit_at;

DROP TABLE log_visit_at_backfill;
