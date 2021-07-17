package com.drd.springbootpoc.app.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drd.springbootpoc.app.model.dao.IClienteDao;
import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.dtomapper.ClienteDTOMapper;
import com.drd.springbootpoc.app.model.entity.Cliente;
import com.drd.springbootpoc.app.util.paginator.Pagina;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO getCliente(Long id) {

		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		return clienteEntity!=null ? new ClienteDTO(
				clienteEntity.getId(),
				clienteEntity.getNombre(), 
				clienteEntity.getApellido(), 
				clienteEntity.getEmail(), 
				clienteEntity.getBornAt(), 
				clienteEntity.getCreateAt()
				) : null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> getAllClientes() {

		List<ClienteDTO> clientes =  new ArrayList<>();
		var clientesEntity =  clienteDao.findAll();
		
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
	@Transactional(readOnly = true)
	public Pagina<ClienteDTO> getAllClientes(Pageable pageable) {
		
		var paginaClientes = clienteDao.findAll(pageable);
		
		return new Pagina<>(paginaClientes,
				ClienteDTOMapper.transformToDTO(paginaClientes.getContent()));

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
		clienteDao.deleteById(id);
	}
	
	
}
