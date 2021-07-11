package com.drd.springbootpoc.app.models.dao;

import java.util.List;

import com.drd.springbootpoc.app.models.entity.Cliente;

public interface IClienteDao {
	
	public List<Cliente> findAll();

	public Cliente findOne(Long id);
	
	public void save(Cliente cliente);

	public void delete(Long id);

}
