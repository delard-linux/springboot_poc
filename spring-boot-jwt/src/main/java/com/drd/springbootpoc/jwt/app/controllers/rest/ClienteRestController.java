package com.drd.springbootpoc.jwt.app.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drd.springbootpoc.jwt.app.common.controller.AppController;
import com.drd.springbootpoc.jwt.app.model.service.IClienteService;
import com.drd.springbootpoc.jwt.app.view.xml.ClienteList;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController extends AppController{
	
	@Autowired
	private IClienteService clienteService;
	

	@GetMapping(value={"/listar"})
	public ClienteList listarRest() {
		
		return new ClienteList(clienteService.obtenerTodosClientes());
	}	
	

}
