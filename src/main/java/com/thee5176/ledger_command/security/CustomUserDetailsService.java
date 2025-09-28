package com.thee5176.ledger_command.security;

import java.util.List;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.thee5176.ledger_command.record.domain.model.credential.tables.pojos.Authorities;
import com.thee5176.ledger_command.record.domain.model.credential.tables.pojos.Users;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final JOOQUsersRepository usersRepository;
    private final JOOQAuthoritiesRepository authoritiesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.fetchUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<Authorities> authorities = authoritiesRepository.getAuthoritiesByUsername(username);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities.stream().map(Authorities::getAuthority).toArray(String[]::new))
                .accountExpired(!user.getIsAccountNonExpired())
                .accountLocked(!user.getIsAccountNonLocked())
                .credentialsExpired(!user.getIsCredentialsNonExpired())
                .disabled(!user.getIsEnabled())
                .build();
    }
    
}