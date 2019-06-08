package me.vitblokhin.backend.controller;

import lombok.extern.log4j.Log4j2;
import me.vitblokhin.backend.dto.AuthenticationRequestDto;
import me.vitblokhin.backend.dto.AuthenticationResponseDto;
import me.vitblokhin.backend.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("${rest-api.auth-url}")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService jwtUserDetailsService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                        JwtTokenProvider jwtTokenProvider,
                                        UserDetailsService jwtUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            log.info("Login attempt --- username: " + requestDto.getUsername());
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

            String token = jwtTokenProvider.createToken(userDetails);

            AuthenticationResponseDto authenticationResponse = new AuthenticationResponseDto();
            authenticationResponse.setUsername(username);
            authenticationResponse.setToken(token);

            log.info("Login succeed --- username: " + requestDto.getUsername());
            return ResponseEntity.ok(authenticationResponse);
        } catch (AuthenticationException e) {
            log.info("Login failed --- username: " + requestDto.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }

    }
} // class AuthenticationController
