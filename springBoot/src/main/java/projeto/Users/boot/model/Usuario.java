package projeto.Users.boot.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String login;
	    private String senha;
	    
	    @OneToMany(fetch = FetchType.EAGER)
	    @JoinTable(name = "usuarios_role" , joinColumns = @JoinColumn(name = "usuario_id" , referencedColumnName = "id" , table = "usuario") ,
	    inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id" , table = "role")) /*Criando tabela de acesso do Usuario*/
	    private List<Role> roles;
	    

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getLogin() {
	        return login;
	    }

	    public void setLogin(String login) {
	        this.login = login;
	    }

	    public String getSenha() {
	        return senha;
	    }

	    public void setSenha(String senha) {
	        this.senha = senha;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return roles; 
	    }

	    @Override
	    public String getPassword() {
	        return senha;
	    }

	    @Override
	    public String getUsername() {
	        return login;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	}