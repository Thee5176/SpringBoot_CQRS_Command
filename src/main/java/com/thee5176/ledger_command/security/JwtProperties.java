package com.thee5176.ledger_command.security;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
public class JwtProperties {
    private String issuerUri;
    private List<String> audiences;

    public String getIssuerUri() {
        return issuerUri;
    }

    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    public List<String> getAudiences() {
        return audiences;
    }

    public void setAudiences(List<String> audiences) {
        this.audiences = audiences;
    }
}