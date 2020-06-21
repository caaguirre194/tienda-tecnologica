package com.ceiba.tiendatecnologica.dominio.servicio.vendedor;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.excepcion.GarantiaExtendidaException;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ServicioVendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantía extendida";
	public static final String EL_PRODUCTO_NO_PUEDE_TENER_GARANTIA = "Este producto no cuenta con garantía extendida";
	public static final double TOPE_PRECIO_PRODUCTO = 500000.0;

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	public ServicioVendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;
	}

	public void generarGarantia(String codigo, String cliente) {
		if(cuentaConGarantia(codigo)){
			if(tieneGarantia(codigo) == false ) {
				Producto producto = this.repositorioProducto.obtenerPorCodigo(codigo);
				GarantiaExtendida garantia = calcularFechaPrecioGarantia(producto.getPrecio(), new GarantiaExtendida(producto, cliente));
				this.repositorioGarantia.agregar(garantia);
			}else{
				throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
			}
		}else{
			throw new GarantiaExtendidaException(EL_PRODUCTO_NO_PUEDE_TENER_GARANTIA);
		}
	}

	public GarantiaExtendida calcularFechaPrecioGarantia(double precioProducto, GarantiaExtendida garantia){
		double valorGarantia;
		Date fechaFinGarantia;
		Date fechaInicioGarantia = garantia.getFechaSolicitudGarantia();
		if(precioProducto>TOPE_PRECIO_PRODUCTO){
			valorGarantia = precioProducto*.20;
			fechaFinGarantia = agregarDiasFecha(fechaInicioGarantia,200);
			int cantidadLunes = obtenerCantidadLunes(fechaInicioGarantia, fechaFinGarantia);
			fechaFinGarantia = agregarDiasFecha(fechaFinGarantia, cantidadLunes);
			if(esDiaDomingo(fechaFinGarantia)){
				fechaFinGarantia = agregarDiasFecha(fechaFinGarantia,1);
			}
		}else{
			valorGarantia = precioProducto*.10;
			fechaFinGarantia = agregarDiasFecha(fechaInicioGarantia,100);
		}
		garantia.setFechaFinGarantia(fechaFinGarantia);
		garantia.setPrecioGarantia(valorGarantia);
		Date date2 = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
		return garantia;
	}

	private boolean esDiaDomingo(Date fecha){
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		if (cFecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	public Date agregarDiasFecha(Date fecha, int dias) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(Calendar.DATE, dias);
		Date x = new Date();
		x.setTime(c.getTime().getTime());
		return x;
	}

	public int obtenerCantidadLunes(Date fechaInicio, Date fechaFin){
		int totalLunes = 0;
		Calendar cInicio = Calendar.getInstance();
		cInicio.setTime(fechaInicio);
		Calendar cFin = Calendar.getInstance();
		cFin.setTime(fechaFin);
		while (!cFin.before(cInicio)) {
			if (cInicio.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				totalLunes++;
			}
			cInicio.add(Calendar.DATE, 1);
		}
		return totalLunes;
	}

	public boolean tieneGarantia(String codigo) {
		Producto producto = this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
		if(producto!=null){
			return true;
		}else{
			return false;
		}
	}

	public boolean cuentaConGarantia(String codigo){
		int vocales = 0;
		for (int x = 0; x < codigo.length(); x++) {
			char actual = codigo.toLowerCase().charAt(x);
			if (actual == 'a' || actual == 'e' || actual == 'i'  || actual == 'o' ||actual == 'u') {
				vocales++;
			}
		}
		if(vocales == 3){
			return false;
		}else{
			return true;
		}
	}

}
