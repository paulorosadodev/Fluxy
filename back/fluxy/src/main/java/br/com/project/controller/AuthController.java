package br.com.project.controller;

import br.com.project.dto.auth.LoginRequestDTO;
import br.com.project.dto.auth.RegisterRequestDTO;
import br.com.project.dto.auth.ResponseDTO;
import br.com.project.model.Loja;
import br.com.project.repository.LojaRepository;
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
    private final LojaRepository lojaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional <Loja> loja = this.lojaRepository.encontrarPorNome(body.nome());

        if (loja.isEmpty()) {
            Loja novaLoja = new Loja();
            novaLoja.setUsername(body.nome());
            novaLoja.setSenha(passwordEncoder.encode(body.senha()));
            this.lojaRepository.salvar(novaLoja);

            String token = this.tokenService.generateToken(novaLoja);
            return ResponseEntity.ok(new ResponseDTO(token, novaLoja));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        Loja loja = this.lojaRepository.encontrarPorNome(body.nome()).orElseThrow(() -> new RuntimeException("Loja não encontrada."));

        if (passwordEncoder.matches(body.senha(), loja.getSenha())) {
            String token = this.tokenService.generateToken(loja);
            return ResponseEntity.ok(new ResponseDTO(token, loja));
        }
        return ResponseEntity.badRequest().build();
    }
}
