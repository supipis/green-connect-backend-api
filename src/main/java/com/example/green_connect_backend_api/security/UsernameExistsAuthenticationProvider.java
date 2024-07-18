package com.example.green_connect_backend_api.security;

import com.example.green_connect_backend_api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UsernameExistsAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    public UsernameExistsAuthenticationProvider(UserRepository userService) {
        this.userRepository = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        if (userRepository.existsByUsername(username)) {
            return new UsernamePasswordAuthenticationToken(username, null, null);
        } else {
            // Handle username not found
            throw new UsernameNotFoundException("Username not found");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}