package projeto.Users.boot.model;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role implements GrantedAuthority {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String nomeRole;
	
	@Override
	public String getAuthority() {  /*Exemplo do que esse metodo irá Mostrar é o seguinte : ROLE_ADMIN , ROLE_GERENTE*/
		return this.nomeRole;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	
	
}
