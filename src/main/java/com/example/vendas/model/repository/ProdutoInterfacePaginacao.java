package com.example.vendas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.model.entity.Produto;

public interface ProdutoInterfacePaginacao extends JpaRepository<Produto, Long>{

}
