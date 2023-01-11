package org.iesvegademijas.model;

import java.util.Objects;

public class Producto {

	private int codigo;
	private String nombre;
	private double precio;
	private int codigo_fabricante;

	
	
	public Producto() {
		super();
		// TODO Apéndice de constructor generado automáticamente
	}

	public Producto( String nombre, double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}
	
	public Producto( String nombre, double precio, int codigo_fabricante) {
		this.nombre = nombre;
		this.precio = precio;
		this.codigo_fabricante = codigo_fabricante;
	}
	
	public Producto(int codigo, String nombre, double precio, int codigo_fabricante) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.codigo_fabricante = codigo_fabricante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public int getCodigoFabricante() {
		return codigo_fabricante;
	}

	public void setCodigoFabricante(int codigoFabricante) {
		this.codigo_fabricante = codigoFabricante;
	}
		
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo, codigo_fabricante, nombre, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return codigo == other.codigo && codigo_fabricante == other.codigo_fabricante
				&& Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}

	//cuando utilice todos los atributos hay que usar el toString de arriba en lugar del inferior
	/*
	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", nombre=" + nombre +  " ]";
	}*/
	
	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", codigoFabricante=" + codigo_fabricante + " ]";
	}
}
