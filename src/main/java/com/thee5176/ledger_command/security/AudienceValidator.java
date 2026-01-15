package com.thee5176.ledger_command.security;

import java.util.List;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final List<String> allowedAudiences;

    public AudienceValidator(List<String> allowedAudiences) {
        this.allowedAudiences = allowedAudiences;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        List<String> audiences = jwt.getAudience();
        
        // Check if any of the JWT audiences match our allowed audiences
        for (String audience : audiences) {
            if (allowedAudiences.contains(audience)) {
                return OAuth2TokenValidatorResult.success();
            }
        }
        
        OAuth2Error error = new OAuth2Error(
            "invalid_token",
            "The required audience is missing",
            null
        );
        return OAuth2TokenValidatorResult.failure(error);
    }
}