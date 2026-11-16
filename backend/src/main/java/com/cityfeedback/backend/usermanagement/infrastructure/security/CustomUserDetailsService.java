
package com.cityfeedback.backend.usermanagement.infrastructure.security;

import com.cityfeedback.backend.usermanagement.domain.EmailAddress;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        EmailAddress emailAddress = new EmailAddress(email);

        User user = userRepository.findByEmail(emailAddress)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail().value())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .disabled(user.getAccountStatus()
                        != com.cityfeedback.backend.usermanagement.domain.AccountStatus.ACTIVE)
                .build();
    }
}
