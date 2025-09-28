package com.thee5176.ledger_command.record.application.mapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTOTest;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.Ledgers;

class LedgerMapperTest {

    @Test
    void testMap_ShouldMapLedgersEntryDTOToLedgers() {
        ModelMapper modelMapper = new ModelMapper();
        LedgerMapper ledgerMapper = new LedgerMapper(modelMapper);

        LedgersEntryDTO dto = LedgersEntryDTOTest.createSampleLedgersEntryDTO();

        Ledgers ledger = ledgerMapper.map(dto);

        assertNotNull(ledger);
        assertEquals(dto.getDate(),ledger.getDate());
        assertEquals(dto.getDescription(), ledger.getDescription());
        assertEquals(dto.getTimestamp(), ledger.getCreatedAt());
        assertEquals(dto.getTimestamp(), ledger.getUpdatedAt());
    }

    @Test
    void testMap_NullDTO_ShouldReturnNullFields() {
        ModelMapper modelMapper = new ModelMapper();
        LedgerMapper ledgerMapper = new LedgerMapper(modelMapper);

        LedgersEntryDTO dto = new LedgersEntryDTO();

        Ledgers ledger = ledgerMapper.map(dto);

        assertNotNull(ledger);
        assertNull(ledger.getDescription());
    }

}