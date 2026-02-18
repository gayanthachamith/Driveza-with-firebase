package com.example.drivezawithfirebase.config;


import com.example.drivezawithfirebase.security.FirebaseTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.google.api.AnnotationsProto.http;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // public pages
                        .requestMatchers("/", "/login", "/signup", "/map").permitAll()

                        // static
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // API must be authenticated using Firebase token
                        .requestMatchers("/api/**").authenticated()

                        .anyRequest().permitAll()
                )

                // keep form login for thymeleaf if you want, but it won't be used for /api/**
                .formLogin(Customizer.withDefaults())

                // API should return 401 not redirect
                .exceptionHandling(ex -> ex
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                request -> request.getRequestURI().startsWith("/api/")
                        )
                )

                // Firebase filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(new FirebaseTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}