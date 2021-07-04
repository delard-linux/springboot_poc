package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Rol;

@Service
public class RolServiceImpl implements RolService {


	private List<Rol> listaRoles;
	
	public RolServiceImpl() {
		this.listaRoles = 	Arrays.asList(
				new Rol(1,"ROLE_ADMIN","Administrador"),
				new Rol(2,"ROLE_USER","Usuario"),
				new Rol(3,"ROLE_MODERATOR","Moderador"));
	}

	@Override
	public List<Rol> listar() {
		return listaRoles;
	}

	@Override
	public Rol obtenerPorId(Integer id) {
		
	    Optional<Rol> selectedRol = this.listaRoles.stream()
	    		.filter(s -> id.equals(s.getId()))
	    		.findFirst();
	    return selectedRol.isPresent() ? selectedRol.get() : null;

	}

}
