package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @TestMethodOrder faz com que o programa rode os testes na ordem estabelecida
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate; //TestRestTemplate cria uma requisição, e envia essa requisição para a classe controladora

	private Usuario usuario; // objeto que permite trabalhar o método POST
	private Usuario usuarioUpdate; // objeto que permite trabalhar o método PUT
	private Usuario usuarioAdmin; // objeto que permite trabalhar os métodos PUT e GET, pois substitui a necessidade de usuário e senha

	@Autowired
	private UsuarioRepository usuarioRepository; // UsuarioRepository permite o acesso ao banco de dados

	@BeforeAll
	public void start() {
		
		// cria-se os dados de acesso do administrador do sistema
		LocalDate dataAdmin = LocalDate.parse("1990-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		usuarioAdmin = new Usuario(0L, "Administrador", "admin@email.com.br", "admin123", dataAdmin);

		if (!usuarioRepository.findByUsuario(usuarioAdmin.getUsuario()).isPresent()) { // verificar se os dados do administrador já existem no sistema

			HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuarioAdmin);
			testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class); // cria o usuário, para se testar os métodos que precisam de autenticação para funcionar
		}

		LocalDate dataPost = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		usuario = new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", dataPost); // metódo usuário

		LocalDate dataPut = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		usuarioUpdate = new Usuario(2L, "Paulo Antunes de Souza", "paulo_souza@email.com.br", "souza123", dataPut); 

	}
	
	@Test
	@Order(1) 	//identifica a ordem a ser seguida de testes.
    @DisplayName("✔ Cadastrar Usuário!")
	public void deveRealizarPostUsuario() {

		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuario/cadastrar", HttpMethod.POST, request, Usuario.class);
		
		// verificar se a resposta foi gravada, ou seja, cria-se um usuário, se sim, o teste foi ok
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode()); 
	}

	@Test
	@Order(2)
	@DisplayName("👍 Listar todos os Usuários!")
	public void deveMostrarTodosUsuarios() {
		 
		// por apenas listar os usuários, não cria a requisição
		// deve-se colocar o withBasicAuth (login e senha) + endpoint "/usuarios/all" +
		// método GET + Null (pois não há requisição) + String.class (porque mostra a lista de usuários do banco, não um usuário em específico
		// se a resposta for ok, ou seja, trazer a lista, o teste foi ok
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123")
		.exchange("/usuario/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode()); // 
	}

	@Test
	@Order(3)
	@DisplayName("😳 Alterar Usuário!")
	public void deveRealizarPutUsuario() {
		
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuarioUpdate);
		
		// withBasicAuth determina o login e a senha para que se faça a requisição
		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123")
		.exchange("/usuario/alterar", HttpMethod.PUT, request, Usuario.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode()); // se a alteração for feita, o teste foi ok
	}
}

// O teste da Camada Controller é um pouco diferente dos testes anteriores
// porquê faremos
// Requisições (http Request) e na sequencia o teste analisará se as Respostas
// das
// Requisições (http Response) foram as esperadas.
// Observe que o método start(), anotado com a anotação @BeforeAll, inicializa
// três objetos do
// tipo Usuário:
// 1. usuario: Não foi passado o Id, porquê este objeto será utilizado para
// testar o método Post.
// 2. usuarioUpdate: Foi passado o Id, porquê o objeto será utilizado para
// testar o método Put.
// 3. usuarioAdmin: Como o nosso Blog Pessoal está com a Camada de Segurança
// Basic
// implementada, precisaremos de um usuário para efetuar login na API.
// Utilizaremos este
// objeto para criar o usuário administrador antes de executar os testes.
// Para simular as Requisições e Respostas, utilizaremos algumas classes e
// métodos:

// TestRestTemplate()
// É um cliente para escrever testes criando um modelo de comunicação com as
// APIs HTTP. Ele fornece os mesmos
// métodos, cabeçalhos e outras construções do protocolo HTTP.

// HttpEntity() Representa uma solicitação HTTP ou uma entidade de resposta,
// composta pelo status da resposta (2XX, 4XX ou
// 5XX), o corpo (Body) e os cabeçalhos (Headers).

// ResponseEntity() Extensão de HttpEntity que adiciona um código de status
// (http Status)
// TestRestTemplate.exchange(URI, HttpMethod, RequestType, ResponseType) : O
// método exchange executa uma requisição de qualquer
// método HTTP e retorna uma instância da Classe ResponseEntity. Ele pode ser
// usado para criar requisições
// com os verbos http GET, POST, PUT e DELETE. Usando o método exchange(),
// podemos realizar todas as operações
// do CRUD (criar, consultar, atualizar e excluir). Todas as requisições do
// método exchange() retornarão como
// resposta um Objeto da Classe ResponseEntity.
