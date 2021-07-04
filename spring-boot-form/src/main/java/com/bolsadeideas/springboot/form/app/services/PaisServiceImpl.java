package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

@Service
public class PaisServiceImpl implements PaisService {


	private List<Pais> listaPaises;
	
	public PaisServiceImpl() {
		this.listaPaises = 	Arrays.asList(
				new Pais(1, "ES", "España"), 
				new Pais(2, "MX", "Mexico"), 
				new Pais(3, "CL", "Chile"),
				new Pais(4, "AR", "Argentina"), 
				new Pais(5, "PE", "Peru"), 
				new Pais(6, "CO", "Colombia"),
				new Pais(7, "VE", "Venezuela"));
	}

	@Override
	public List<Pais> listar() {
		return listaPaises;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		
	    Optional<Pais> selectedPais = this.listaPaises.stream()
	    		.filter(s -> id.equals(s.getId()))
	    		.findFirst();
	    return selectedPais.isPresent() ? selectedPais.get() : null;

	}

}
