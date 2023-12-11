package com.api.parkingcontrol.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic((httpB) -> {

                })
                .authorizeHttpRequests((aHttpR) -> {
                    aHttpR
                            // .requestMatchers(HttpMethod.GET, "/api/parking-spot/**").permitAll()
                            // .requestMatchers(HttpMethod.POST, "/api/parking-spot/**").hasRole("USER")
                            // .requestMatchers(HttpMethod.DELETE, "/api/parking-spot/**").hasRole("ADMIN")
                            .anyRequest()
                            .authenticated();
                })
                .csrf((cr) -> {
                    cr.disable();
                });

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
