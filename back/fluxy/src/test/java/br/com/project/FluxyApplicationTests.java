package br.com.project;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FluxyApplicationTests {

	@BeforeAll
	static void setup() {
		Dotenv.load(); // carrega o .env
	}

	@Test
	void contextLoads() {
	}

}
