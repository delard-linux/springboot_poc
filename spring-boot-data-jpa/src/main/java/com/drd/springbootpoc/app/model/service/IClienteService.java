package com.drd.springbootpoc.app.model.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;

public interface IClienteService {
	
	public ClienteDTO getCliente(Long id);

	public List<ClienteDTO> getAllClientes();

	public List<ClienteDTO> getAllClientes(Pageable pageable);

	public void saveCliente(ClienteDTO cliente);

	public void deleteCliente(Long id);

}
