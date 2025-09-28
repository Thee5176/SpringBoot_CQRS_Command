package com.thee5176.ledger_command.record.application.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class LedgersEntryDTOTest {
    // Test data for Dr/Cr balanced Ledgers
    // Arrange
    final UUID id = UUID.randomUUID();
    final LocalDate date = LocalDate.of(2024, 6, 1);
    final String description = "Test description";
    final List<LedgerItemsEntryDTO> ledgerItems = LedgerItemsEntryDTOTest.createBalancedSampleLedgerItemsEntryDTO();
    final LocalDateTime timestamp = LocalDateTime.now();

    @Test
    void testLedgersEntryDTO_AllFields() {
        // Act
        LedgersEntryDTO dto = new LedgersEntryDTO(id, date, description, ledgerItems, timestamp);

        // Assertion
        assertEquals(id, dto.getId());
        assertEquals(date, dto.getDate());
        assertEquals(description, dto.getDescription());
        assertEquals(ledgerItems, dto.getLedgerItems());
        assertEquals(timestamp, dto.getTimestamp());
    }

    @Test
    void testLedgersEntryDTO_NullFields() {
        LedgersEntryDTO dto = new LedgersEntryDTO(null, null, null, null, null);

        assertNull(dto.getId());
        assertNull(dto.getDate());
        assertNull(dto.getDescription());
        assertNull(dto.getLedgerItems());
        assertNull(dto.getTimestamp());
    }

    @Test
    void testGetCoaList_ReturnsCorrectCoaList() {
        // Arrange
        LedgersEntryDTO dto = createSampleLedgersEntryDTO();
        List<Integer> expectedCoaList = dto.getLedgerItems().stream()
                .map(LedgerItemsEntryDTO::getCoa)
                .toList();

        // Act
        List<Integer> actualCoaList = dto.getCoaList();

        // Assert
        assertEquals(expectedCoaList, actualCoaList);
    }

    @Test
    void testGetCoaList_WithNullLedgerItems_ThrowsException() {
        // Arrange
        LedgersEntryDTO dto = new LedgersEntryDTO();
        dto.setLedgerItems(null);

        // Act & Assert
        try {
            dto.getCoaList();
        } catch (NullPointerException e) {
            // Expected exception
            return;
        }
        throw new AssertionError("Expected NullPointerException");
    }

    @Test
    void testGetCoaList_WithEmptyLedgerItems_ReturnsEmptyList() {
        // Arrange
        LedgersEntryDTO dto = new LedgersEntryDTO();
        dto.setLedgerItems(List.of());

        // Act
        List<Integer> coaList = dto.getCoaList();

        // Assert
        assertEquals(List.of(), coaList);
    }

    @Test
    void testSettersAndGetters() {
        LedgersEntryDTO dto = new LedgersEntryDTO();
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        String description = "Sample";
        List<LedgerItemsEntryDTO> ledgerItems = LedgerItemsEntryDTOTest.createBalancedSampleLedgerItemsEntryDTO();
        LocalDateTime timestamp = LocalDateTime.now();

        dto.setId(id);
        dto.setDate(date);
        dto.setDescription(description);
        dto.setLedgerItems(ledgerItems);
        dto.setTimestamp(timestamp);

        assertEquals(id, dto.getId());
        assertEquals(date, dto.getDate());
        assertEquals(description, dto.getDescription());
        assertEquals(ledgerItems, dto.getLedgerItems());
        assertEquals(timestamp, dto.getTimestamp());
    }

    @Test
    void testNoArgsConstructor() {
        LedgersEntryDTO dto = new LedgersEntryDTO();
        assertNull(dto.getId());
        assertNull(dto.getDate());
        assertNull(dto.getDescription());
        assertNull(dto.getLedgerItems());
        assertNull(dto.getTimestamp());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        String description = "AllArgs";
        List<LedgerItemsEntryDTO> ledgerItems = LedgerItemsEntryDTOTest.createBalancedSampleLedgerItemsEntryDTO();
        LocalDateTime timestamp = LocalDateTime.now();

        LedgersEntryDTO dto = new LedgersEntryDTO(id, date, description, ledgerItems, timestamp);

        assertEquals(id, dto.getId());
        assertEquals(date, dto.getDate());
        assertEquals(description, dto.getDescription());
        assertEquals(ledgerItems, dto.getLedgerItems());
        assertEquals(timestamp, dto.getTimestamp());
    }

    // Reusable Test Data
    public static LedgersEntryDTO createSampleLedgersEntryDTO() {
        final UUID id = UUID.randomUUID();
        final LocalDate date = LocalDate.of(2024, 6, 1);
        final String description = "Test description";
        final List<LedgerItemsEntryDTO> ledgerItems = LedgerItemsEntryDTOTest.createBalancedSampleLedgerItemsEntryDTO();
        final LocalDateTime timestamp = LocalDateTime.now();

        final LedgersEntryDTO ledgersEntryDTO = new LedgersEntryDTO(id, date, description, ledgerItems, timestamp);
        return ledgersEntryDTO;
    }
}
