package com.example.space_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@org.springframework.context.annotation.Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())  // Authorize all requests
                .httpBasic(withDefaults())  // Use the new withDefaults() method for HTTP Basic
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);  // Optional: Enables default form login
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("user")
                .password("{noop}password")  // {noop} means no password encoder (plaintext for demo purposes)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
