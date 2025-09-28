package com.thee5176.ledger_command.record.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;
import com.thee5176.ledger_command.record.application.validation.BalanceCheck;
import io.swagger.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgersEntryDTO {
    @Nullable
    UUID id;

    @PastOrPresent(message = "Date must be in the past or present.")
    LocalDate date;

    @NotBlank
    String description;

    @BalanceCheck
    @Schema(implementation = LedgerItemsEntryDTO.class)
    List<LedgerItemsEntryDTO> ledgerItems;
    
    @PastOrPresent(message = "Timestamp must be in the past or present.")
    LocalDateTime timestamp;

    @UniqueElements
    @Schema(hidden = true)
    public List<Integer> getCoaList() {
        return ledgerItems.stream()
                .map(LedgerItemsEntryDTO::getCoa)
                .toList();
    }
}