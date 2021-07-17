package com.drd.springbootpoc.app.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.drd.springbootpoc.app.model.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

}
