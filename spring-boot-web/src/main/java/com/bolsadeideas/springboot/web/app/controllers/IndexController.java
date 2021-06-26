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
	
	@GetMapping(value = {"/index", "/", "", "/home"})
	public String index(ModelMap model) {
		model.addAttribute("titulo", textoIndex);
		
		return "index";		
	}

	@RequestMapping(value = {"/perfil"})
	public String perfil(Model model) {

		Usuario usuario = new Usuario();
		usuario.setNombre("David");
		usuario.setApellido("Pelaez");
		usuario.setEmail("david@correo.com");
		
		model.addAttribute("titulo", textoPerfil.concat(usuario.getNombre()));
		model.addAttribute("usuario", usuario);
		return "perfil";		
	}

	@RequestMapping(value = {"/listar"})
	public String listar(Model model) {

		model.addAttribute("titulo", textoListar);
		
		return "listar";		
	}

	@ModelAttribute("usuarios")
	public List<Usuario> rellenarUsuarios(){
		return Arrays.asList(	
				new Usuario("David","Pelaez","david@correo.com"),
				new Usuario("Pepe","Botella","pepe@correo.com"),
				new Usuario("Javier","Lasalle",null));
	}

}

