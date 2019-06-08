package me.vitblokhin.backend.config;

import me.vitblokhin.backend.security.jwt.JwtConfigurer;
import me.vitblokhin.backend.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint entryPointUnauthorizedHandler;

    @Value("${rest-api.url}")
    private String API_URL;
    private final String LOGIN_ENDPOINT = "/auth/login";
    private final String ADMIN_ENDPOINT = "/admin/**";
    private final String PROTECTED_ENDPOINT = "/protected/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, AuthenticationEntryPoint entryPointUnauthorizedHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.entryPointUnauthorizedHandler = entryPointUnauthorizedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.entryPointUnauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/*.js", API_URL + LOGIN_ENDPOINT).permitAll()
                .antMatchers(API_URL).permitAll()
                .antMatchers(HttpMethod.POST, API_URL + "/user").anonymous()
                .antMatchers(API_URL + ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(API_URL + PROTECTED_ENDPOINT).hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

} // class SecurityConfig
