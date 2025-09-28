package com.thee5176.ledger_command.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.thee5176.ledger_command.record.application.dto.LedgerItemsEntryDTO;
import com.thee5176.ledger_command.record.application.dto.LedgerItemsEntryDTOTest;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTOTest;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItems;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.Ledgers;

@SpringBootTest
class ModelMapperConfigTest {
    @Autowired
    private ModelMapperConfig modelMapperConfig;

    @BeforeEach
    void setUp() {
        modelMapperConfig = new ModelMapperConfig();
    }

    @Test
    void testMappingLedgerItemsEntryDTOToLedgerItemsMapping() {
        final LedgerItemsEntryDTO dto = LedgerItemsEntryDTOTest.createOneSampleLedgerItemsEntryDTO();
        final ModelMapper modelMapper = modelMapperConfig.modelMapper();

        LedgerItems ledgerItems = modelMapper.map(dto, LedgerItems.class);

        assertEquals(dto.getCoa(), ledgerItems.getCoa());
        assertEquals(dto.getAmount(), ledgerItems.getAmount());
        assertEquals(dto.getBalanceType(), ledgerItems.getType());
        assertEquals(null, ledgerItems.getId()); 
        assertEquals(null, ledgerItems.getLedgerId()); 
        assertEquals(null, ledgerItems.getCreatedAt()); 
        assertEquals(null, ledgerItems.getUpdatedAt()); 
    }

    @Test
    void testMappingLedgersEntryDTOToLedgersWithoutID() {
        LedgersEntryDTO dto = LedgersEntryDTOTest.createSampleLedgersEntryDTO();
        dto.setId(null);
        final ModelMapper modelMapper = modelMapperConfig.modelMapper();

        Ledgers ledgers = modelMapper.map(dto, Ledgers.class);

        assertEquals(dto.getDate(), ledgers.getDate());
        assertEquals(dto.getDescription(), ledgers.getDescription());
        assertEquals(null, ledgers.getId());
        assertEquals(null, ledgers.getCreatedAt());
        assertEquals(null, ledgers.getUpdatedAt());
        assertEquals(null, ledgers.getOwnerId());
    }
}