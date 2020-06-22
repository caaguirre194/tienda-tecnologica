package com.ceiba.tiendatecnologica.testdatabuilder;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GarantiaTestDataBuilder {

    private static final Producto PRODUCTO = new ProductoTestDataBuilder().build();
    private static final Date FECHA_SOLICITUD_GARANTIA = new Date();
    private static final Date FECHA_FIN_GARANTIA = new Date();
    private static final double PRECIO_GARANTIA = 240.0;
    private static final String NOMBRE_CLIENTE = "felipe";

    private Producto producto;
    private Date fechaSolicitudGarantia;
    private Date fechaFinGarantia;
    private double precioGarantia;
    private String nombreCliente;

    public GarantiaTestDataBuilder(){
        this.producto = PRODUCTO;
        this.fechaSolicitudGarantia = FECHA_SOLICITUD_GARANTIA;
        this.fechaFinGarantia = FECHA_FIN_GARANTIA;
        this.precioGarantia = PRECIO_GARANTIA;
        this.nombreCliente = NOMBRE_CLIENTE;
    }

    public GarantiaTestDataBuilder conFechaSolicitud(Date fechaSolicitudGarantia) {
        this.fechaSolicitudGarantia = fechaSolicitudGarantia;
        return this;
    }

    public GarantiaTestDataBuilder conProducto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public GarantiaExtendida build() {
        return new GarantiaExtendida(this.producto,fechaSolicitudGarantia,  this.fechaFinGarantia, this.precioGarantia,
        this.nombreCliente);
    }
    // new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime()
}
