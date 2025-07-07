SELECT order_participants_id
FROM sample_kits
WHERE order_participants_id NOT IN (SELECT id FROM order_participants);
SELECT * FROM sample_kits WHERE order_participants_id IS NULL;
