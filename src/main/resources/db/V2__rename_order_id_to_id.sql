DELETE FROM sample_kits
WHERE orders_id IS NOT NULL
  AND orders_id NOT IN (SELECT id FROM orders);
