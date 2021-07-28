package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // fala que a classe é a controladora
@RequestMapping ("/postagens")
@CrossOrigin ("*") // aceita requisição de qualquer origem
public class PostagemController {
	
	@Autowired
	private PostagemRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
		
	}
	
	@GetMapping("/{id}") // indica que quando for pesquisado /postagem/número do id, acessa o método abaixo
	public ResponseEntity<Postagem> GetById(@PathVariable long id) { // @PathVariable determina que se traga o valor do id quando o mesmo for pesquisado
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)) // pega o que consta dentro do ID solicitado
				.orElse(ResponseEntity.notFound().build()); // caso não haja o id pesquisado, ele retorna como not found
	}
	
	@GetMapping("/titulo/{titulo}")	// {se determina que depois do /, o atributo a ser pesquisado é titulo, pois o {id} já foi feito como parâmetro
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping // insere dados na tabela, para inserir via postman, não precisa de ID, pois a info será criada, ou seja, o id é criado automaticamente
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@PutMapping // atualiza os dados na tabela, precisa de ID, pois precisa determinar qual id será feita a alteração
	public ResponseEntity<Postagem> put (@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	@DeleteMapping("/{id}") // necessita de id, para identificar qual o local a ser excluído
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
	
	
}
	

