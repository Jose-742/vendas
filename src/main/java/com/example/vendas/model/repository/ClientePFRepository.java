package com.example.vendas.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vendas.model.entity.ClientePF;

public interface ClientePFRepository extends JpaRepository<ClientePF, Long>{

	@Query("select c from ClientePF c where c.nome like :nome%")
	List<ClientePF> findByBuscarPorNome(@Param("nome") String nome);

}
