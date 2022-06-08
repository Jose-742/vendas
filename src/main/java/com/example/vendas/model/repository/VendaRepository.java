package com.example.vendas.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vendas.model.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

	@Query("select v from Venda v where v.data = :data")
	List<Venda> buscarPorData(@Param("data") LocalDate data);

	@Query("select v from Venda v where v.clientePF.id = :id")
	List<Venda> buscarVendasUsuario(@Param("id") Long id);

	@Query("select v from Venda v where v.data = :data AND v.clientePF.id = :id ")
	List<Venda> buscarPorDataUser(@Param("data") LocalDate data, @Param("id") Long id);

}
