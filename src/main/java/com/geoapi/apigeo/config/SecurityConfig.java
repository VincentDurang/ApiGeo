package com.geoapi.apigeo.config;

import com.geoapi.apigeo.security.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    // Configuration de la sécurité
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/countries/all").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/countries/research/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/countries/add").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/countries/update/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/countries/delete/{id}").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(httpBasicCustomizer -> {});
        return http.build();
    }

    // Configuration de l'authentification
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    // Configuration de l'encodage des mots de passe
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
