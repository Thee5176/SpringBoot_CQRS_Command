package com.thee5176.ledger_command.security;

import static org.springframework.security.config.Customizer.withDefaults;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Value("${okta.oauth2.issuer}")
    private String issuer;
    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/health").permitAll()
            .anyRequest().authenticated());

        http.logout(logout -> logout.logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/logout"))
            .addLogoutHandler(oidclogoutHandler()));
    
        http.oauth2Login(withDefaults());

        // audience validation is handled by Default OAuth2TokenValidators
        http.oauth2ResourceServer(oauth2 -> oauth2
            .jwt(withDefaults()));

        return http.build();
    }

    private LogoutHandler oidclogoutHandler() {
        return (request, response, authentication) -> {
            try {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .build()
                    .toUriString();

                String issuerBase = issuer.endsWith("/") ? issuer : issuer + "/";

                String logoutUrl = UriComponentsBuilder
                    .fromHttpUrl(issuerBase)
                    .path("v2/logout")
                    .queryParam("client_id", clientId)
                    .queryParam("returnTo", baseUrl)
                    .build()
                    .toUriString();

                response.sendRedirect(logoutUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}