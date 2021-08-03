package br.org.generation.lojagames.controller;

import java.util.List;

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

import br.org.generation.lojagames.model.Categoria;
import br.org.generation.lojagames.repository.CategoriaRepository;

@RestController // Rest responde a requisições http, que são get, post, put e delete. 
@RequestMapping("/categorias") 
@CrossOrigin(origins="*", allowedHeaders="*") //possibilita que se acesse o front end, mesmo fora do LocalHost. (origins = "*", allowedHeaders = "*") 
//Esta anotação, além de liberar as origens, libera também os cabeçalhos das requisições“*” = Deixa Livre, qualquer endereço
public class CategoriaController {
	
	@Autowired // 
	public CategoriaRepository categoriaRepository;
	
	//Criar os métodos que são utilizados 
	@GetMapping //traz o Request como o caminho
	public ResponseEntity<List<Categoria>> getAll(){ // :<> tipo de objeto que vai trazer na respost e getAll() - Traga todos os dados

		return ResponseEntity.ok(categoriaRepository.findAll());
		// ok = deu certo, conseguiu puxar os dados
		// ele puxa os dados a partir da repository
		// usa o método find all para procurar todas as respostas em categoria
	}
	
		@GetMapping("/tipo/{tipo}")
		public ResponseEntity<List<Categoria>> GetByTipo(@PathVariable String tipo){
			
			return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));	
		}


	
	@GetMapping("/{id}") // mostra que pode se colocar o id que se quer procurar. {} indica que é um variável
	public ResponseEntity<Categoria> getById(@PathVariable long id){ // @PathVariabel indica que como será pesquisado
		
		return categoriaRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)) 
				.orElse(ResponseEntity.notFound().build());
	}
		// determina que precisa fazer um mapeamento do objeto -> resp
		// map precisa de uma resposta, se ela for positiva, retornar o que foi pesquisado
		//.orElse determina que se a resposta foi nula, build da a resposta de notfound, ou seja, não foi encontrado

	@PostMapping // insere dados na tabela, para inserir via postman, não precisa de ID, pois a info será criada, ou seja, o id é criado automaticamente
	public ResponseEntity<Categoria> post (@RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
		// o save traz o resultado que 
	}
	
	@PutMapping // atualiza os dados na tabela, precisa de ID, pois precisa determinar qual id será feita a alteração
	public ResponseEntity<Categoria> put (@RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria));
	}
	
	@DeleteMapping("/{id}") // necessita de id, para identificar qual o local a ser excluído
	public void delete(@PathVariable long id) { 
		categoriaRepository.deleteById(id);
		
		// @PathVariabel indica que como será pesquisado 
		// void pois não tem return, ou seja, ao deletar, não necessita de uma resposta
		
	}
	

}


