package com.drd.springbootpoc.app.util.paginator;

import java.util.List;

public class Pagina<T> {
	
	private List<T> contenido;
	private int paginaSize;
	private int numeroDePaginas;
	private int numeroPaginaActual;

	public List<T> getContenido() {
		return contenido;
	}

	public void setContenido(List<T> contenido) {
		this.contenido = contenido;
	}
	
	public int getPaginaSize() {
		return paginaSize;
	}

	public void setPaginaSize(int paginaSize) {
		this.paginaSize = paginaSize;
	}

	public int getNumeroDePaginas() {
		return numeroDePaginas;
	}

	public void setNumeroDePaginas(int numeroDePaginas) {
		this.numeroDePaginas = numeroDePaginas;
	}

	public int getNumeroPaginaActual() {
		return numeroPaginaActual;
	}

	public void setNumeroPaginaActual(int numeroPaginaActual) {
		this.numeroPaginaActual = numeroPaginaActual;
	}
}
