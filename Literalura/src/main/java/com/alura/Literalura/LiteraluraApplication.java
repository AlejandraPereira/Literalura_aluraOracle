package com.alura.Literalura;

import com.alura.Literalura.principal.Principal;
import com.alura.Literalura.repository.IAuthorRepository;
import com.alura.Literalura.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.security.Permission;

@SpringBootApplication
//@EnableJpaRepositories("com.alura.Literalura.repository")
public class LiteraluraApplication  implements CommandLineRunner {
	@Autowired
	private IBookRepository bookRepository;
	@Autowired
	private IAuthorRepository authorRepository;

	public static void main(String[] args) {

		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(bookRepository,authorRepository);
		principal.showMenu();
	}
}
