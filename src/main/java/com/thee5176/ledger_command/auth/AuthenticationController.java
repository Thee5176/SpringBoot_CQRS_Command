package com.thee5176.ledger_command.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@Deprecated(since = "1.2.0", forRemoval = true)
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	private final AuthenticationService authenticationService;

    @PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

		return ResponseEntity.ok(authenticationService.register(request));
	}


    @PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {		
		
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}

}

record LoginRequest(String username, String password) {}