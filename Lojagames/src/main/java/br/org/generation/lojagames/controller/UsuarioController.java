package br.org.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.lojagames.model.Usuario;
import br.org.generation.lojagames.model.UsuarioLogin;
import br.org.generation.lojagames.repository.UsuarioRepository;
import br.org.generation.lojagames.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	//trazendo todos  cadastrados no banco
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll(){
		
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	//buisca pelo id
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable long id){
		
			return usuarioRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.notFound().build());
				
	}

   //buscando pelo nome do usuario
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> getByNome(@PathVariable String nome){
		
		return ResponseEntity.ok(usuarioRepository.findAllByNomeContainingIgnoreCase(nome));
				
	}
	
	//cadastrando usuario
	@PostMapping("/cadastrar")
	public ResponseEntity<Optional<Usuario>> postUsuario(@RequestBody Usuario usuario){
		
		//realizando a ligação da class swerviço e usuario
		Optional<Usuario> novoUsuario = usuarioService.cadastrarUsuario(usuario);
		
		try {
			//se der tudo certo salve o usuario
			return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			return ResponseEntity.badRequest().build();
		}
		
			
	}
	
	//atualizando usuario
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario){
		
		Optional<Usuario> atualizarUsuario = usuarioService.atualizarUsuario(usuario);
		
		try {
			
			return ResponseEntity.ok(atualizarUsuario.get());
			
		} catch (Exception e) {
			// TODO: handle exception
			
			return ResponseEntity.badRequest().build();
			
		}
		
		
	}
	
	//realizando login
	@PostMapping("/login")
	public ResponseEntity<UsuarioLogin> autenticandoUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		
		return usuarioService.loginUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
