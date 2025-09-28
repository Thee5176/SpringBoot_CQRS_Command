package com.thee5176.ledger_command.record.application.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.thee5176.ledger_command.record.domain.model.accounting.enums.BalanceType;

public class LedgerItemsEntryDTOTest {

    // Test data for a debit ledgerItems (e.g., for an asset or expense account)
    // Arrange
    final Integer coaDebit = 1101; // Example Chart of Accounts code for "Cash"
    final Double amountDebit = 250.50;
    final BalanceType typeDebit = BalanceType.Debit;

    //Act
    final LedgerItemsEntryDTO debitLedgerItems = new LedgerItemsEntryDTO(coaDebit, amountDebit, typeDebit);
    
    
    // Test data for a credit ledgerItems (e.g., for a liability, equity, or revenue account)
    // Arrange
    final Integer coaCredit = 4101; // Example Chart of Accounts code for "Sales Revenue"
    final Double amountCredit = 250.50;
    final BalanceType typeCredit = BalanceType.Credit;
    
    //Act
    final LedgerItemsEntryDTO creditLedgerItems = new LedgerItemsEntryDTO(coaCredit, amountCredit, typeCredit);

    @Test
    void canCreateDebitLedgerItems() {
        //Assert
        assertNotNull(debitLedgerItems);
        assertEquals(coaDebit, debitLedgerItems.getCoa());
        assertEquals(amountDebit, debitLedgerItems.getAmount());
        assertEquals(typeDebit, debitLedgerItems.getBalanceType());
    }

    @Test
    void canCreateCreditLedgerItems() {
        //Assert
        assertNotNull(creditLedgerItems);
        assertEquals(coaCredit, creditLedgerItems.getCoa());
        assertEquals(amountCredit, creditLedgerItems.getAmount());
        assertEquals(typeCredit, creditLedgerItems.getBalanceType());
    }

    @Test
    void testGetBalanceMethod() {
        //Assert
        assertEquals(amountDebit, debitLedgerItems.getBalance());
        assertEquals(amountCredit  * -1, creditLedgerItems.getBalance());
    }

    public static LedgerItemsEntryDTO createOneSampleLedgerItemsEntryDTO() {
        return new LedgerItemsEntryDTO(1101, 100.0, BalanceType.Debit);
    }

    public static List<LedgerItemsEntryDTO> createBalancedSampleLedgerItemsEntryDTO() {
        // This creates a balanced ledger with one debit and one credit
        final LedgerItemsEntryDTO debitLedgerItems = new LedgerItemsEntryDTO(1101, 100.0, BalanceType.Debit);
        final LedgerItemsEntryDTO creditLedgerItems = new LedgerItemsEntryDTO(4101, 100.0, BalanceType.Credit);

        return List.of(debitLedgerItems, creditLedgerItems);
    }

    public static List<LedgerItemsEntryDTO> createImalancedSampleLedgerItemsEntryDTO() {
        // This creates an imbalanced ledger with one debit and one credit
        final LedgerItemsEntryDTO debitLedgerItems = new LedgerItemsEntryDTO(1101, 0.0, BalanceType.Debit);
        final LedgerItemsEntryDTO creditLedgerItems = new LedgerItemsEntryDTO(4101, 100.0, BalanceType.Credit);
        
        return List.of(debitLedgerItems, creditLedgerItems);
    }
}