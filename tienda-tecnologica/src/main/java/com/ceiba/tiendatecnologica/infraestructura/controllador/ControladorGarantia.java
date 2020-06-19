package com.ceiba.tiendatecnologica.infraestructura.controllador;

import com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia.ManejadorGenerarGarantia;
import com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia.ManejadorObtenerGarantia;
import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garantia")
public class ControladorGarantia {
	private final ManejadorObtenerGarantia manejadorObtenerGarantia;

	public ControladorGarantia(ManejadorObtenerGarantia manejadorObtenerGarantia) {
		this.manejadorObtenerGarantia = manejadorObtenerGarantia;
	}

	@PostMapping("/{idProducto}/{nombreCliente}")
	public void generar(@PathVariable(name = "idProducto") String codigoProducto) {
		throw new UnsupportedOperationException("Método pendiente por implementar");
	}

	@GetMapping("/{id}")
	public GarantiaExtendida buscar(@PathVariable(name = "id") String codigo) {
		return this.manejadorObtenerGarantia.ejecutar(codigo);
	}
}
