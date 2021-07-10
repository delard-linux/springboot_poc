package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.app.model.domain.ClienteDTO;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteDao {
	
	public List<Cliente> findAll();

	public Cliente findOne(Long id);
	
	public void save(ClienteDTO cliente);

	public void delete(Long id);

}
