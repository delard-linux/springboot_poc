package com.drd.springbootpoc.jwt.app.model.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.drd.springbootpoc.jwt.app.model.domain.ProductoDTO;
import com.drd.springbootpoc.jwt.app.model.entity.Producto;

public class ProductoDTOMapper {

	private ProductoDTOMapper() {}	
	
	public static List<ProductoDTO> transformEntityListToDTOList(Iterable<Producto> productosEntityIterable) {

		List<ProductoDTO> productos =  new ArrayList<>();
		
		productosEntityIterable.forEach(pr -> 
			productos.add(transformEntityToDTO(pr)));
		
		return productos;
	}
	
	public static ProductoDTO transformEntityToDTO(Producto productoEntity) {

		return new ProductoDTO(productoEntity.getId(),
				productoEntity.getNombre(), 
				productoEntity.getPrecio(), 
				productoEntity.getCreateAt());

	}

	public static Producto transformDTOToEntity(ProductoDTO producto) {

		var productoEntity = new Producto();
		
		productoEntity.setId(producto.getId());
		productoEntity.setNombre(producto.getNombre());
		productoEntity.setPrecio(producto.getPrecio());
		productoEntity.setCreateAt(producto.getCreateAt());
		
		return productoEntity;

	}	
	
	
}
