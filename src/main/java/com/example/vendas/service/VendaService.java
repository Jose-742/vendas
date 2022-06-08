package com.example.vendas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.Venda;
import com.example.vendas.model.repository.VendaRepository;

@Repository
public class VendaService {

	@Autowired
	private VendaRepository repository;
	
	
	public void save(Venda venda) {
		repository.save(venda);
	}
	
	public Venda venda(Long id) {
		return repository.findById(id).get();
	}
	
	public List<Venda> vendas(){
		return repository.findAll();
	}
	
	public void update(Venda venda) {
		repository.save(venda);
	}
	
	public List<Venda> buscarPorData(LocalDate data){
		if(data == null) {
			return vendas();
		}
		return repository.buscarPorData(data);
	}
	
	public List<Venda> buscarPorDataUser(LocalDate data, Long id){
		if(data == null) {
			return buscarVendasUsuario(id);
		}
		return repository.buscarPorDataUser(data, id);
	}

	public List<Venda> buscarVendasUsuario(Long id){
		return repository.buscarVendasUsuario(id);
	}
}
