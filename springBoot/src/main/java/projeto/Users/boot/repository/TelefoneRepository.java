package projeto.Users.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import projeto.Users.boot.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long>{

	@Query ("select t from Telefone t where t.pessoa.id= ?1")
	public List<Telefone> getTelefones(Long pessoaid);
	
}