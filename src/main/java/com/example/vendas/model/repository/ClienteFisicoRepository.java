package com.example.vendas.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.ClientePF;

@Repository
public class ClienteFisicoRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void save(ClientePF clientePF) {
		em.persist(clientePF);
	}
	
	public ClientePF clienteFisico(Long id) {
		return em.find(ClientePF.class, id);
	}
	
	public List<ClientePF> clientesFisicos(){
		Query query = em.createQuery("from ClientePF");
		return query.getResultList();
	}
	
	public void remove(Long id) {
		ClientePF clientePF = em.find(ClientePF.class, id);
		em.remove(clientePF);
	}
	
	public void update(ClientePF clientePF) {
		em.merge(clientePF);
	}
	
	public List<ClientePF> buscarPorNome(String nome){
		if(nome.isEmpty()) {
			return clientesFisicos();
		}
		Query query = em.createQuery("from ClientePF c where c.nome like:nome");
		query.setParameter("nome", "%"+nome+"%");
		List<ClientePF> buscarNome = query.getResultList();
		return buscarNome;		
	}
}
