package com.drd.springbootpoc.app.model.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.drd.springbootpoc.app.model.domain.ItemFacturaDTO;
import com.drd.springbootpoc.app.model.entity.ItemFactura;

public class ItemFacturaDTOMapper {

	private ItemFacturaDTOMapper() {}	
	
	public static List<ItemFacturaDTO> transformEntityListToDTOList(Iterable<ItemFactura> itemsFacturaEntityIterable) {

		List<ItemFacturaDTO> itemsFactura =  new ArrayList<>();
		
		itemsFacturaEntityIterable.forEach(itemFac -> 
			itemsFactura.add(transformEntityToDTO(itemFac)));
		
		return itemsFactura;
	}
	
	public static ItemFacturaDTO transformEntityToDTO(ItemFactura itemFacturaEntity) {

		return new ItemFacturaDTO(itemFacturaEntity.getId(),
				itemFacturaEntity.getCantidad(), 
				ProductoDTOMapper.transformEntityToDTO(itemFacturaEntity.getProducto()));

	}

	public static ItemFactura transformDTOToEntity(ItemFacturaDTO itemFactura) {

		var itemFacturaEntity = new ItemFactura();
		
		itemFacturaEntity.setId(itemFactura.getId());
		itemFacturaEntity.setCantidad(itemFactura.getCantidad());
		itemFacturaEntity.setProducto(ProductoDTOMapper.transformDTOToEntity(itemFactura.getProducto()));
		
		return itemFacturaEntity;

	}	
	
	
}
