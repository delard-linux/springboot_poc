package com.drd.springbootpoc.app.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class FacturaDTO implements Serializable {

	private static final long serialVersionUID = -5306816541822811632L;

	private Long id;

	@NotBlank
	private String descripcion;

	private String observacion;

	private Date createAt;
	
	private ClienteDTO cliente;	

	private List<ItemFacturaDTO> items;
	
	public FacturaDTO() {
		items = new ArrayList<>();
	}
	
	public FacturaDTO(Long id, @NotNull String descripcion, String observacion, 
			Date createAt, @NotEmpty List<ItemFacturaDTO> items) {
		this.id = id;
		this.descripcion = descripcion;
		this.observacion = observacion;
		this.createAt = createAt;
		this.items = items;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ItemFacturaDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemFacturaDTO> items) {
		this.items = items;
	}
	
	public void addItemFactura(ItemFacturaDTO item){
		items.add(item);
	}
	
	public Double getTotal() {
		return items.stream()
				  .map(x -> x.calcularImporte())
				  .reduce(0.0, Double::sum);
	}
	
}
