package com.bolsadeideas.springboot.web.app.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolsadeideas.springboot.web.app.model.Usuario;

@Controller
@RequestMapping(value = {"/app"})
public class IndexController {
	
	
	@Value("${texto.indexcontroller.index.titulo}")
	private String textoIndex;
	@Value("${texto.indexcontroller.perfil.titulo}")
	private String textoPerfil;
	@Value("${texto.indexcontroller.listar.titulo}")
	private String textoListar;
	
	private static final String ATTRIBUTE_TITULO = "titulo";
	private static final String ATTRIBUTE_USUARIO = "usuario";
	private static final String ATTRIBUTE_USUARIOS = "usuarios";

	private static final String VIEW_INDEX = "index";
	private static final String VIEW_PERFIL = "perfil";
	private static final String VIEW_LISTAR = "listar";

	
	@GetMapping(value = {"/index", "/", "", "/home"})
	public String index(ModelMap model) {
		model.addAttribute(ATTRIBUTE_TITULO, textoIndex);
		
		return VIEW_INDEX;		
	}

	@RequestMapping(value = {"/perfil"})
	public String perfil(Model model) {

		var usuario = new Usuario();
		usuario.setNombre("David");
		usuario.setApellido("Pelaez");
		usuario.setEmail("david@correo.com");
		
		model.addAttribute(ATTRIBUTE_TITULO, textoPerfil.concat(usuario.getNombre()));
		model.addAttribute(ATTRIBUTE_USUARIO, usuario);
		return VIEW_PERFIL;		
	}

	@RequestMapping(value = {"/listar"})
	public String listar(Model model) {

		model.addAttribute(ATTRIBUTE_TITULO, textoListar);
		
		return VIEW_LISTAR;		
	}

	@ModelAttribute(ATTRIBUTE_USUARIOS)
	public List<Usuario> rellenarUsuarios(){
		return Arrays.asList(	
				new Usuario("David","Pelaez","david@correo.com"),
				new Usuario("Pepe","Botella","pepe@correo.com"),
				new Usuario("Javier","Lasalle",null));
	}

}

