-- Convert balance_type enum columns to boolean and rename column to balance_type
-- Mapping: true = Debit, false = Credit

BEGIN;

-- code_of_account.type: accounting.balance_type -> boolean
ALTER TABLE accounting.code_of_account
  ALTER COLUMN "type" TYPE boolean
  USING (
    CASE WHEN "type"::text = 'Debit' THEN true
         WHEN "type"::text = 'Credit'  THEN false
         ELSE false
    END
  );

-- Rename to the requested boolean column name
ALTER TABLE accounting.code_of_account RENAME COLUMN "type" TO "balance_type";

COMMENT ON COLUMN accounting.code_of_account."balance_type" IS 'true = Debit, false = Credit';

-- entries.type: accounting.balance_type -> boolean
ALTER TABLE accounting.entries
  ALTER COLUMN "type" TYPE boolean
  USING (
    CASE WHEN "type"::text = 'Debit' THEN true
         WHEN "type"::text = 'Credit'  THEN false
         ELSE false
    END
  );

ALTER TABLE accounting.entries RENAME COLUMN "type" TO "balance_type";

COMMENT ON COLUMN accounting.entries."balance_type" IS 'true = Debit, false = Credit';

-- Drop the enum type if no longer used
DROP TYPE IF EXISTS accounting.balance_type;
