package br.org.generation.lojagames.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produto")
public class Produto {
	
	//Definir os atributos
	@Id // identifica a chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Cria o auto incremento
	private long id;
	
	@NotNull (message = "O campo tipo deve ser obrigatório!")
	private String titulo;
	
	@NotNull (message = "O campo descricao deve ser obrigatório!")
	@Size (min = 5, max = 500, message = "O campo descrição deve ter no mínimo 5 e no máximo 500 caracteres")
	private String descricao;
	
	@NotNull (message = "O campo console deve ser obrigatório!")
	private String console;
	
	@NotNull (message = "O campo valor deve ser obrigatório!")
	@Positive // aceita só valor positivo para esse atributo
	private BigDecimal valor;

	@ManyToOne // faz a relação com a classe categoria, ou seja, vários produtos para uma categoria apenas
	@JsonIgnoreProperties("produto")
	private Categoria categoria;
	
	@ManyToOne // vários produtos para um usuário
	@JsonIgnoreProperties("produto")
	private Usuario usuario;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	
	
	
	
	
	
	
}

