package com.geoapi.apigeo.controller;

import com.geoapi.apigeo.model.UserEntity;
import com.geoapi.apigeo.security.JwtResponse;
import com.geoapi.apigeo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired
    private final AuthenticationManager authenticationManager;


    JwtUtils jwtUtils;

    // Constructor
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {

        // Création de l'objet d'authentification
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        // Authentification
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        // Mise à jour du contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        // Génération du token
        String jwt = jwtUtils.generateJwtToken(authenticationResponse);

        // Récupération des informations de l'utilisateur
        UserEntity userEntity = (UserEntity) authenticationResponse.getPrincipal();

        // Récupération des rôles
        List<String> roles = userEntity.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Retourne le token
        return ResponseEntity.ok(new JwtResponse(jwt,
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                roles));
    }


    // Classe interne
    public record LoginRequest(String username, String password) {
    }

}
