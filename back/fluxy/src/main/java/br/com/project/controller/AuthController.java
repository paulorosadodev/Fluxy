package br.com.project.controller;

import br.com.project.dto.auth.LoginRequestDTO;
import br.com.project.dto.auth.RegisterRequestDTO;
import br.com.project.dto.auth.RegisterResponseDTO;
import br.com.project.model.Store;
import br.com.project.repository.StoreRepository;
import br.com.project.service.StoreService;
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
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        if (!body.password().equals(body.confirmPassword())) {
            return ResponseEntity.badRequest().body("As senhas não coincidem");
        }

        Optional<Store> existingStore = storeRepository.findByName(body.name());

        if (existingStore.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Loja já cadastrada");
        }

        Store newStore = new Store();
        newStore.setName(body.name());
        newStore.setPassword(passwordEncoder.encode(body.password()));

        storeRepository.save(newStore);

        String token = tokenService.generateToken(newStore);
        return ResponseEntity.ok(new RegisterResponseDTO(token, newStore));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO body) {
        String name = body.name();
        String password = body.password();

        return storeService.authenticate(name, password)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos"));
    }
}
