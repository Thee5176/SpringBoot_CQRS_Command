package com.thee5176.ledger_command.record.domain.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.application.mapper.LedgerItemsMapper;
import com.thee5176.ledger_command.record.application.mapper.LedgerMapper;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItems;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.Ledgers;
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
        
        List<LedgerItems> existingLedgerItemsList = ledgerItemRepository.getLedgerItemsByLedgerId(ledgersEntryDTO.getId());
        List<LedgerItems> ledgerItemsUpdateList = ledgerItemsMapper.map(ledgersEntryDTO);
        
        // Check Already Exist By COA : UpdateList -> COA
        Map<Integer, LedgerItems> existingItemsByCoa = existingLedgerItemsList.stream()
            .collect(Collectors.toMap(LedgerItems::getCoa, Function.identity()));

        // Stream1# : Assign ID to LedgerItems -> Update LedgerItems
        // Stream1# : 一致する勘定科目コード -> IDをLedgerItems割り当て -> LedgerItemsを更新
        ledgerItemsUpdateList.stream()
            .filter(existingItem -> existingItemsByCoa.containsKey(existingItem.getCoa()))
            .forEach(itemToUpdate -> {
                LedgerItems existingItem = existingItemsByCoa.get(itemToUpdate.getCoa());
                itemToUpdate.setId(existingItem.getId());
                itemToUpdate.setLedgerId(existingItem.getLedgerId());
                log.debug("Ledger item updated : {}", itemToUpdate);
                ledgerItemRepository.updateLedgerItems(itemToUpdate);
            });

        // Stream2# : Create new LedgerItems that no in Stream#1 (Id is still null)
        // Stream2# : Stream#1で見つからなかったLedgerItemsを作成
        ledgerItemsUpdateList.stream().forEach(ledgerItems -> {
            if (ledgerItems.getId() == null) {
                ledgerItems.setId(UUID.randomUUID());
                ledgerItems.setLedgerId(ledgersEntryDTO.getId());
                log.debug("New ledger item created: {}", ledgerItems);
                ledgerItemRepository.createLedgerItems(ledgerItems);
            }
        });

        // Stream3# : Delete ledger items that are not in the updated list
        // Stream3# : 更新リストに存在しないLedgerItemsを削除
        existingLedgerItemsList.stream()
            .filter(existingItem -> !ledgerItemsUpdateList.contains(existingItem))
            .forEach(itemToDelete -> {
                ledgerItemRepository.deleteLedgerItems(itemToDelete.getId());
                log.debug("Ledger item deleted: {}", itemToDelete);
            }
        );
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
