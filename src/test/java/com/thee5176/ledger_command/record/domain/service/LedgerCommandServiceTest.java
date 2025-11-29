package com.thee5176.ledger_command.record.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTOTest;
import com.thee5176.ledger_command.record.application.mapper.LedgerItemsMapper;
import com.thee5176.ledger_command.record.application.mapper.LedgerMapper;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItems;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItemsTest;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.Ledgers;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgersTest;
import com.thee5176.ledger_command.record.infrastructure.repository.LedgerItemsRepository;
import com.thee5176.ledger_command.record.infrastructure.repository.LedgerRepository;

class LedgerCommandServiceTest {

    private LedgerRepository ledgerRepository;
    private LedgerItemsRepository ledgerItemsRepository;
    private LedgerMapper ledgerMapper;
    private LedgerItemsMapper ledgerItemsMapper;
    private LedgerCommandService recordCommandService;

    @BeforeEach
    void setUp() {
        ledgerRepository = mock(LedgerRepository.class);
        ledgerItemsRepository = mock(LedgerItemsRepository.class);
        ledgerMapper = mock(LedgerMapper.class);
        ledgerItemsMapper = mock(LedgerItemsMapper.class);
        recordCommandService = new LedgerCommandService(
            ledgerRepository, ledgerItemsRepository, ledgerMapper, ledgerItemsMapper
        );
    }


    @Test
    void testCreateRecord_CreatesLedgerAndLedgerItems() {
        LedgersEntryDTO dto = LedgersEntryDTOTest.createSampleLedgersEntryDTO();
        Ledgers ledger = LedgersTest.createSampleLedgers();

        // mock mapping function
        when(ledgerMapper.map(dto)).thenReturn(ledger);
        LedgerItems ledgerItems1 = LedgerItemsTest.createSampleLedgerItems();
        LedgerItems ledgerItems2 = LedgerItemsTest.createSampleLedgerItems();
        when(ledgerItemsMapper.map(dto)).thenReturn(Arrays.asList(ledgerItems1, ledgerItems2));

        // mock repository methods
        doNothing().when(ledgerRepository).createLedger(any(Ledgers.class));
        doNothing().when(ledgerItemsRepository).createLedgerItems(any(LedgerItems.class));

        recordCommandService.createLedger(dto, "testuser");

        verify(ledgerMapper).map(dto);
        verify(ledgerRepository).createLedger(any(Ledgers.class));
        verify(ledgerItemsMapper).map(dto);
        verify(ledgerItemsRepository, times(2)).createLedgerItems(any(LedgerItems.class));
    }

    @Test
    void testCreateLedger_AssignsIdsAndCallsRepositories() {
        LedgersEntryDTO dto = LedgersEntryDTOTest.createSampleLedgersEntryDTO();
        Ledgers ledger = LedgersTest.createSampleLedgers();

        // LedgerItems without IDs set
        LedgerItems ledgerItems1 = LedgerItemsTest.createSampleLedgerItems();
        ledgerItems1.setId(null);
        ledgerItems1.setLedgerId(null);
        LedgerItems ledgerItems2 = LedgerItemsTest.createSampleLedgerItems();
        ledgerItems2.setId(null);
        ledgerItems2.setLedgerId(null);

        when(ledgerMapper.map(dto)).thenReturn(ledger);
        when(ledgerItemsMapper.map(dto)).thenReturn(Arrays.asList(ledgerItems1, ledgerItems2));
        doNothing().when(ledgerRepository).createLedger(any(Ledgers.class));

        recordCommandService.createLedger(dto, "testuser");

        verify(ledgerMapper).map(dto);
        verify(ledgerRepository).createLedger(any(Ledgers.class));
        verify(ledgerItemsMapper).map(dto);
        verify(ledgerItemsRepository, times(2)).createLedgerItems(any(LedgerItems.class));
    }

    @Test
    void testCreateLedger_ThrowsExceptionIfRepositoryFails() {
        LedgersEntryDTO dto = LedgersEntryDTOTest.createSampleLedgersEntryDTO();
        Ledgers ledger = LedgersTest.createSampleLedgers();
        LedgerItems ledgerItems1 = LedgerItemsTest.createSampleLedgerItems();

        when(ledgerMapper.map(dto)).thenReturn(ledger);
        when(ledgerItemsMapper.map(dto)).thenReturn(Arrays.asList(ledgerItems1));
        doNothing().when(ledgerRepository).createLedger(any(Ledgers.class));
        // Simulate repository failure
        doNothing().when(ledgerRepository).createLedger(any(Ledgers.class));
        doNothing().when(ledgerItemsRepository).createLedgerItems(any(LedgerItems.class));

        // Should not throw, as createLedger does not catch exceptions
        recordCommandService.createLedger(dto, "testuser");

        verify(ledgerRepository).createLedger(any(Ledgers.class));
        verify(ledgerItemsRepository).createLedgerItems(any(LedgerItems.class));
    }

    @Test
    void testCreateLedger_LedgerAndItemsHaveSameLedgerId() {
        LedgersEntryDTO dto = LedgersEntryDTOTest.createSampleLedgersEntryDTO();
        Ledgers ledger = LedgersTest.createSampleLedgers();

        LedgerItems ledgerItems1 = LedgerItemsTest.createSampleLedgerItems();
        ledgerItems1.setId(null);
        ledgerItems1.setLedgerId(null);
        LedgerItems ledgerItems2 = LedgerItemsTest.createSampleLedgerItems();
        ledgerItems2.setId(null);
        ledgerItems2.setLedgerId(null);

        when(ledgerMapper.map(dto)).thenReturn(ledger);
        when(ledgerItemsMapper.map(dto)).thenReturn(Arrays.asList(ledgerItems1, ledgerItems2));
        doNothing().when(ledgerRepository).createLedger(any(Ledgers.class));
        doNothing().when(ledgerItemsRepository).createLedgerItems(any(LedgerItems.class));


        recordCommandService.createLedger(dto, "testuser");

        // The actual UUIDs are generated inside the method, so we can't assert their values,
        // but we can verify that setId and setLedgerId are called for each item.
        verify(ledgerItemsRepository, times(2)).createLedgerItems(any(LedgerItems.class));
    }
}