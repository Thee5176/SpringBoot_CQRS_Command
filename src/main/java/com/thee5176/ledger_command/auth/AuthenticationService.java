package com.thee5176.ledger_command.auth;

import java.util.Random;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.thee5176.ledger_command.security.CustomUserDetailsService;
import com.thee5176.ledger_command.security.JOOQUsersRepository;
import com.thee5176.ledger_command.security.JwtService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JOOQUsersRepository usersRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        // encode the password
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // TODO: Add error handling for duplicate information
        if (request.getId() == null) {
            Long userId = Long.valueOf(new Random().nextInt(9999 + 1)); // Generates id with number between 1 and 9999
            request.setId(userId);
        }
        usersRepository.createUser(request);
        
        // Create UserDetails object
        var user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .roles("USER")
                .build();
        // Access Token
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()
            )
        );

        UserDetails user = customUserDetailsService.loadUserByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(user);
        
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public static Long generateRandomId() {
        return Long.valueOf(new Random().nextInt(9000)); // Generates a number between 1 and 9999
    }
}
