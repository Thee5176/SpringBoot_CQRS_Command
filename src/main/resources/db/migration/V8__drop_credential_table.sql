-- Drop foreign key constraint BEFORE dropping the referenced table
ALTER TABLE accounting.ledgers DROP CONSTRAINT IF EXISTS ledger_items_user_fkey;

-- Drop dependent table first (authorities references users)
DROP TABLE IF EXISTS credential.authorities CASCADE;

-- Drop users table
DROP TABLE IF EXISTS credential.users CASCADE;

-- Drop the schema (only if empty, or use CASCADE to force)
DROP SCHEMA IF EXISTS credential CASCADE;
