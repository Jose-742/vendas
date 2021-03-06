package com.example.vendas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query("select u from Usuario u where u.email like :email")
	Usuario findByEmail(@Param("email") String email);
		
}
