package com.thee5176.ledger_command.record.application.controller;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
@RequestMapping("/ledger")
@AllArgsConstructor
public class LedgerController {
    private final LedgerCommandService ledgerCommandService;

    @PostMapping
    public ResponseEntity<String> newLedger(@NotNull @AuthenticationPrincipal UserDetails userDetails, @RequestBody @Validated LedgersEntryDTO ledgersEntryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getAllErrors());
            throw new ValidationException("Validation failed: " + bindingResult.getAllErrors());
        }

        ledgerCommandService.createLedger(ledgersEntryDTO, userDetails.getUsername());
        log.debug("New ledger created: {}", ledgersEntryDTO, userDetails.getUsername());

        return ResponseEntity.ok("Successfully created new ledger");
    }

    @PutMapping
    public ResponseEntity<String> updateLedger(@NotNull @AuthenticationPrincipal UserDetails userDetails, @RequestBody @Validated LedgersEntryDTO ledgersEntryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getAllErrors());
            throw new ValidationException("Validation failed: " + bindingResult.getAllErrors());
        }
        
        ledgerCommandService.updateLedger(ledgersEntryDTO, userDetails.getUsername());
        log.debug("Ledger updated: {}", ledgersEntryDTO);

        return ResponseEntity.ok("Successfully updated ledger");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLedger(@NotNull @AuthenticationPrincipal UserDetails userDetails, @RequestParam UUID uuid) {
        ledgerCommandService.deleteLedger(uuid, userDetails.getUsername());
        log.debug("Ledger deleted: {}", uuid);

        return ResponseEntity.ok("Successfully deleted ledger");
    }
}   