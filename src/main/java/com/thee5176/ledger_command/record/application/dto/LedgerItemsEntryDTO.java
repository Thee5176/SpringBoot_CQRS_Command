package com.thee5176.ledger_command.record.application.dto;

import com.thee5176.ledger_command.record.domain.model.accounting.enums.BalanceType;
import io.swagger.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerItemsEntryDTO{

    Integer coa;
    Double amount;
    BalanceType balanceType;

    @Schema(hidden = true)
    public Double getBalance() {
        return BalanceType.Debit.equals(balanceType) ? amount : amount * -1;
    }
}