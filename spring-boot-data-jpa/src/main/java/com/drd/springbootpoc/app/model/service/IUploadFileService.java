package com.drd.springbootpoc.app.model.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	public Resource load(String nombreFichero) throws MalformedURLException;

	public String upload( MultipartFile fichero) throws IOException;
	
	public boolean delete (String nombreFichero) throws IOException;

	public void deleteAll();

	public void init() throws IOException;
	
}
