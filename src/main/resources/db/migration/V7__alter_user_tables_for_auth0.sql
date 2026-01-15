-- This migration alters remove user data and migrate to use Auth0 managed service
-- Migrate from user-managed credentials to Auth0 IDs (string/UUID based)

-- Step 1: Drop the foreign key constraint
ALTER TABLE accounting.ledgers DROP CONSTRAINT "ledger_items_user_fkey";

-- Step 2: Change the users table ID from bigint to varchar to support Auth0 IDs
ALTER TABLE credential.users ALTER COLUMN "id" TYPE varchar USING "id"::varchar;

-- Step 3: Change the ledgers owner_id to varchar
ALTER TABLE accounting.ledgers ALTER COLUMN "owner_id" TYPE varchar USING "owner_id"::varchar;

-- Step 4: Recreate the foreign key constraint with compatible types
ALTER TABLE accounting.ledgers 
ADD CONSTRAINT "ledger_items_user_fkey" 
FOREIGN KEY ("owner_id") REFERENCES credential.users ("id") ON DELETE SET NULL;