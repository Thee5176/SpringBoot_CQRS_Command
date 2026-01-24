package com.thee5176.ledger_command.record.domain.service;

import java.util.List;
import java.util.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.application.mapper.LedgerItemsMapper;
import com.thee5176.ledger_command.record.application.mapper.LedgerMapper;
import com.thee5176.ledger_command.record.domain.model.tables.pojos.LedgerItems;
import com.thee5176.ledger_command.record.domain.model.tables.pojos.Ledgers;
import com.thee5176.ledger_command.record.infrastructure.repository.LedgerItemsRepository;
import com.thee5176.ledger_command.record.infrastructure.repository.LedgerRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class LedgerCommandService {
    private final LedgerRepository ledgerRepository;

    private final LedgerItemsRepository ledgerItemRepository;

    private final LedgerMapper ledgerMapper;

    private final LedgerItemsMapper ledgerItemsMapper;

    @Transactional
    public void createLedger(LedgersEntryDTO ledgersEntryDTO, @NotNull String userId) {
        final UUID ledgerUuid = UUID.randomUUID();

        // 取引作成stream
        Ledgers ledger = ledgerMapper.map(ledgersEntryDTO)
                .setId(ledgerUuid)
                .setOwnerId(userId);

        ledgerRepository.createLedger(ledger);
        log.debug("Ledger created: {}", ledger);

        // 取引行別作成stream
        List<LedgerItems> ledgerItemsList = ledgerItemsMapper.map(ledgersEntryDTO);

        ledgerItemsList.stream()
                .forEach(ledgerItem -> {
                    ledgerItem.setId(UUID.randomUUID());
                    ledgerItem.setLedgerId(ledgerUuid);
                    log.debug("ledgerItem created: {}", ledgerItem);
                    ledgerItemRepository.createLedgerItems(ledgerItem);
                });
    }

    @Transactional
    public void updateLedger(LedgersEntryDTO ledgersEntryDTO, @NotNull String ownerId) {
        // check if owner owned the transaction
        if (!ledgerRepository.existsByIdAndOwnerId(ledgersEntryDTO.getId(), ownerId)) {
            log.warn("User {} attempted to update ledger {} they do not own", ownerId, ledgersEntryDTO.getId());
            throw new AccessDeniedException("You do not have permission to update this ledger.");
        }

        // 1. Update main Ledger entry
        Ledgers ledger = ledgerMapper.map(ledgersEntryDTO)
                .setOwnerId(ownerId);
        ledgerRepository.updateLedger(ledger);
        log.debug("Ledger updated: {}", ledger);

        // 2. Clear existing items
        ledgerItemRepository.deleteLedgerItemsByLedgerId(ledgersEntryDTO.getId());
        log.debug("Existing ledger items deleted for ledger: {}", ledgersEntryDTO.getId());

        // 3. Insert new items
        List<LedgerItems> ledgerItemsList = ledgerItemsMapper.map(ledgersEntryDTO);
        ledgerItemsList.forEach(ledgerItem -> {
            ledgerItem.setId(UUID.randomUUID());
            ledgerItem.setLedgerId(ledgersEntryDTO.getId());
            log.debug("New ledger item inserted during update: {}", ledgerItem);
            ledgerItemRepository.createLedgerItems(ledgerItem);
        });
    }

    @Transactional
    public void deleteLedger(UUID uuid, @NotNull String ownerId) {
        // check if owner owned the transaction
        if (!ledgerRepository.existsByIdAndOwnerId(uuid, ownerId)) {
            log.warn("User {} attempted to delete ledger {} they do not own", ownerId, uuid);
            throw new AccessDeniedException("You do not have permission to delete this ledger.");
        }

        // cascade delete apply in DB layer
        ledgerRepository.deleteLedger(uuid);
        log.info("User {} deleted Ledger {}", ownerId, uuid);
    }
}
