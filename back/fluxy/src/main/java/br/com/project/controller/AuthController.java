package br.com.project.controller;

import br.com.project.dto.auth.LoginRequestDTO;
import br.com.project.dto.auth.RegisterRequestDTO;
import br.com.project.dto.auth.RegisterResponseDTO;
import br.com.project.model.User;
import br.com.project.repository.UserRepository;
import br.com.project.service.UserService;
import br.com.project.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        if (!body.password().equals(body.confirmPassword())) {
            return ResponseEntity.badRequest().body("As senhas não coincidem");
        }

        Optional<User> existingUser = userRepository.findByName(body.name());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado");
        }

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setPassword(passwordEncoder.encode(body.password()));

        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok(new RegisterResponseDTO(token, newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO body) {
        String name = body.name();
        String password = body.password();

        return userService.authenticate(name, password)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos"));
    }
}
