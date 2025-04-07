package br.com.project.service;

import br.com.project.model.Loja;
import br.com.project.repository.LojaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LojaService {

    private final LojaRepository lojaRepository;
    private final PasswordEncoder encoder;

    public LojaService(LojaRepository lojaRepository, PasswordEncoder encoder) {
        this.lojaRepository = lojaRepository;
        this.encoder = encoder;
    }

    // Método para registrar novo usuário
    public boolean registrar(int id, String nome, String senha) {
        Optional<Loja> existente = lojaRepository.encontrarPorNome(nome);

        if (existente.isPresent()) {
            return false;
        }

        String senhaCriptografada = encoder.encode(senha);

        Loja novo = new Loja();
        novo.setId(id);
        novo.setUsername(nome);
        novo.setSenha(senhaCriptografada);

        lojaRepository.salvar(novo);
        return true;
    }

    public boolean autenticar(String nome, String senhaDigitada) {
        Optional<Loja> loja = lojaRepository.encontrarPorNome(nome);

        if (loja.isEmpty()) {
            return false;
        }

        return encoder.matches(senhaDigitada, loja.get().getSenha());
    }
}