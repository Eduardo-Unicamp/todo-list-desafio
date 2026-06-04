package com.saldanha.todo_list.service;

import com.saldanha.todo_list.dtos.AuthenticationDTO;
import com.saldanha.todo_list.dtos.AuthenticationResponseDTO;
import com.saldanha.todo_list.dtos.RegisterDTO;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.infra.security.TokenService;
import com.saldanha.todo_list.infra.security.UserDetailsImp;
import com.saldanha.todo_list.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;



    public AuthenticationResponseDTO authenticate(AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(),
                authenticationDTO.getPassword()
        );

        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken((UserDetailsImp) auth.getPrincipal());
        return new AuthenticationResponseDTO(token);

    }
    public ResponseEntity<Object> register(RegisterDTO registerDTO){
        if(userRepository.findByUsername(registerDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Nome de usuário indisponível");
        }
        if(userRepository.findByEmail(registerDTO.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("O email informado já está em uso!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());

        User newUser = new User();
        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(registerDTO.getRole());

        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
