package com.drd.springbootpoc.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drd.springbootpoc.app.model.dao.IClienteDao;
import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.dtomapper.ClienteDTOMapper;
import com.drd.springbootpoc.app.util.paginator.Pagina;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO getCliente(Long id) {

		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		return clienteEntity!=null ? ClienteDTOMapper.transformEntityToDTO(clienteEntity) : null;
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> getAllClientes() {
		
		return ClienteDTOMapper.transformEntityListToDTOList(clienteDao.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Pagina<ClienteDTO> getAllClientes(Pageable pageable) {
		
		var paginaClientes = clienteDao.findAll(pageable);
		
		return new Pagina<>(paginaClientes,
				ClienteDTOMapper.transformEntityListToDTOList(paginaClientes.getContent()));

	}
	
	@Override
	@Transactional
	public void saveCliente(ClienteDTO cliente) {
		
		clienteDao.save(ClienteDTOMapper.transformDTOToEntity(cliente));
		
	}

	@Override
	@Transactional
	public void deleteCliente(Long id) {
		clienteDao.deleteById(id);
	}
	
	
}
