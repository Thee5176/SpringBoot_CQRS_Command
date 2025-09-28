package com.thee5176.ledger_command.record.application.validation;

import java.math.BigDecimal;
import java.util.List;
import com.thee5176.ledger_command.record.application.dto.LedgerItemsEntryDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class BalanceCheckValidator implements ConstraintValidator<BalanceCheck, List<LedgerItemsEntryDTO>> {
    @Override
    public void initialize(BalanceCheck balanceCheck) {}
	
    @Override
    public boolean isValid(List<LedgerItemsEntryDTO> ledgerItems, ConstraintValidatorContext context) {
		return ledgerItems.stream()
			.map(item -> item.getBalance())
			.map(BigDecimal::new)
			.reduce(BigDecimal.ZERO, BigDecimal::add)
			.compareTo(BigDecimal.ZERO) == 0;
	}
}
