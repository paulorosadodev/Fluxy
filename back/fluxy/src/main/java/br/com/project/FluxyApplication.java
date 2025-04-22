package br.com.project;

import br.com.project.model.User;
import br.com.project.repository.UserRepository;
import br.com.project.service.auth.TokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringBootApplication(scanBasePackages = "br.com.project")
public class FluxyApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenService tokenService;

	public static void main(String[] args) {
		SpringApplication.run(FluxyApplication.class, args);
	}

	@Bean
	public CommandLineRunner init() {
		return (args) -> {
			Optional<User> existingRootUser = userRepository.findByName("root");

			if (existingRootUser.isEmpty()) {
				User rootUser = new User();
				rootUser.setName("root");
				rootUser.setRole("admin");
				rootUser.setPassword(passwordEncoder.encode("root123"));

				userRepository.save(rootUser);
				System.out.println("Usuário root criado com sucesso!");
			} else {
				System.out.println("Usuário root já existe.");
			}
		};
	}
}
