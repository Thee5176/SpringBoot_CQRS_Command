package com.thee5176.ledger_command.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Deprecated(since = "1.2.0", forRemoval = true)
public class AuthenticationResponse {
    private String token;
}
