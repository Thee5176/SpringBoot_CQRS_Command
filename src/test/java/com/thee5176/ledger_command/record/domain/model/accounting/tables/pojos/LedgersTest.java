package com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class LedgersTest {

    @Test
    void testDefaultConstructor() {
        Ledgers transactions = new Ledgers();
        assertNull(transactions.getId());
        assertNull(transactions.getDate());
        assertNull(transactions.getDescription());
        assertNull(transactions.getCreatedAt());
        assertNull(transactions.getUpdatedAt());
    }

    @Test
    void testParameterizedConstructor() {
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        String description = "Test transaction";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Long ownerId = 1L;

        Ledgers transactions = new Ledgers(id, date, description, createdAt, updatedAt, ownerId);

        assertEquals(id, transactions.getId());
        assertEquals(date, transactions.getDate());
        assertEquals(description, transactions.getDescription());
        assertEquals(createdAt, transactions.getCreatedAt());
        assertEquals(updatedAt, transactions.getUpdatedAt());
        assertEquals(ownerId, transactions.getOwnerId());
    }

    @Test
    void testCopyConstructor() {
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        String description = "Copy test";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Long ownerId = 1L;

        Ledgers original = new Ledgers(id, date, description, createdAt, updatedAt, ownerId);
        Ledgers copy = new Ledgers(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getDate(), copy.getDate());
        assertEquals(original.getDescription(), copy.getDescription());
        assertEquals(original.getCreatedAt(), copy.getCreatedAt());
        assertEquals(original.getUpdatedAt(), copy.getUpdatedAt());
    }

    @Test
    void testSettersAndGetters() {
        Ledgers transactions = new Ledgers();
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.of(2024, 6, 1);
        String description = "Setter test";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 2, 13, 0);

        transactions.setId(id)
            .setDate(date)
            .setDescription(description)
            .setCreatedAt(createdAt)
            .setUpdatedAt(updatedAt);

        assertEquals(id, transactions.getId());
        assertEquals(date, transactions.getDate());
        assertEquals(description, transactions.getDescription());
        assertEquals(createdAt, transactions.getCreatedAt());
        assertEquals(updatedAt, transactions.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        String description = "Equals test";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Long ownerId = 1L;

        Ledgers t1 = new Ledgers(id, date, description, createdAt, updatedAt, ownerId);
        Ledgers t2 = new Ledgers(id, date, description, createdAt, updatedAt, ownerId);
        Ledgers t3 = new Ledgers();

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1.hashCode(), t3.hashCode());
    }

    @Test
    void testToString() {
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.of(2024, 6, 1);
        String description = "ToString test";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 2, 13, 0);
        Long ownerId = 1L;

        Ledgers transactions = new Ledgers(id, date, description, createdAt, updatedAt, ownerId);
        String str = transactions.toString();

        assertTrue(str.contains(id.toString()));
        assertTrue(str.contains(date.toString()));
        assertTrue(str.contains(description));
        assertTrue(str.contains(createdAt.toString()));
        assertTrue(str.contains(updatedAt.toString()));
    }

    public static Ledgers createSampleLedgers() {
        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        LocalDate date = LocalDate.of(2024, 6, 1);
        String description = "Sample transaction";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 1, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 2, 11, 0);
        Long ownerId = 1L;

        return new Ledgers(id, date, description, createdAt, updatedAt, ownerId);
    }
    
}
