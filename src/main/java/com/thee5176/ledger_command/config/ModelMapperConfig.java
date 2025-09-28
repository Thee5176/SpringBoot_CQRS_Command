package com.thee5176.ledger_command.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.thee5176.ledger_command.record.application.dto.LedgerItemsEntryDTO;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItems;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.Ledgers;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE)
            .setFieldMatchingEnabled(true);       
        
        // LedgerItemsEntryDTO map to LedgerItems
        modelMapper.createTypeMap(LedgerItemsEntryDTO.class, LedgerItems.class)
            .addMapping(LedgerItemsEntryDTO::getCoa, LedgerItems::setCoa)
            .addMapping(LedgerItemsEntryDTO::getAmount, LedgerItems::setAmount)
            .addMapping(LedgerItemsEntryDTO::getBalanceType, LedgerItems::setType)
            .addMappings(mapper -> mapper.skip(LedgerItems::setId))
            .addMappings(mapper -> mapper.skip(LedgerItems::setLedgerId))
            .addMappings(mapper -> mapper.skip(LedgerItems::setCreatedAt))
            .addMappings(mapper -> mapper.skip(LedgerItems::setUpdatedAt));
            // Note1: The ID and LedgerId are set in the service layer.
            // Note2: The CreatedAt and UpdatedAt are set in the custom mapper layer,

        // LedgersEntryDTO map to Ledgers
        modelMapper.createTypeMap(LedgersEntryDTO.class, Ledgers.class)
            .addMapping(LedgersEntryDTO::getDate, Ledgers::setDate)
            .addMapping(LedgersEntryDTO::getDescription, Ledgers::setDescription)
            .addMappings(mapper -> mapper.skip(Ledgers::setCreatedAt))
            .addMappings(mapper -> mapper.skip(Ledgers::setUpdatedAt))
            .addMappings(mapper -> mapper.skip(Ledgers::setId))
            .addMappings(mapper -> mapper.skip(Ledgers::setOwnerId));
            // Note1: The CreatedAt and UpdatedAt is set in the custom mapper layer.
            // Note2: The ID is set in the service layer.
            
        modelMapper.validate();
        return modelMapper;
    }
}