package me.vitblokhin.backend.security;

import lombok.extern.log4j.Log4j2;
import me.vitblokhin.backend.model.User;
import me.vitblokhin.backend.repository.UserRepository;
import me.vitblokhin.backend.security.jwt.JwtUser;
import me.vitblokhin.backend.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service("userDetailsService")
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            log.info("UserDetailsService: user with username {} not found", username);
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(optUser.get());
        log.info("UserDetailsService: user with username {} successfully loaded", username);
        return jwtUser;
    }
} // class JwtUserDetailsService
