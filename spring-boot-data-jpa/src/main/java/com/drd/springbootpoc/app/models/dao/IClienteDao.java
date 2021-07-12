package com.drd.springbootpoc.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.drd.springbootpoc.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
