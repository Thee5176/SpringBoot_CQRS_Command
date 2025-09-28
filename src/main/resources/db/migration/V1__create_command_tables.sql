CREATE SCHEMA IF NOT EXISTS accounting;

CREATE TYPE accounting.balance_type AS ENUM (
  'Debit',
  'Credit'
);

CREATE TYPE accounting.element AS ENUM (
  'Assets',
  'Liabilities',
  'Equity',
  'Revenue',
  'Expenses',
  'Other'
);

CREATE TABLE accounting.transactions (
  "id" uuid PRIMARY KEY,
  "date" date NOT NULL,
  "description" varchar NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL
);

CREATE TABLE accounting.entries (
  "id" uuid PRIMARY KEY,
  "transaction_id" uuid NOT NULL,
  "coa" int NOT NULL,
  "amount" double precision NOT NULL,
  "type" accounting.balance_type NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL
);

CREATE TABLE accounting.code_of_account (
  "code" integer PRIMARY KEY NOT NULL,
  "title" varchar UNIQUE NOT NULL,
  "level" int NOT NULL,
  "element" accounting.element NOT NULL,
  "type" accounting.balance_type NOT NULL
);

ALTER TABLE accounting.entries ADD FOREIGN KEY ("coa") REFERENCES accounting.code_of_account ("code") ON DELETE SET NULL;
ALTER TABLE accounting.entries ADD FOREIGN KEY ("transaction_id") REFERENCES accounting.transactions ("id") ON DELETE CASCADE;