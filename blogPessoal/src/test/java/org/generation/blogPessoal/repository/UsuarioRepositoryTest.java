package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.generation.blogPessoal.model.Usuario;

// TestInstance -> faz com que se rode a classe start e na sequência todos os testes
//Lifecycle.PER_CLASS, determina que se rode por classe
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) 
public class UsuarioRepositoryTest { //usa banco de dados. A senha não é criptografada, pois não se usa a classe de serviço, que é a responsável por criptografar a senha.

	@Autowired
	private UsuarioRepository usuarioRepository; // injestão do package repository (@Autowired, através do objeto usuarioRepository

	@BeforeAll //@BeforeAll executa uma única vez o start e na sequência roda todos os testes
	void start() {

		LocalDate data = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		Usuario usuario = new Usuario(0, "João da Silva", "joao@email.com.br", "13465278", data);
		
		// ! -> está negando o que está presente a frente, ou seja, se o usuário já está presente no banco
		// garante para que não se repita o usuário, caso já esteja presente no banco
		// findByUsuario -> verificar por usuário, se o mesmo já existe, se não, o mesmo é salvo no banco
		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) // verificar se já existe o usuário no banco de dados.
			usuarioRepository.save(usuario); // se o mesmo não estiver presente, o mesmo deve ser salvo na tabela

		usuario = new Usuario(0, "Manuel da Silva", "manuel@email.com.br", "13465278", data);
		
		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			usuarioRepository.save(usuario);

		usuario = new Usuario(0, "Frederico da Silva", "frederico@email.com.br", "13465278", data);
		
		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			usuarioRepository.save(usuario);
		
		usuario = new Usuario(0, "Paulo Antunes", "paulo@email.com.br", "13465278", data);
		
		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			usuarioRepository.save(usuario);
	}

	@Test
	@DisplayName("💾 Retorna o nome")
	public void findByNomeRetornaNome() {
		// findByNome -> procura no banco através do nome
		Usuario usuario = usuarioRepository.findByNome("João da Silva");
		assertTrue(usuario.getNome().equals("João da Silva")); // verificar se o o que foi buscado em findByNome é igual a João da silva, sendo positivo, a verificação está ok
	}

	@Test
	@DisplayName("💾 Retorna 3 usuarios")
public void findAllByNomeContainingIgnoreCaseRetornaTresUsuarios() { // nomeia que o teste trará 3 nomes 
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva"); // verifica se com o nome silva, se retorne 3 usuários, sendo positivo, o teste está ok
		assertEquals(3, listaDeUsuarios.size());
	}

	@AfterAll // faz esse último teste depois de todos os outros. Pode-se finalizar de outras maneiras, como limpar o banco de dados.
	public void end() {
		System.out.println("Teste Finalizado!");
	}
}
