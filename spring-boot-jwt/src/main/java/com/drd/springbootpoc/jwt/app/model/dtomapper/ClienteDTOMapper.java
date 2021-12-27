package com.drd.springbootpoc.jwt.app.model.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.drd.springbootpoc.jwt.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.jwt.app.model.entity.Cliente;

public class ClienteDTOMapper {

	private ClienteDTOMapper() {}	
	
	public static List<ClienteDTO> transformEntityListToDTOList(Iterable<Cliente> clientesEntityIterable) {

		List<ClienteDTO> clientes =  new ArrayList<>();
		
		clientesEntityIterable.forEach(cl -> 
						clientes.add(transformEntityToDTO(cl)));
		
		return clientes;
	}
	
	public static ClienteDTO transformEntityToDTO(Cliente clienteEntity) {

		return new ClienteDTO(clienteEntity.getId(),
				clienteEntity.getNombre(), 
				clienteEntity.getApellido(), 
				clienteEntity.getEmail(), 
				clienteEntity.getBornAt(), 
				clienteEntity.getCreateAt(),
				clienteEntity.getFoto());

	}

	public static Cliente transformDTOToEntity(ClienteDTO cliente) {

		var clienteEntity = new Cliente();
		
		clienteEntity.setId(cliente.getId());
		clienteEntity.setNombre(cliente.getNombre());
		clienteEntity.setApellido(cliente.getApellido());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setBornAt(cliente.getBornAt());
		clienteEntity.setCreateAt(cliente.getCreateAt());
		clienteEntity.setFoto(cliente.getFoto());
		
		return clienteEntity;

	}	
	
	
}
