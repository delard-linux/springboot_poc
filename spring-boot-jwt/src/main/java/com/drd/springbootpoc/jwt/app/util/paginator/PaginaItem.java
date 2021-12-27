package com.drd.springbootpoc.jwt.app.util.paginator;

public class PaginaItem {
	private int numero;
	private boolean actual;

	public PaginaItem(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}

	public boolean isActual() {
		return actual;
	}
}
