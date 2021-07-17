package com.drd.springbootpoc.app.model.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.entity.Cliente;

public class ClienteDTOMapper {

	private ClienteDTOMapper() {}	
	
	public static List<ClienteDTO> transformToDTO(Iterable<Cliente> clientesEntityIterable) {

		List<ClienteDTO> clientes =  new ArrayList<>();
		
		clientesEntityIterable.forEach(cl -> 
						clientes.add(new ClienteDTO(
										cl.getId(),
										cl.getNombre(), 
										cl.getApellido(), 
										cl.getEmail(), 
										cl.getBornAt(), 
										cl.getCreateAt())));
		
		return clientes;
	}
	
}
