package br.com.rest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rest.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	@Modifying
	@Query("UPDATE Pessoa p SET p.enabled = false WHERE p.id =:id")
	void disablePerson(@Param("id")Long id);

	
	// caso eu queira pesquisar Leandro e digitar apenas and ele vai retornar todos os nomes que tenham and devido os %
	@Query("SELECT p FROM Pessoa p WHERE p.nome LIKE LOWER(CONCAT ('%',:nome,'%'))")
	Page<Pessoa> findPersonsByName(@Param("nome")String firstName, Pageable pageable);

}
