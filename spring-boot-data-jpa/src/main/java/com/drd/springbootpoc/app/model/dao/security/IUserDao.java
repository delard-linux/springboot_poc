package com.drd.springbootpoc.app.model.dao.security;

import org.springframework.data.repository.CrudRepository;

import com.drd.springbootpoc.app.model.entity.security.User;

public interface IUserDao extends CrudRepository<User, Long>{

	public User findByUsername(String username);
	
}
