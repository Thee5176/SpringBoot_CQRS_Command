package com.thee5176.ledger_command.record.application.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItems;

@Service
public class LedgerItemsMapper {
    private final ModelMapper modelMapper;

    public LedgerItemsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LedgerItems> map(LedgersEntryDTO ledgersEntryDTO) {
        
        return ledgersEntryDTO.getLedgerItems().stream()
            .map(ledgerItemsDto -> this.modelMapper.map(ledgerItemsDto, LedgerItems.class))
            .map(ledgerItems -> {
                ledgerItems.setCreatedAt(ledgersEntryDTO.getTimestamp());
                ledgerItems.setUpdatedAt(ledgersEntryDTO.getTimestamp());
                return ledgerItems;
            })
            .collect(Collectors.toList());
    }
}