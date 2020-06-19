package com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorGenerarGarantia {

	public ManejadorGenerarGarantia() {}
	
	@Transactional
	public void ejecutar(String codigoProducto,String nombreCliente) {
		throw new UnsupportedOperationException("Método pendiente por implementar");
	}
}
