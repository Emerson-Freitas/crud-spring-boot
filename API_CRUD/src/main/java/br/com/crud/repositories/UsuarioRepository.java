package br.com.crud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.crud.models.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

	@Query(value = "select u from UsuarioModel u where upper(trim(u.nome)) like %?1%")
	List<UsuarioModel> buscarPorNome(String nome);
}
