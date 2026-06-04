package com.saldanha.todo_list.controller;

import com.saldanha.todo_list.dtos.AuthenticationDTO;
import com.saldanha.todo_list.dtos.AuthenticationResponseDTO;
import com.saldanha.todo_list.dtos.RegisterDTO;
import com.saldanha.todo_list.infra.security.TokenService;
import com.saldanha.todo_list.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;//do outro tutorial


    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        AuthenticationResponseDTO token = authenticationService.authenticate(authenticationDTO);//call my authentication service
        return ResponseEntity.ok().body(token);


    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO){
        return authenticationService.register(registerDTO);
    }


}
