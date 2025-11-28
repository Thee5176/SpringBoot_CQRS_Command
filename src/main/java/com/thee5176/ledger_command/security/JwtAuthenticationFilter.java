package com.thee5176.ledger_command.security;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Deprecated(since = "1.2.0", forRemoval = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull FilterChain filterChain
    ) throws IOException, ServletException {
        // Skip authentication for auth endpoints (so login/refresh endpoints are not blocked)
        String path = request.getServletPath();
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method) || path != null && path.startsWith("/api/v1/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        
        // (Check 1) Header contains Valid JWT Token in the Authorization Header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String jwt = authHeader.substring(7);
        String userName;
        try {
            userName = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Invalid token or parsing error — do not throw, continue as anonymous
            LOGGER.debug("Failed to extract username from JWT: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // (Check 2) User is not already authenticated
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

                // (Check 3) Token is valid
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // 2. Authenticate the user into Spring Security Context
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities()
                    );
                    
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                LOGGER.debug("JWT authentication skipped : {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}