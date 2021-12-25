package com.drd.springbootpoc.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;

@XmlRootElement(name="clientes")
public class ClienteList {
	
	@XmlElement(name="cliente")
	public List<ClienteDTO> clientes;

	public ClienteList() {}

	public ClienteList(List<ClienteDTO> clientes) {
		this.clientes = clientes;
	}

	public List<ClienteDTO> getClientes() {
		return clientes;
	}
	
	
	
}
