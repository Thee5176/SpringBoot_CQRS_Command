package com.thee5176.ledger_command.record.application.controller;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.thee5176.ledger_command.record.application.dto.LedgersEntryDTO;
import com.thee5176.ledger_command.record.application.exception.ValidationException;
import com.thee5176.ledger_command.record.domain.service.LedgerCommandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
@RequestMapping("/ledger")
@AllArgsConstructor
public class LedgersController {
    private final LedgerCommandService ledgerCommandService;

    @PostMapping
    public ResponseEntity<String> newLedger(@AuthenticationPrincipal Jwt jwt, @RequestBody @Validated LedgersEntryDTO ledgersEntryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getAllErrors());
            throw new ValidationException("Validation failed: " + bindingResult.getAllErrors());
        }

        String userId = jwt.getSubject(); // Get user ID from 'sub' claim
        ledgerCommandService.createLedger(ledgersEntryDTO, userId);
        log.debug("New ledger created: {} for user: {}", ledgersEntryDTO, userId);
        return ResponseEntity.ok("Successfully created new ledger");
    }

    @PutMapping
    public ResponseEntity<String> updateLedger(@AuthenticationPrincipal Jwt jwt, @RequestBody @Validated LedgersEntryDTO ledgersEntryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getAllErrors());
            throw new ValidationException("Validation failed: " + bindingResult.getAllErrors());
        }
        
        String userId = jwt.getSubject();
        ledgerCommandService.updateLedger(ledgersEntryDTO, userId);
        log.debug("Ledger updated: {} for user: {}", ledgersEntryDTO, userId);

        return ResponseEntity.ok("Successfully updated ledger");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLedger(@AuthenticationPrincipal Jwt jwt, @RequestParam UUID uuid) {
        String userId = jwt.getSubject();
        ledgerCommandService.deleteLedger(uuid, userId);
        log.debug("Ledger deleted: {} for user: {}", uuid, userId);

        return ResponseEntity.ok("Successfully deleted ledger");
    }
}