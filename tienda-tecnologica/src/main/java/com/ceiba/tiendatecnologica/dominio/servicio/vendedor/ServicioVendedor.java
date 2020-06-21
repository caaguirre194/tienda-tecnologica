package com.ceiba.tiendatecnologica.dominio.servicio.vendedor;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.excepcion.GarantiaExtendidaException;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Calendar;

public class ServicioVendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantía extendida";
	public static final String EL_PRODUCTO_NO_PUEDE_TENER_GARANTIA = "Este producto no cuenta con garantía extendida";

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
				GarantiaExtendida garantia = new GarantiaExtendida(producto, cliente);
				this.repositorioGarantia.agregar(garantia);
			}else{
				throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
			}
		}else{
			throw new GarantiaExtendidaException(EL_PRODUCTO_NO_PUEDE_TENER_GARANTIA);
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
