package com.drd.springbootpoc.jwt.app.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.drd.springbootpoc.jwt.app.exception.AppException;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private static final String FOLDER_UPLOADS = "uploads";
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Resource load(String nombreFichero) throws MalformedURLException{
		
		var pathFichero = Paths.get(FOLDER_UPLOADS).resolve(nombreFichero).toAbsolutePath();
		log.info("pathFoto: {}", pathFichero);
		
		Resource recurso = null;
		
			recurso = new UrlResource(pathFichero.toUri());
			if(!recurso.exists() || !recurso.isReadable()) {
				throw new AppException("Error: no se puede obtener la imagen: " + pathFichero.toString());
			}
		
		return recurso;
	}

	@Override
	public String upload(MultipartFile fichero) throws IOException{
		
		String nombreFichero = null;
		InputStream streamFichero = null;

			if (fichero!=null 
					&& fichero.getOriginalFilename()!=null) {				
				nombreFichero = fichero.getOriginalFilename();
				streamFichero = fichero.getInputStream();	
			}

		String uniqueFilename = UUID.randomUUID().toString() + "_" + nombreFichero;
		var rootPath = Paths.get(FOLDER_UPLOADS).resolve(uniqueFilename);

		var rootAbsolutePath = rootPath.toAbsolutePath();
		
		log.info("rootPath: {}", rootPath);
		log.info("rootAbsolutPath: {}",rootAbsolutePath);

		Files.copy(streamFichero, rootAbsolutePath);

		return uniqueFilename;
	}
		
	@Override
	public boolean delete(String nombreFichero) throws IOException {
		
		var rootPath = Paths.get(FOLDER_UPLOADS).resolve(nombreFichero).toAbsolutePath();
		var archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()){
			Files.delete(rootPath);
		}
		
		return true;
	}
	
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(FOLDER_UPLOADS).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(FOLDER_UPLOADS));
	}
}
