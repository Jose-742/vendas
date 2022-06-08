package com.example.vendas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.Produto;
import com.example.vendas.model.repository.ProdutoRepository;

@Repository
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public void save(Produto produto) {
		repository.save(produto);
	}
	
	public Produto produto(Long id) {
		return repository.findById(id).get();
	}
	
	public List<Produto> listandoPorPaginacao(PageRequest pageRequest){
	 Page<Produto> produtosRequest =  repository.findAll(pageRequest);	
	 List<Produto> produtosResponse = new ArrayList<>();
	 
	 for(Produto p : produtosRequest) {
		 if(p.getStatus() == true) {
			 produtosResponse.add(p);
		 }
	 } 
		return  produtosResponse;
	}
	
	public void deletarProduto(Long id) {
		Produto produto  = repository.findById(id).get();
		produto.setStatus(false);
		repository.save(produto);
	}
	
	public void update(Produto produto) {
		repository.save(produto);
	}
	
	public List<String> findProdutosByTermo(String termo){
		return repository.findByProdutosByTermo(termo);
	}
	
	public List<Produto> findProdutosByDescricao(String descricao){
		return repository.findProdutosByDescricao(descricao);
	}
	
	
}
