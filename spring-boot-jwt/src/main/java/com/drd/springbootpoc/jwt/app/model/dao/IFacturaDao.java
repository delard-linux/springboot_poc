package com.drd.springbootpoc.jwt.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.drd.springbootpoc.jwt.app.model.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
	
	@Query("select distinct f from Factura f left join fetch f.items itemsFac left join fetch itemsFac.producto where f.cliente.id = ?1")
	public List<Factura> findByClienteId(Long clienteId);

	@Query("select distinct f from Factura f left join fetch f.items itemsFac left join fetch itemsFac.producto left join fetch f.cliente where f.id = ?1")
	public Factura findByIdWithCliente(Long facturaId);

}
