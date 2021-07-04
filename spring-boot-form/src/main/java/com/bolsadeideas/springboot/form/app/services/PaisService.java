package com.bolsadeideas.springboot.form.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

@Service
public interface PaisService {
	public List<Pais> listar();
	public Pais obtenerPorId(Integer id);

}
