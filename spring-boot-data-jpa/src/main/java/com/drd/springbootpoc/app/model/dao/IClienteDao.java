package com.drd.springbootpoc.app.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.drd.springbootpoc.app.model.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
	
	@Query("select c from Cliente c where c.nombre like %?1% and c.apellido like %?2% and c.email like %?3%")
	public Page<Cliente> findByNombreAndApellidoAndEmail(Pageable pageable, String nombre, String apellido, String email);

}
