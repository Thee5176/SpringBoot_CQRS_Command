CREATE SCHEMA IF NOT EXISTS credential;

CREATE TABLE credential.users (
    "id" bigint PRIMARY KEY NOT NULL,
    "username" VARCHAR(50) NOT NULL UNIQUE,
    "password" VARCHAR(255) NOT NULL,
    "is_enabled" BOOLEAN,
    "is_account_non_expired" BOOLEAN,
    "is_credentials_non_expired" BOOLEAN,
    "is_account_non_locked" BOOLEAN,

    "firstname" VARCHAR(255) NOT NULL,
    "lastname" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL
);

CREATE TABLE credential.authorities (
    "id" bigint PRIMARY KEY NOT NULL,
    "username" VARCHAR(50) NOT NULL,
    "authority" VARCHAR(50)[] NOT NULL,

    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES credential.users (username)
);

CREATE UNIQUE INDEX ix_auth_username ON credential.authorities (username, authority);

ALTER TABLE accounting.ledgers ADD COLUMN "owner_id" BIGINT NOT NULL;
ALTER TABLE accounting.ledgers ADD CONSTRAINT "ledger_items_user_fkey" FOREIGN KEY ("owner_id") REFERENCES credential.users ("id") ON DELETE SET NULL;