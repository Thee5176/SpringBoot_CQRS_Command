ALTER TABLE accounting.transactions
    RENAME TO ledgers;

ALTER TABLE accounting.entries
    RENAME TO ledger_items;

ALTER TABLE accounting.ledger_items
    RENAME COLUMN transaction_id TO ledger_id;