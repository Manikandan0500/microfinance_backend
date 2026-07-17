package com.bbots.mfin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bbots.mfin.repository.SecurityRepository;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> userData = repository.findUserForAuth(username);

        if (userData == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new User(
                (String) userData.get("username"),
                (String) userData.get("password"), // In production, this should be BCrypt encoded
                Collections.singletonList(new SimpleGrantedAuthority((String) userData.get("roles"))));
    }
}
