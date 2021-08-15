package org.generation.blogPessoal.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

// Para realizar os testes, deve-se rodar apenas a parte de teste, não precisa rodar a aplicação em si
//@SpringBootTest -> indica que a classe é de teste do spring. 
// RANDOM.PORT, procura uma porta disponível para rodas os testes, diferente da porta que está rodando a aplicação.

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) 	 
public class UsuarioTest {

	// CRIAR OBJETOS do tipo usuário para que se possa rodas os teste
	public Usuario usuario; // usuário com atributos
	public Usuario usuarioNulo = new Usuario(); // usuários com atributos vazios
	
	@Autowired // verificar se há alguma condição das anotações da aplicação e se as mesmas não estão sendo respeitadas
	// por exemplo, se o atributo nome é nulo ou não, dai apresenta a mensagem de erro
	// cria uma lista com os erros de validação de cada atributo
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	
	Validator validator = factory.getValidator();

	@BeforeEach // determina que se execute esse método, antes de exercutar cada teste em si
	public void start() {
		// preenche o objeto usuário criado na linha 28
		// LocalDate é um classe, precisa criar o objetivo data
		// preencher os dados do objeto usuário. 
		// 0L é o ID, que é 0, pois se cria automaticamente, através do a
		LocalDate data = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		usuario = new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", data);
	}
	
	// CRIAS OS TESTES
	
	@Test // @Test -> indica que o método a ser criado é um teste. 
	// @DisplayName -> pode ser criado conforme a lógica do programador.
	// para se inserir os emojis, pressionar tecla windows + .
	@DisplayName("✔ Valida Atributos Não Nulos") 	
	void testValidaAtributos() {
		
		// Set, é uma collection que não permite dados duplicados, ou seja, grava a mensagem apenas uma vez
		// ConstraintViolation -> verifica as regras atribuídas nos atributos, e valida se as mesmas estão sendo respeitadas
		// assertTrue verificar se a lista está vazia, caso os dados sejam preenchidos de forma correta
		Set<ConstraintViolation<Usuario>> violacao = validator.validate(usuario);
		
		// mostra as mensagens dos testes
		System.out.println(violacao.toString()); 
		
		assertTrue(violacao.isEmpty());
	}

	@Test
	@DisplayName("✖ Não Valida Atributos Nulos")
	void testNaoValidaAtributos() {
		
		// verifica o objeto usuarioNulo, que não tem valores preenchidos.
		// Como os atributos estão preenchidos, a mesma apresenta erro, pois o assertTrue diz que a condição é verdadeira, ou seja, não aceita objetos nulos.
		Set<ConstraintViolation<Usuario>> violacao = validator.validate(usuarioNulo);
		
		System.out.println(violacao.toString());
		
		// ao se colocar assertFalse, o teste diz que a os objetos não podem ser nulos, ou seja, seria uma afirmação positiva, pois os objetos estão preenchidos
		assertTrue(violacao.isEmpty());
	
	}
}
