package br.org.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.lojagames.model.Categoria;

// criar a herança através da dependência do JPA que foi criada no começo da aplicação, possibilita que ela utilize consultar, postar, alterar e deletar todos em relação ao ID 
// CategoriaRepository pois se trabalhará com a classe categoria<Categoria que é a classe que se irá utilizar os métodos herdados e o Long é referente ao ID, que será manipulado)
//Long pois transforma o ID (primitivo) em objeto e permite que ele seja trabalhado
@Repository // 
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{ // interface não se implementa código, apenas se coloca quais parâmetros irá ter

	public List<Categoria> findAllByTipoContainingIgnoreCase(String tipo);

}
