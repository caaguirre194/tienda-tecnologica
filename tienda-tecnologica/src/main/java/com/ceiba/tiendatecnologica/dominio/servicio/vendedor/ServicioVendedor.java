package com.ceiba.tiendatecnologica.dominio.servicio.vendedor;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.excepcion.GarantiaExtendidaException;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;

import java.util.Calendar;

public class ServicioVendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantía extendida";

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	public ServicioVendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;
	}

	public void generarGarantia(String codigo, String cliente) {
		if(tieneGarantia(codigo)!=false){
			Producto producto = this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
			GarantiaExtendida garantia = new GarantiaExtendida(producto,cliente);
			this.repositorioGarantia.agregar(garantia);
		}
	}

	public boolean tieneGarantia(String codigo) {
		Producto producto = this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
		if(producto!=null){
			return true;
		}else{
			return false;
		}
	}
}
