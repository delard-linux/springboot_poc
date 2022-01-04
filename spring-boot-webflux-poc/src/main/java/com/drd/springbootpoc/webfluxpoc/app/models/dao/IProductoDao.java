package com.drd.springbootpoc.webfluxpoc.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.drd.springbootpoc.webfluxpoc.app.models.documents.Producto;

public interface IProductoDao extends ReactiveMongoRepository<Producto, String> {
	
}
