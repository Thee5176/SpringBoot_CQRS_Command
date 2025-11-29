-- This migration alters remove user data and migrate to use managed Auth0 managed service
-- DROP TABLE credential.users CASCADE;
-- DROP TABLE credential.authorities CASCADE;

ALTER TABLE accounting.ledgers ALTER COLUMN owner_id TYPE varchar USING owner_id::varchar;