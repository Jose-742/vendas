package com.example.vendas.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vendas.model.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query("select p.descricao from Produto p where p.status = true AND p.descricao like :termo%")
	List<String> findByProdutosByTermo(@Param("termo")String termo);

	@Query("select  p from Produto p where p.status = true AND p.descricao like :descricao%")
	List<Produto> findProdutosByDescricao(@Param("descricao") String descricao);

}
