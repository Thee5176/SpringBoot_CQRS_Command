package com.thee5176.ledger_command.record.application.controller;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.thee5176.ledger_command.record.domain.model.accounting.tables.pojos.LedgerItems;
import com.thee5176.ledger_command.record.infrastructure.repository.LedgerItemsRepository;
import lombok.AllArgsConstructor;




@RestController
@RequestMapping("/ledger_items")
@AllArgsConstructor
public class LedgerItemsController {
    private final LedgerItemsRepository ledgerItemsRepository;

    @PostMapping
    public ResponseEntity<String> createLedgerItems(@RequestBody LedgerItems ledgerItems) {
        ledgerItemsRepository.createLedgerItems(ledgerItems);
        
        return ResponseEntity.ok("created ledgerItems succesfully");
    }
    
    @PutMapping
    public ResponseEntity<String> updateLedgerItems(@RequestBody LedgerItems ledgerItems) {
        ledgerItemsRepository.updateLedgerItems(ledgerItems);

        return ResponseEntity.ok("updated ledgerItems succesfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLedgerItems(@RequestParam UUID uuid) {
        ledgerItemsRepository.deleteLedgerItems(uuid);

        return ResponseEntity.ok("deleted ledgerItems succesfully");
    }
}
