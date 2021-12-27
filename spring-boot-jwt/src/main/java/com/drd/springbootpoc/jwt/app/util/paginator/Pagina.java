package com.drd.springbootpoc.jwt.app.util.paginator;

import java.util.List;

import org.springframework.data.domain.Page;

public class Pagina<T> {
	
	private List<T> contenido;
	private int paginaSize;
	private int numeroDePaginas;
	private int numeroPaginaActual;
	
	private boolean first;
	private boolean last;
	private boolean hasNext;
	private boolean hasPrevious;
	
	public Pagina(Page<?> page, List<T> nuevoContenido) {
		this.paginaSize = page.getSize();
		this.numeroDePaginas = page.getTotalPages();
		this.numeroPaginaActual = page.getNumber()+1;
		this.first = page.isFirst();
		this.last = page.isLast();
		this.hasNext = page.hasNext();
		this.hasPrevious = page.hasPrevious();
		this.contenido = nuevoContenido;
	}
	
	public List<T> getContenido() {
		return contenido;
	}
	
	public int getPaginaSize() {
		return paginaSize;
	}

	public int getNumeroDePaginas() {
		return numeroDePaginas;
	}

	public int getNumeroPaginaActual() {
		return numeroPaginaActual;
	}
	
	public boolean isFirst() {
		return first;
	}

	public boolean isLast() {
		return last;
	}

	public boolean hasNext() {
		return hasNext;
	}

	public boolean hasPrevious() {
		return hasPrevious;
	}

	
}
