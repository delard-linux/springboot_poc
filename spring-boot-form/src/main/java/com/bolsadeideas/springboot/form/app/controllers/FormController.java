package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolPropertyEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Rol;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RolService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")
public class FormController {
	
	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@ModelAttribute(name = "listaPaises")
	public List<Pais> listaPaises(){
		return paisService.listar();
	}

	@Autowired
	private RolService rolService;

	@Autowired
	private RolPropertyEditor rolEditor;
	
	@ModelAttribute(name = "listaRoles")
	public List<Rol> listaRolesMap(){
		return rolService.listar();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaNacimiento" ,new CustomDateEditor(dateFormat,true));
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());
		
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		binder.registerCustomEditor(Rol.class, "roles", rolEditor);
		
	}
	
	@GetMapping("/form")
	public String form(Model model) {

		var usuario = new Usuario();
		usuario.setNombre("John");
		usuario.setApellido("Doe");
		usuario.setIdentificador("123.456.789-K");
		model.addAttribute("titulo","Formulario de usuarios");
		model.addAttribute("usuario",usuario);

		return "form";
	}

	@PostMapping("/form")
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		
		model.addAttribute("titulo","Resultado del envío del formulario");
		
		if(result.hasErrors()) {
			return "form";
		}
		
		model.addAttribute("usuario",usuario);
		
		status.setComplete();
		
		return "resultado";
	}

}
