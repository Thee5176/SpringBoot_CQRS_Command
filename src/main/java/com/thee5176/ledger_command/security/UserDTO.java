package com.thee5176.ledger_command.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Deprecated(since = "1.2.0", forRemoval = true)
public class UserDTO implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
