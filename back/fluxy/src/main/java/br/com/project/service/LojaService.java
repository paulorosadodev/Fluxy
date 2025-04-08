package br.com.project.service;

import br.com.project.model.Loja;
import br.com.project.repository.LojaRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class LojaService {

    private final LojaRepository lojaRepository;
    private final PasswordEncoder encoder;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public LojaService(LojaRepository lojaRepository, PasswordEncoder encoder) {
        this.lojaRepository = lojaRepository;
        this.encoder = encoder;
    }

    public boolean registrar(String nome, String senha) {
        Optional<Loja> existente = lojaRepository.encontrarPorNome(nome);

        if (existente.isPresent()) {
            return false;
        }

        String senhaCriptografada = encoder.encode(senha);

        Loja novo = new Loja();
        novo.setNome(nome);
        novo.setSenha(senhaCriptografada);

        lojaRepository.salvar(novo);
        return true;
    }

    public Optional<String> autenticar(String nome, String senhaDigitada) {
        Optional<Loja> loja = lojaRepository.encontrarPorNome(nome);

        if (loja.isEmpty() || !encoder.matches(senhaDigitada, loja.get().getSenha())) {
            return Optional.empty();
        }

        String token = gerarToken(loja.get().getNome());
        return Optional.of(token);
    }

    private String gerarToken(String nomeUsuario) {
        return Jwts.builder()
                .setSubject(nomeUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();

    }
}