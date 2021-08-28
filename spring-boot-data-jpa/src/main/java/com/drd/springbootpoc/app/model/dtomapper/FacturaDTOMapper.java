package com.drd.springbootpoc.app.model.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.drd.springbootpoc.app.model.domain.FacturaDTO;
import com.drd.springbootpoc.app.model.entity.Factura;
import com.drd.springbootpoc.app.model.entity.ItemFactura;

public class FacturaDTOMapper {

	private FacturaDTOMapper() {}	
	
	public static List<FacturaDTO> transformEntityListToDTOList(Iterable<Factura> facturasEntityIterable) {

		List<FacturaDTO> facturas =  new ArrayList<>();
		
		facturasEntityIterable.forEach(fac -> 
						facturas.add(transformEntityToDTO(fac)));
		
		return facturas;
	}
	
	public static FacturaDTO transformEntityToDTO(Factura facturaEntity) {

		var factura =  new FacturaDTO(facturaEntity.getId(),
				facturaEntity.getDescripcion(), 
				facturaEntity.getObservacion(), 
				facturaEntity.getCreateAt(), 
				ItemFacturaDTOMapper.transformEntityListToDTOList(facturaEntity.getItems())); 
		
		if (facturaEntity.getCliente() != null) {
			factura.setCliente(ClienteDTOMapper.transformEntityToDTO(facturaEntity.getCliente()));
		}
				
		return factura;
		
	}

	public static Factura transformDTOToEntity(FacturaDTO factura) {

		var facturaEntity = new Factura();
		
		var itemsList = new ArrayList<ItemFactura>();
		
		factura.getItems().forEach(itemFac -> 
			itemsList.add(ItemFacturaDTOMapper.transformDTOToEntity(itemFac)));
		
		facturaEntity.setId(factura.getId());
		facturaEntity.setDescripcion(factura.getDescripcion());
		facturaEntity.setObservacion(factura.getObservacion());
		facturaEntity.setCreateAt(factura.getCreateAt());
		facturaEntity.setItems(itemsList);
		facturaEntity.setCliente(ClienteDTOMapper.transformDTOToEntity(factura.getCliente()));
		
		return facturaEntity;

	}	
	
	
}
