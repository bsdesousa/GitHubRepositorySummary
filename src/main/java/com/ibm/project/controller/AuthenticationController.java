package com.ibm.project.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.project.configuration.jwt.JwtTokenProvider;
import com.ibm.project.model.User;

/**
 * Classe responsável por implementar o rest de autenticação da aplicação.
 * 
 * @author bruno
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("")
    public ResponseEntity signin(@RequestBody User usuario) {

        try {
            String username = usuario.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, usuario.getPassword()));
            String token = jwtTokenProvider.createToken(username, Arrays.asList("admin"));

            Map<Object, Object> model = new HashMap<Object, Object>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Usuário ou senha inválida!");
        }
    }
}
