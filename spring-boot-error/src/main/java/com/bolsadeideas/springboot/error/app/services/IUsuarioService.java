package com.bolsadeideas.springboot.error.app.services;

import java.util.List;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> listar();
	public Usuario obtenerPorId(Integer id);

}
