package com.drd.springbootpoc.app.model.dao.security;

import org.springframework.data.repository.CrudRepository;

import com.drd.springbootpoc.app.model.entity.security.Usuario;

public interface IUserDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
}
