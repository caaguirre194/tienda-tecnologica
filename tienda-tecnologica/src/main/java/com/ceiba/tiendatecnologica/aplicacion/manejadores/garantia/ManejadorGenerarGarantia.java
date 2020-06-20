package com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia;

import com.ceiba.tiendatecnologica.dominio.servicio.producto.ServicioObtenerProducto;
import com.ceiba.tiendatecnologica.dominio.servicio.vendedor.ServicioVendedor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorGenerarGarantia {

	private final ServicioObtenerProducto servicioObtenerProducto;
	private final ServicioVendedor servicioVendedor;

	public ManejadorGenerarGarantia(ServicioObtenerProducto servicioObtenerProducto, ServicioVendedor servicioVendedor) {
		this.servicioVendedor = servicioVendedor;
		this.servicioObtenerProducto = servicioObtenerProducto;
	}
	
	@Transactional
	public void ejecutar(String codigoProducto, String nombreCliente) {
		servicioVendedor.generarGarantia(codigoProducto, nombreCliente);
	}
}
