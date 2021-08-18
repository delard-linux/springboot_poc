package com.drd.springbootpoc.app.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.drd.springbootpoc.app.model.dao.IClienteDao;
import com.drd.springbootpoc.app.model.dao.IFacturaDao;
import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.domain.ClienteSearchCriteria;
import com.drd.springbootpoc.app.model.dtomapper.ClienteDTOMapper;
import com.drd.springbootpoc.app.model.dtomapper.FacturaDTOMapper;
import com.drd.springbootpoc.app.util.paginator.Pagina;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	IClienteDao clienteDao;

	@Autowired
	IFacturaDao facturaDao;
	
	@Autowired
	private IUploadFileService uploadFileService;

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO obtenerCliente(Long id) {

		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		return clienteEntity!=null ? ClienteDTOMapper.transformEntityToDTO(clienteEntity) : null;
		
	}	
	
	@Override
	@Transactional(readOnly = true)
	public ClienteDTO obtenerClienteConFacturas(Long id) {

		var clienteEntity = clienteDao.findById(id).orElse(null);
		ClienteDTO clienteDto = null;

		if (clienteEntity != null) {
			
			clienteDto = ClienteDTOMapper.transformEntityToDTO(clienteEntity);
			
			var listaFacturasEntity = facturaDao.findByClienteId(id);
			if (listaFacturasEntity != null)
				clienteDto.setFacturas(FacturaDTOMapper.transformEntityListToDTOList(listaFacturasEntity));
			
		}
		
		return clienteDto;
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> obtenerTodosClientes() {
		
		return ClienteDTOMapper.transformEntityListToDTOList(clienteDao.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Pagina<ClienteDTO> obtenerTodosClientes(Pageable pageable) {
		
		var paginaClientes = clienteDao.findAll(pageable);
		
		return new Pagina<>(paginaClientes,
				ClienteDTOMapper.transformEntityListToDTOList(paginaClientes.getContent()));

	}
	@Override
	@Transactional(readOnly = true)
	public Pagina<ClienteDTO> obtenerTodosClientesCriteria(Pageable pageable, ClienteSearchCriteria criteria) {
		
		var paginaClientes = clienteDao.findByNombreAndApellidoAndEmail(pageable,
				criteria.getNombreFilter(),criteria.getApellidoFilter(),criteria.getEmailFilter());
		
		return new Pagina<>(paginaClientes,
				ClienteDTOMapper.transformEntityListToDTOList(paginaClientes.getContent()));

	}
	
	@Override
	@Transactional
	public Long crearCliente(ClienteDTO cliente) {
		
		var clienteSalvado = clienteDao.save(ClienteDTOMapper.transformDTOToEntity(cliente));
		
		return clienteSalvado.getId();
	}	
	
	@Override
	@Transactional
	public Long crearClienteConFoto(ClienteDTO cliente, MultipartFile file) throws IOException {
				
		var nombreFicheroFoto = uploadFileService.upload(file);
		cliente.setFoto(nombreFicheroFoto);
		
		return crearCliente(cliente);
	}	
	

	@Override
	@Transactional
	public void actualizarCliente(ClienteDTO cliente) {
		
		clienteDao.save(ClienteDTOMapper.transformDTOToEntity(cliente));
		
	}	
	
	
	@Override
	@Transactional
	public void actualizarClienteConFoto(ClienteDTO cliente, MultipartFile file) throws IOException {
		
		var clientePrevio = obtenerCliente(cliente.getId());
		
		if (clientePrevio.getFoto()!=null) 
			uploadFileService.delete(clientePrevio.getFoto());
		
		cliente.setFoto(uploadFileService.upload(file));
		
		clienteDao.save(ClienteDTOMapper.transformDTOToEntity(cliente));
		
	}

	@Override
	@Transactional
	public void borrarCliente(Long id) throws IOException {
		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		if (clienteEntity!=null && clienteEntity.getFoto() !=null ) {
			uploadFileService.delete(clienteEntity.getFoto());
		}
		
		clienteDao.deleteById(id);
		
	}
		
	@Override
	public boolean borrarFotoCliente(Long id) throws IOException {
		
		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		if (clienteEntity!=null && clienteEntity.getFoto() !=null ) {
			return uploadFileService.delete(clienteEntity.getFoto());
		}
		
		return false;
	}

	@Override
	@Transactional
	public Long guardarCliente(ClienteDTO cliente, MultipartFile foto) throws IOException {
		
		Long idCliente = cliente.getId();
		
		String nombreFichero = null;

		if (foto!=null 
				&& foto.getOriginalFilename()!=null) {				
			nombreFichero = foto.getOriginalFilename();	
		}

		if (idCliente == null) {
			if (nombreFichero!=null && !nombreFichero.isBlank()) 
				idCliente = crearClienteConFoto(cliente, foto);
			else
				idCliente = crearCliente(cliente);
		} else {
			if (nombreFichero!=null  && !nombreFichero.isBlank()) 
				actualizarClienteConFoto(cliente, foto);
			else 
				actualizarCliente(cliente);
		}
		
		return idCliente;
		
	}	
	
}
