package br.org.generation.lojagames.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull; //constraints = restrições, como os dados serão manipulados

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // inicializa a classe model. Faz com que essa classe seja uma tabela
@Table(name = "tb_categoria") // define um nome para a tabela
public class Categoria { // define as tabelas do banco de dados. A classe é publica porque precisa ser acessada, já os atributos são private, pois são trabalhados só dentro dessa classe
	
	//Definir os atributos
	
	// private porque serão trabalhados apenas dentro da classe
	
	@Id // id é uma cHave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY) //faz com que se gere o valor automaticamente(strategy determina o jeito que será criado. identity =  gera automaticamente 1, 2, 3
	private long id; 
	
	@NotNull (message = "O atributo tipo não pode ser Nulo!") // forçar o usuário a digitar algo para que não fique em branco. Incluir a mensagem ao ter o atributo vazio
	private String tipo;
	
	// cria a relação entre a classe categoria que pode ter muitos protudos //  mapeia (mappedby) o atributo produto da tb_. Cascade siginifica que se algo for deletado, tudo o que pertencer ao tema, todas as postagens são deletadas automaticamentemappedBy cascade
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("categoria") // faz com que não se crie um loop ao mostrar os dados pesquisados 
	private List<Produto> produto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	
	// Fazer os get e set
	
	
	
	
	

}
