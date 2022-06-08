package com.example.vendas.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.Enderecos;

@Repository
public interface EnderecosRepository extends JpaRepository<Enderecos, Long>{

	@Query("select e from Enderecos e where e.clientePF.id = :id AND e.status = true")
	List<Enderecos> findByBuscarPorCliente(Long id);

}
