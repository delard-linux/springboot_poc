package com.bolsadeideas.springboot.app.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.model.domain.ClienteDTO;
import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO getCliente(Long id) {

		var clienteEntity = clienteDao.findOne(id);
		
		return new ClienteDTO(
				clienteEntity.getId(),
				clienteEntity.getNombre(), 
				clienteEntity.getApellido(), 
				clienteEntity.getEmail(), 
				clienteEntity.getBornAt(), 
				clienteEntity.getCreateAt()
				);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> getAllClientes() {

		List<ClienteDTO> clientes =  new ArrayList<>();
		List<Cliente> clientesEntity =  clienteDao.findAll();
		
		clientesEntity.forEach(cl -> 
						clientes.add(new ClienteDTO(
										cl.getId(),
										cl.getNombre(), 
										cl.getApellido(), 
										cl.getEmail(), 
										cl.getBornAt(), 
										cl.getCreateAt())));
		
		return clientes;
	}

	
	@Override
	@Transactional
	public void saveCliente(ClienteDTO cliente) {

		var clienteEntity = new Cliente();
		clienteEntity.setId(cliente.getId());
		clienteEntity.setNombre(cliente.getNombre());
		clienteEntity.setApellido(cliente.getApellido());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setBornAt(cliente.getBornAt());
		clienteEntity.setCreateAt(cliente.getCreateAt());
		
		clienteDao.save(clienteEntity);
		
	}

	@Override
	@Transactional
	public void deleteCliente(Long id) {
		clienteDao.delete(id);
	}

}
