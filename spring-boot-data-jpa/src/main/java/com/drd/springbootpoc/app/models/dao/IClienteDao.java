package com.drd.springbootpoc.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.drd.springbootpoc.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

}
