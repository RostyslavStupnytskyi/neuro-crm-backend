-- Modify audit_logs table to support string entity IDs and actor IDs
ALTER TABLE audit_logs ALTER COLUMN actor_id TYPE VARCHAR(255);
ALTER TABLE audit_logs ALTER COLUMN entity_id TYPE VARCHAR(255);