package com.ceiba.tiendatecnologica.testdatabuilder;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GarantiaTestDataBuilder {



    public GarantiaExtendida build() {
        return new GarantiaExtendida(new ProductoTestDataBuilder().build(), new Date(),  new Date(), 240.0,
        "felipe");
    }

}
