package com.thee5176.ledger_command.record.infrastructure.repository;

import java.util.List;
import java.util.UUID;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import com.thee5176.ledger_command.record.application.exception.JooqOperationException;
import com.thee5176.ledger_command.record.domain.model.accounting.Tables;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItems;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@AllArgsConstructor
public class LedgerItemsRepository {
    
    private final DSLContext dslContext;

    public void createLedgerItems(LedgerItems ledgerItems) {
        try {
            dslContext.insertInto(Tables.LEDGER_ITEMS, Tables.LEDGER_ITEMS.ID, Tables.LEDGER_ITEMS.LEDGER_ID, Tables.LEDGER_ITEMS.COA, Tables.LEDGER_ITEMS.AMOUNT, Tables.LEDGER_ITEMS.TYPE, Tables.LEDGER_ITEMS.CREATED_AT, Tables.LEDGER_ITEMS.UPDATED_AT)
                .values(ledgerItems.getId(), ledgerItems.getLedgerId(), ledgerItems.getCoa(), ledgerItems.getAmount(), ledgerItems.getType(), ledgerItems.getCreatedAt(), ledgerItems.getUpdatedAt())
                .execute();
        } catch (Exception e) {
            log.error("Error creating ledger items", e);
            throw new JooqOperationException("Failed to create LedgerItems");
        }
    }

        public void updateLedgerItems(LedgerItems ledgerItems) {
            try {
            dslContext.update(Tables.LEDGER_ITEMS)
                .set(Tables.LEDGER_ITEMS.LEDGER_ID, ledgerItems.getLedgerId())
                .set(Tables.LEDGER_ITEMS.AMOUNT, ledgerItems.getAmount())
                .set(Tables.LEDGER_ITEMS.COA, ledgerItems.getCoa())
                .set(Tables.LEDGER_ITEMS.TYPE, ledgerItems.getType())
                .set(Tables.LEDGER_ITEMS.UPDATED_AT, ledgerItems.getUpdatedAt())
                .where(Tables.LEDGER_ITEMS.ID.eq(ledgerItems.getId()))
                .execute();
        } catch (Exception e) {
            log.error("Error updating ledger items", e);
            throw new JooqOperationException("Failed to update LedgerItems");
        }
    }

    public void deleteLedgerItems(UUID uuid) {
        try {
            dslContext.delete(Tables.LEDGER_ITEMS)
                .where(Tables.LEDGER_ITEMS.ID.eq(uuid))
                .execute();
        } catch (Exception e) {
            log.error("Error deleting ledger items", e);
            throw new JooqOperationException("Failed to delete LedgerItems");
        }
    }

    public List<LedgerItems> getLedgerItemsByLedgerId(UUID ledgerId) {
        return dslContext.selectFrom(Tables.LEDGER_ITEMS)
            .where(Tables.LEDGER_ITEMS.LEDGER_ID.eq(ledgerId))
                .fetchInto(LedgerItems.class);
    }

    public LedgerItems getLedgerItems(UUID uuid) {
        return dslContext.selectFrom(Tables.LEDGER_ITEMS)
            .where(Tables.LEDGER_ITEMS.ID.eq(uuid))
                .fetchOneInto(LedgerItems.class);
    }
}
