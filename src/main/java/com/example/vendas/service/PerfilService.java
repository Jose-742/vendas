package com.example.vendas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.Perfil;
import com.example.vendas.model.repository.PerfilRepository;

@Repository
public class PerfilService {

	@Autowired
	private PerfilRepository repository;
	
	public  Perfil perfil(Long id) {
		return repository.findById(id).get();
	}
	
	public List<Perfil> perfis(){
		return repository.findAll();
	}
}
