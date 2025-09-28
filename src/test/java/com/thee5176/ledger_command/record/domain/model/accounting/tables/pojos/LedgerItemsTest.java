package com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.thee5176.ledger_command.record.domain.model.accounting.enums.BalanceType;

public class LedgerItemsTest {

    // Valid COA values from V2 and V3 migrations
    private static final Integer[] VALID_COA = {1101, 1202, 1303, 1404};

    @Test
    void testDefaultConstructorAndSetters() {
        LedgerItems ledger_items = new LedgerItems();
        UUID id = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Integer coa = VALID_COA[0]; // Use a valid COA
        Double amount = 200.5;
        BalanceType type = BalanceType.Debit;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        ledger_items.setId(id)
            .setLedgerId(transactionId)
            .setCoa(coa)
            .setAmount(amount)
            .setType(type)
            .setCreatedAt(createdAt)
            .setUpdatedAt(updatedAt);

        assertEquals(coa, ledger_items.getCoa());
        // ... rest unchanged
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Integer coa = VALID_COA[1];
        Double amount = 300.75;
        BalanceType type = BalanceType.Credit;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        LedgerItems ledger_items = new LedgerItems(id, transactionId, coa, amount, type, createdAt, updatedAt);

        assertEquals(coa, ledger_items.getCoa());
    }

    @Test
    void testToStringWithAllFieldsSet() {
        UUID id = UUID.randomUUID();
        UUID ledgerId = UUID.randomUUID();
        Integer coa = VALID_COA[0];
        Double amount = 123.45;
        BalanceType type = BalanceType.Debit;
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 1, 12, 0, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 2, 13, 30, 0);

        LedgerItems ledgerItems = new LedgerItems(id, ledgerId, coa, amount, type, createdAt, updatedAt);

        String result = ledgerItems.toString();

        String expectedStart = "LedgerItems (" + id + ", " + ledgerId + ", " + coa + ", " + amount + ", " + type + ", " + createdAt + ", " + updatedAt + ")";
        assertEquals(expectedStart, result);
    }

    @Test
    void testToStringWithNullFields() {
        LedgerItems ledgerItems = new LedgerItems();
        String result = ledgerItems.toString();
        // All fields are null, so the output should be: LedgerItems (null, null, null, null, null, null, null)
        assertEquals("LedgerItems (null, null, null, null, null, null, null)", result);
    }

    public static LedgerItems createSampleLedgerItems() {
        UUID id = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Integer coa = VALID_COA[2];
        Double amount = 150.0;
        BalanceType type = BalanceType.Debit;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        return new LedgerItems(id, transactionId, coa, amount, type, createdAt, updatedAt);
    }
}
