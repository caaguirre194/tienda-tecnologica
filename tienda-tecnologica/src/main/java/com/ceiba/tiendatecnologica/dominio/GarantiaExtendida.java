package com.ceiba.tiendatecnologica.dominio;

import java.util.Date;

public class GarantiaExtendida {

	private Producto producto;
	private Date fechaSolicitudGarantia;
	private Date fechaFinGarantia;
	private double precioGarantia;
	private String nombreCliente;
	
	public GarantiaExtendida(Producto producto) {
		this.fechaSolicitudGarantia = new Date();
		this.producto = producto;
	}

	public GarantiaExtendida(Producto producto, String nombreCliente) {
		this.fechaSolicitudGarantia = new Date();
		this.producto = producto;
		this.nombreCliente = nombreCliente;
	}

	public GarantiaExtendida(Producto producto, Date fechaSolicitudGarantia, Date fechaFinGarantia, double precioGarantia,
			String nombreCliente) {
		this.producto = producto;
		this.fechaSolicitudGarantia = fechaSolicitudGarantia;
		this.fechaFinGarantia = fechaFinGarantia;
		this.precioGarantia = precioGarantia;
		this.nombreCliente = nombreCliente;
	}

	public Producto getProducto() {
		return producto;
	}

	public Date getFechaSolicitudGarantia() {
		return fechaSolicitudGarantia;
	}

	public Date getFechaFinGarantia() {
		return fechaFinGarantia;
	}

	public double getPrecioGarantia() {
		return precioGarantia;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setFechaFinGarantia(Date fechaFinGarantia) {
		this.fechaFinGarantia = fechaFinGarantia;
	}

	public void setPrecioGarantia(double precioGarantia) {
		this.precioGarantia = precioGarantia;
	}

}
