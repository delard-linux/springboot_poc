package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.model.domain.ClienteDTO;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente", Cliente.class).getResultList();
	}

	@Override
	@Transactional
	public void save(ClienteDTO cliente) {
		
		var clienteEntity = new Cliente();
		clienteEntity.setId(cliente.getId());
		clienteEntity.setNombre(cliente.getNombre());
		clienteEntity.setApellido(cliente.getApellido());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setBornAt(cliente.getBornAt());
		clienteEntity.setCreateAt(cliente.getCreateAt());
		
		// diferenciación salvar cliente actual o crear nuevo
		if (cliente.getId()!=null && cliente.getId()>0) {
			em.merge(clienteEntity);
		} else {
			em.persist(clienteEntity);
		}
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}
