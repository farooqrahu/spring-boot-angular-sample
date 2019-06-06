package me.vitblokhin.backend.security.jwt;

import me.vitblokhin.backend.enums.Status;
import me.vitblokhin.backend.model.Role;
import me.vitblokhin.backend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdatedAt(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles){
        return roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());
    }
} // class JwtUserFactory
