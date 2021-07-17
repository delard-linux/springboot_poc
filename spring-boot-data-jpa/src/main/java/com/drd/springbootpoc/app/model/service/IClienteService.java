package com.drd.springbootpoc.app.model.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.util.paginator.Pagina;

public interface IClienteService {
	
	public ClienteDTO getCliente(Long id);

	public List<ClienteDTO> getAllClientes();

	public Pagina<ClienteDTO> getAllClientes(Pageable pageable);

	public void saveCliente(ClienteDTO cliente);

	public void deleteCliente(Long id);

}
