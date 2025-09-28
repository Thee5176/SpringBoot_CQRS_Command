package com.thee5176.ledger_command.record.application.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.Ledgers;

@Service
public class LedgerMapper {
    private final ModelMapper modelMapper;

    public LedgerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Ledgers map(LedgersEntryDTO ledgersEntryDTO) {
        Ledgers ledger = this.modelMapper.map(ledgersEntryDTO, Ledgers.class);   
        ledger.setCreatedAt(ledgersEntryDTO.getTimestamp());
        ledger.setUpdatedAt(ledgersEntryDTO.getTimestamp());
        // Note: The ID is set in the service layer.
        return ledger;
    }
}