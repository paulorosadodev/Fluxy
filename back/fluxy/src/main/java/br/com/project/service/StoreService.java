package br.com.project.service;

import br.com.project.dto.auth.LoginResponseDTO;
import br.com.project.model.Store;
import br.com.project.repository.StoreRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final PasswordEncoder encoder;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public StoreService(StoreRepository storeRepository, PasswordEncoder encoder) {
        this.storeRepository = storeRepository;
        this.encoder = encoder;
    }

    public boolean register(String name, String password) {
        Optional<Store> existent = storeRepository.findByName(name);

        if (existent.isPresent()) {
            return false;
        }

        String encryptedPassword = encoder.encode(password);

        Store newStore = new Store();
        newStore.setName(name);
        newStore.setPassword(encryptedPassword);

        storeRepository.save(newStore);
        return true;
    }

    public Optional<LoginResponseDTO> authenticate(String name, String typedPassword) {
        Optional<Store> store = storeRepository.findByName(name);

        if (store.isEmpty() || !encoder.matches(typedPassword, store.get().getPassword())) {
            return Optional.empty();
        }

        String token = generateToken(store.get().getName());
        return Optional.of(new LoginResponseDTO(token, store.get().getName()));
    }


    private String generateToken(String storeName) {
        return Jwts.builder()
                .setSubject(storeName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();

    }
}