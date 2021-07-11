package com.drd.springbootpoc.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.drd.springbootpoc.app.models.entity.Cliente;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);
	}
	
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente", Cliente.class).getResultList();
	}

	@Override
	public void save(Cliente cliente) {
		
		// diferenciación salvar cliente actual o crear nuevo
		if (cliente.getId()!=null && cliente.getId()>0) {
			em.merge(cliente);
		} else {
			em.persist(cliente);
		}
		
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}
