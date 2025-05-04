package br.com.project.service;

import br.com.project.dto.auth.LoginResponseDTO;
import br.com.project.model.User;
import br.com.project.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public boolean register(String name, String password, String role) {
        Optional<User> existent = userRepository.findByName(name);

        if (existent.isPresent()) {
            return false;
        }

        String encryptedPassword = encoder.encode(password);

        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(encryptedPassword);
        newUser.setRole(role);

        userRepository.save(newUser);
        return true;
    }

    public Optional<LoginResponseDTO> authenticate(String name, String typedPassword) {
        Optional<User> user = userRepository.findByName(name);

        if (user.isEmpty() || !encoder.matches(typedPassword, user.get().getPassword())) {
            return Optional.empty();
        }

        String token = generateToken(user.get().getName());
        return Optional.of(new LoginResponseDTO(token, user.get().getName()));
    }


    private String generateToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();

    }
}