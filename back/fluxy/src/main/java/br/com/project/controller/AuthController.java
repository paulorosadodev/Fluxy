package br.com.project.controller;

import br.com.project.dto.LoginRequest;
import br.com.project.dto.RegisterRequest;
import br.com.project.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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

        System.out.println("entrou");
        if (!registrado) {
            return ResponseEntity
                    .badRequest()
                    .body("Loja com esse nome já existe.");
        }

        return ResponseEntity.ok("Registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        boolean autenticado = lojaService.autenticar(
                request.getNome(),
                request.getSenha()
        );

        if (!autenticado) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas.");
        }

        return ResponseEntity.ok("Login realizado com sucesso!");
    }
}
