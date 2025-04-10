package br.com.project.service.auth;

import br.com.project.model.Store;
import br.com.project.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Store store = this.storeRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException(
                "Loja não encontrada."));
        return new org.springframework.security.core.userdetails.User(store.getName(), store.getPassword(),
                new ArrayList<>());
    }
}
