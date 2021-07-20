package com.drd.springbootpoc.app.model.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.util.paginator.Pagina;

public interface IClienteService {
	
	public ClienteDTO obtenerCliente(Long id);

	public List<ClienteDTO> obtenerTodosClientes();

	public Pagina<ClienteDTO> obtenerTodosClientes(Pageable pageable);

	public Long crearCliente(ClienteDTO cliente);
	
	public Long crearClienteConFoto(ClienteDTO cliente, String fileName, InputStream data);

	public void actualizarCliente(ClienteDTO cliente);
	
	public void actualizarClienteConFoto(ClienteDTO cliente, String fileName, InputStream data);

	public String guardarFoto(String fileName, InputStream data);

	public void borrarCliente(Long id);
	
	public boolean borrarFotoCliente(Long id);

	public boolean borrarFoto(String fileName);

}
