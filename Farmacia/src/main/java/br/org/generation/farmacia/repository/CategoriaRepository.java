package br.org.generation.farmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.farmacia.model.CategoriaModel;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long>{
	
	//criar o método de pesquisa fora do padrão
	
	public List<CategoriaModel> findAllBytipoContainingIgnoreCase (String tipo);
	
	
	
	
	

}
