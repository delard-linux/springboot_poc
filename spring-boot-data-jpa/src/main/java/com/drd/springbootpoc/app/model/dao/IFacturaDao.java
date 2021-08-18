package com.drd.springbootpoc.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.drd.springbootpoc.app.model.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
	
	@Query("select distinct f from Factura f left join fetch f.items itemsFac left join fetch itemsFac.producto where f.cliente.id = ?1")
	public List<Factura> findByClienteId(Long clienteId);

}
