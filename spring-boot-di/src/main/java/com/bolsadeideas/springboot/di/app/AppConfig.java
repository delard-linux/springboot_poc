package com.bolsadeideas.springboot.di.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bolsadeideas.springboot.di.app.models.domain.ItemFactura;
import com.bolsadeideas.springboot.di.app.models.domain.Producto;
import com.bolsadeideas.springboot.di.app.models.services.IServicio;
import com.bolsadeideas.springboot.di.app.models.services.MiServicio;
import com.bolsadeideas.springboot.di.app.models.services.MiServicioComplejo;

@Configuration
public class AppConfig {
	
	@Bean("miServicioSimple")
	public IServicio registrarMiServicio() {
		return new MiServicio();		
	}

	@Bean("miServicioComplejo")
	@Primary
	public IServicio registrarMiServicio2() {
		return new MiServicioComplejo();		
	}

	@Bean("itemFactura")
	@Primary
	public List<ItemFactura> registrarItemsFactura(){
		
		var producto1 = new Producto("Camara Sony", 100);
		var producto2 = new Producto("Bicicleta Bianchi aro 26", 200); 
		
		var linea1 = new ItemFactura(producto1, 2);
		var linea2 = new ItemFactura(producto2, 4);
		
		return Arrays.asList(linea1, linea2);
	}
	
	@Bean("itemFacturaOficina")
	public List<ItemFactura> registrarItemsFacturaOficina(){
		
		var producto1 = new Producto("Monitor LG LCD 24", 250);
		var producto2 = new Producto("Notebook Asus", 500); 
		var producto3 = new Producto("Impresora HP Multifunción", 80); 
		var producto4 = new Producto("Escritorio Oficina", 300); 
		
		var linea1 = new ItemFactura(producto1, 2);
		var linea2 = new ItemFactura(producto2, 1);
		var linea3 = new ItemFactura(producto3, 1);
		var linea4 = new ItemFactura(producto4, 1);
		
		return Arrays.asList(linea1, linea2, linea3, linea4);
	}



}
