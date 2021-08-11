package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

// comunicação da api com o banco de dados
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	// Optional porque pode retornar uma resposta vazia
	public Optional<Usuario> findByUsuario (String usuario);
	
	public List<Usuario> findAllByNomeContainingIgnoreCase (String nome);
	
	public Usuario findByNome (String nome);
	
	
	
	
	
	

}
