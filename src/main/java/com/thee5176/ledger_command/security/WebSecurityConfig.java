package com.thee5176.ledger_command.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * This class contains a method that is no longer recommended for use.
 *
 * @deprecated As of version 1.2, this method is superseded by {@link #newMethod()}.
 *             The reason for deprecation is improved performance and clarity in the new approach.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Deprecated(since = "1.2.0", forRemoval = true)
public class WebSecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final JOOQUsersRepository usersRepository;
	private final JOOQAuthoritiesRepository authoritiesRepository;

    @Bean
	@Deprecated(since = "1.2.0", forRemoval = true)
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	@Deprecated(since = "1.2.0", forRemoval = true)
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	@Deprecated(since = "1.2.0", forRemoval = true)
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	@Deprecated(since = "1.2.0", forRemoval = true)
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		return new JwtAuthenticationFilter(jwtService, userDetailsService);
	}

	@Bean
	@Deprecated(since = "1.2.0", forRemoval = true)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		// Allow request path unauthenticated access and disable CSRF for simplicity
		http
			.csrf((csrf -> csrf.disable()))
			.authorizeHttpRequests(auth -> 
				auth.requestMatchers("/api/v1/auth/login","/api/v1/auth/register", "/error").permitAll()
				.requestMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated()
				)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authenticationProvider(daoAuthenticationProvider())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	@Deprecated(since = "1.2.0", forRemoval = true)
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService(usersRepository, authoritiesRepository);
	}
}