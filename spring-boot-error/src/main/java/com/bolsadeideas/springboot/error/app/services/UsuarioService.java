package com.bolsadeideas.springboot.error.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;

@Service
public class UsuarioService implements IUsuarioService {

	private List<Usuario> lista;
	
	public UsuarioService() {
		this.lista = Arrays.asList(
				new Usuario(1, "Rafa", "Nadal"),
				new Usuario(2, "Roger", "Federer"),
				new Usuario(3, "John", "Smith"),
				new Usuario(4, "David", "Ferrer"),
				new Usuario(5, "Feliciano", "Lopez"),
				new Usuario(6, "Roberto", "Bautista"),
				new Usuario(7, "Gael", "Monfils"),
				new Usuario(8, "David", "Nalbandian"),
				new Usuario(9, "Fernando", "Verdasco")
				);
	}

	@Override
	public List<Usuario> listar() {
			return this.lista;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {

	    Optional<Usuario> selectedUsuario = this.lista.stream()
	    		.filter(s -> id.equals(s.getId()))
	    		.findFirst();
	    return selectedUsuario.isPresent() ? selectedUsuario.get() : null;
		
	}

}
