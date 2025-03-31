package projeto.Users.boot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Profissao {
	
	@Id
	private Long Id;
	private String nome;
	
	
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
}
