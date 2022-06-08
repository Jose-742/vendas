package com.example.vendas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.ClientePF;
import com.example.vendas.model.repository.ClientePFRepository;

@Repository
public class ClientePFService {
	
	@Autowired
	private ClientePFRepository repository;

	public void save(ClientePF clientePF) {
		repository.save(clientePF);
	}
	
	public ClientePF clienteFisico(Long id) {
		return repository.findById(id).get();
	}
	
	public List<ClientePF> clientesFisicos(){
		return repository.findAll();
	}
	
	public void update(ClientePF clientePF) {
		repository.save(clientePF);
	}
	
	public List<ClientePF> buscarPorNome(String nome){
		if(nome.isEmpty()) {
			return clientesFisicos();
		}
		return repository.findByBuscarPorNome(nome);
	}
}
