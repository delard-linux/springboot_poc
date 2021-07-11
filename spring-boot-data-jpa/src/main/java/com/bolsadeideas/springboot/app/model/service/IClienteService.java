package com.bolsadeideas.springboot.app.model.service;

import java.util.List;

import com.bolsadeideas.springboot.app.model.domain.ClienteDTO;

public interface IClienteService {
	
	public ClienteDTO getCliente(Long id);

	public List<ClienteDTO> getAllClientes();

	public void saveCliente(ClienteDTO cliente);

	public void deleteCliente(Long id);

}
