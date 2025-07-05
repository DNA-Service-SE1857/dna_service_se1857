SELECT CONSTRAINT_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_NAME = 'sample_kits' AND COLUMN_NAME = 'samples_id';



ALTER TABLE sample_kits DROP FOREIGN KEY FK1ntprak1v0s7q1shm7r8twq4h;

ALTER TABLE sample_kits DROP COLUMN samples_id;
