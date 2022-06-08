package com.example.vendas.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.vendas.model.entity.ItemVenda;

@Repository
public class ItemVendaRepository {
	@PersistenceContext
    private EntityManager em;

    public void save(ItemVenda itemVenda){
        em.persist(itemVenda);
    }

    public ItemVenda itemVenda(Long id){
        return em.find(ItemVenda.class, id);
    }

	public List<ItemVenda> itemVendas(){
        Query query = em.createQuery("from ItemVenda");
        return query.getResultList();
    }

    public void remove(Long id){
    	ItemVenda itemv = em.find(ItemVenda.class, id);
        em.remove(itemv);
    }

    public void update(ItemVenda itemVenda){
        em.merge(itemVenda);
    }
}
