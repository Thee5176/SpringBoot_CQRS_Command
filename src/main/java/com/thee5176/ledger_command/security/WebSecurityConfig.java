package com.thee5176.ledger_command.security;

import static org.springframework.security.config.Customizer.withDefaults;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Value("${okta.oauth2.issuer}")
    private String issuerUri;
    @Value("${okta.oauth2.client-id}")
    private String clientId;

    private final JwtProperties jwtProperties;


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/health").permitAll()
            .anyRequest().authenticated());

        http.logout(logout -> logout.logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/logout"))
            .addLogoutHandler(oidclogoutHandler()));
    
        http.oauth2Login(withDefaults());

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(jwtProperties.getIssuerUri());

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(jwtProperties.getAudiences());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(jwtProperties.getIssuerUri());
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }

    private LogoutHandler oidclogoutHandler() {
        return (request, response, authentication) -> {
            try {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                response.sendRedirect(issuerUri + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}