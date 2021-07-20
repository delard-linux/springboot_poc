package com.drd.springbootpoc.app.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final String FOLDER_UPLOADS = "uploads";
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO obtenerCliente(Long id) {

		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		return clienteEntity!=null ? ClienteDTOMapper.transformEntityToDTO(clienteEntity) : null;
		
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
	@Transactional
	public Long crearCliente(ClienteDTO cliente) {
		
		var clienteSalvado = clienteDao.save(ClienteDTOMapper.transformDTOToEntity(cliente));
		
		return clienteSalvado.getId();
	}	
	
	@Override
	@Transactional
	public Long crearClienteConFoto(ClienteDTO cliente, String fileName, InputStream data) {
				
		var nombreFicheroFoto = guardarFoto(fileName, data);
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
	public void actualizarClienteConFoto(ClienteDTO cliente, String fileName, InputStream data) {
		
		var clientePrevio = obtenerCliente(cliente.getId());
		
		if (clientePrevio.getFoto()!=null) 
			borrarFoto(clientePrevio.getFoto());
		
		cliente.setFoto(guardarFoto(fileName, data));
		
		clienteDao.save(ClienteDTOMapper.transformDTOToEntity(cliente));
		
	}

	@Override
	public String guardarFoto(String fileName, InputStream data) {

		String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;
		var rootPath = Paths.get(FOLDER_UPLOADS).resolve(uniqueFilename);

		var rootAbsolutePath = rootPath.toAbsolutePath();
		
		log.info("rootPath: {}", rootPath);
		log.info("rootAbsolutPath: {}",rootAbsolutePath);

		try {
			Files.copy(data, rootAbsolutePath);
			//TODO: esto tiene que sacarse fuera
//			flash.addFlashAttribute(FLASH_INFO, "Has subido correctamente '" + uniqueFilename + "'");
			return uniqueFilename;
		} catch (IOException e) {
			// TODO e tendría que tracear con el logger y tb escalar la excepcion para evitar el guardado del formulario
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public void borrarCliente(Long id) {
		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		if (clienteEntity!=null && clienteEntity.getFoto() !=null ) {
			borrarFoto(clienteEntity.getFoto());
		}
		
		clienteDao.deleteById(id);
		
	}
		
	@Override
	//TODO revisar si se usa
	public boolean borrarFotoCliente(Long id) {
		
		var clienteEntity = clienteDao.findById(id).orElse(null);
		
		if (clienteEntity!=null && clienteEntity.getFoto() !=null ) {
			return borrarFoto(clienteEntity.getFoto());
		}
		
		return false;
	}	
	
	@Override
	public boolean borrarFoto(String fileName) {
		
		var rootPath = Paths.get(FOLDER_UPLOADS).resolve(fileName).toAbsolutePath();
		var archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()){
			try {
				Files.delete(rootPath);

			} catch (IOException e) {
				//TODO pintar la excepción y la traza a fichero de errores
				e.printStackTrace();
				return false;
			}				
		}
		
		return true;
	}
}
