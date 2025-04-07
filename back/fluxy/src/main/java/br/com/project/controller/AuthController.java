package br.com.project.controller;

import br.com.project.dto.LoginRequest;
import br.com.project.dto.RegisterRequest;
import br.com.project.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LojaService lojaService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        boolean registrado = lojaService.registrar(
                request.getId(),
                request.getNome(),
                request.getSenha()
        );

        // System.out.println("entrou");
        if (!registrado) {
            return ResponseEntity
                    .badRequest()
                    .body("Nome já existente em outra loja!");
        }

        return ResponseEntity.ok("Registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<String> tokenOptional = lojaService.autenticar(
                request.getNome(),
                request.getSenha()
        );

        if (tokenOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais Inválidas.");
        }

        String token = tokenOptional.get();

        return ResponseEntity.ok(Map.of("token", token));
    }
}
