package br.com.project.service.auth;

import br.com.project.model.User;
import br.com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = this.userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException(
                "Usuário não encontrado"));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                new ArrayList<>());
    }
}
