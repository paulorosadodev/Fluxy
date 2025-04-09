package br.com.project.controller;

import br.com.project.dto.auth.LoginRequestDTO;
import br.com.project.dto.auth.LoginResponseDTO;
import br.com.project.dto.auth.RegisterRequestDTO;
import br.com.project.dto.auth.ResponseDTO;
import br.com.project.model.Loja;
import br.com.project.repository.LojaRepository;
import br.com.project.service.LojaService;
import br.com.project.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LojaRepository lojaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final LojaService lojaService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        if (!body.password().equals(body.confirmPassword())) {
            return ResponseEntity.badRequest().body("As senhas não coincidem.");
        }

        Optional<Loja> lojaExistente = lojaRepository.encontrarPorNome(body.name());

        if (lojaExistente.isPresent()) {
            return ResponseEntity.badRequest().body("Loja já registrada.");
        }

        Loja novaLoja = new Loja();
        novaLoja.setNome(body.name());
        novaLoja.setSenha(passwordEncoder.encode(body.password()));

        lojaRepository.salvar(novaLoja);

        String token = tokenService.generateToken(novaLoja);
        return ResponseEntity.ok(new ResponseDTO(token, novaLoja));
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO body) {
        String name = body.name();
        String password = body.password();

        return lojaService.autenticar(name, password)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).body("Usuário ou senha inválidos"));
    }
}
