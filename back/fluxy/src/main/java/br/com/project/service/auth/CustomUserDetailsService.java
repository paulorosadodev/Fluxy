package br.com.project.service.auth;

import br.com.project.model.Loja;
import br.com.project.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LojaRepository lojaRepository;

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Loja loja = this.lojaRepository.encontrarPorNome(nome).orElseThrow(() -> new UsernameNotFoundException("Loja não encontrada."));
        return new org.springframework.security.core.userdetails.User(loja.getNome(), loja.getSenha(), new ArrayList<>());
    }
}
