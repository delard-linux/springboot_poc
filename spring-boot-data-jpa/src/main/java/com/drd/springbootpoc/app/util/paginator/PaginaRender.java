package com.drd.springbootpoc.app.util.paginator;

import java.util.List;

import org.springframework.data.domain.Page;

public class PaginaRender<T> {
	
	private Page<T> pagina;
	private int numElementosPorPagina;
	
	private String url;
	private int totalPaginas;
	private int numPaginaActual;
	private List<PaginaItem> paginas;
	
	public PaginaRender(String url, Page<T> pagina) {
		super();
		this.url = url;
		this.pagina = pagina;
		
		numElementosPorPagina = 6;
		this.totalPaginas = pagina.getTotalPages();
		this.numPaginaActual = pagina.getNumber();
		
		int desde;
		int hasta;
		if (totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (numPaginaActual <= numElementosPorPagina / 2) {
				desde = 1;
				hasta = numElementosPorPagina;
			} else if (numPaginaActual >= totalPaginas - numElementosPorPagina / 2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			} else {
				desde = numPaginaActual - numElementosPorPagina / 2;
				hasta = numElementosPorPagina;
			}
		}
		
		for (var i = 0; i < hasta; i++) {
			paginas.add(new PaginaItem(desde + i, numPaginaActual == desde + i));
		}
	}
	
	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getNumPaginaActual() {
		return numPaginaActual;
	}

	public List<PaginaItem> getPaginas() {
		return paginas;
	}
	
	public boolean isFirst() {
		return pagina.isFirst();
	}

	public boolean isLast() {
		return pagina.isLast();
	}

	public boolean isHasNext() {
		return pagina.hasNext();
	}

	public boolean isHasPrevious() {
		return pagina.hasPrevious();
	}
	
}
