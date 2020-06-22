package com.ceiba.tiendatecnologica.infraestructura;

import com.ceiba.tiendatecnologica.aplicacion.comando.ComandoProducto;
import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.testdatabuilder.ProductoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorGarantiaTest {

    public static final String ESTE_PRODUCTO_NO_CUENTA_CON_GARANTIA = "Este producto no cuenta con garantía extendida";
    private static final String CODIGO_PRODUCTO_NO_CUENTA_CON_GARANTIA = "U01TEA0150";
    private static final String CODIGO_PRODUCO_PRECIO_MENOR_TOPE = "F01TSA0151";
    private static final String CODIGO_PRODUCO_PRECIO_MAYOR_TOPE = "PROD_004";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void generarGarantiaProducto() throws Exception
    {
        ComandoProducto comandoProducto = new ProductoTestDataBuilder().buildComando();
        mvc.perform( MockMvcRequestBuilders
                .post("/garantia/{idProducto}/{nombreCliente}","F01TSA0150","felipe")
                .content(objectMapper.writeValueAsString(comandoProducto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void productoNoCuentaConGarantia() throws Exception{
        ComandoProducto comandoProducto = new ProductoTestDataBuilder().buildComando();
        mvc.perform( MockMvcRequestBuilders
                .post("/garantia/{idProducto}/{nombreCliente}",CODIGO_PRODUCTO_NO_CUENTA_CON_GARANTIA,"felipe")
                .content(objectMapper.writeValueAsString(comandoProducto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").value(ESTE_PRODUCTO_NO_CUENTA_CON_GARANTIA));
    }

    @Test
    public void crearGarantiaConPrecioMenorTope() throws Exception{
        ComandoProducto comandoProducto = new ProductoTestDataBuilder().buildComando();
        mvc.perform( MockMvcRequestBuilders
                .post("/garantia/{idProducto}/{nombreCliente}",CODIGO_PRODUCO_PRECIO_MENOR_TOPE,"felipe")
                .content(objectMapper.writeValueAsString(comandoProducto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void crearGarantiaConPrecioMayorTope() throws Exception{
        ComandoProducto comandoProducto = new ProductoTestDataBuilder().buildComando();
        mvc.perform( MockMvcRequestBuilders
                .post("/garantia/{idProducto}/{nombreCliente}",CODIGO_PRODUCO_PRECIO_MAYOR_TOPE,"felipe")
                .content(objectMapper.writeValueAsString(comandoProducto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getGarantiaPorIdMenorTope() throws Exception {
        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .get("/garantia/{id}", CODIGO_PRODUCO_PRECIO_MENOR_TOPE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String responseData = mvcResult.getResponse().getContentAsString();

        GarantiaExtendida garantia = objectMapper.readValue(responseData, GarantiaExtendida.class);
        Producto producto = garantia.getProducto();

        Calendar c = Calendar.getInstance();
        c.setTime(garantia.getFechaSolicitudGarantia());
        c.add(Calendar.DATE, 100);
        Date x = new Date();
        x.setTime(c.getTime().getTime());

        boolean precioEquivalente = (garantia.getPrecioGarantia() == producto.getPrecio()*.10);
        boolean fechaFinEquivalente = (garantia.getFechaFinGarantia().compareTo(x) == 0);

        // assert
        assertTrue(precioEquivalente);
        assertTrue(fechaFinEquivalente);

    }

    @Test
    public void getGarantiaPorIdMayorTope() throws Exception {
        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .get("/garantia/{id}", CODIGO_PRODUCO_PRECIO_MAYOR_TOPE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String responseData = mvcResult.getResponse().getContentAsString();

        GarantiaExtendida garantia = objectMapper.readValue(responseData, GarantiaExtendida.class);
        Producto producto = garantia.getProducto();

        Calendar c = Calendar.getInstance();
        c.setTime(garantia.getFechaSolicitudGarantia());
        c.add(Calendar.DATE, 200);
        Date fechaFinGarantiaEsperada = new Date();
        fechaFinGarantiaEsperada.setTime(c.getTime().getTime());


        int totalLunes = 0;
        Calendar cInicio = Calendar.getInstance();
        cInicio.setTime(garantia.getFechaSolicitudGarantia());
        Calendar cFin = Calendar.getInstance();
        cFin.setTime(fechaFinGarantiaEsperada);
        while (!cFin.before(cInicio)) {
            if (cInicio.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                totalLunes++;
            }
            cInicio.add(Calendar.DATE, 1);
        }

        c.add(Calendar.DATE, totalLunes);

        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            c.add(Calendar.DATE, 1);
        }
        fechaFinGarantiaEsperada.setTime(c.getTime().getTime());


        boolean precioEquivalente = (garantia.getPrecioGarantia() == producto.getPrecio()*.20);
        boolean fechaFinEquivalente = (garantia.getFechaFinGarantia().compareTo(fechaFinGarantiaEsperada) == 0);

        // assert
        assertTrue(precioEquivalente);
        assertTrue(fechaFinEquivalente);
    }


}
