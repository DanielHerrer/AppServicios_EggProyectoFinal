/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Contrato;
import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.OfertaServicio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.servicios.servicios.ContratoServicios;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// NO BORRAR
//        Arbol de trabajo de Servicio/Oferta/Contrato/Calificacion
//        -----------
//        El Servicio es ofertado por el Cliente =>
//        El Cliente genera una Oferta =>
//        Se genera un Contrato con toda la info =>
//        Ambos usuarios mostrarían sus Contratos =>
//        El Proveedor debe aceptar o rechazar el Contrato =>
//        El Cliente va a poder ver el estado de su Contrato =>
//        ----------------
//        El Proveedor acepta el Contrato =>
//        El Proveedor finaliza el Contrato =>
//        El Cliente califica el Contrato=>

/**
 *
 * @author joaquin
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicios contratoServicio;
    @Autowired
    private OfertaServicio ofertaServicio;

    @GetMapping("/lista")
    public String listar(ModelMap modelo) throws MiException {
        List<Contrato> listaContra = contratoServicio.listarContratos();
        modelo.addAttribute("listaContra", listaContra);

        return "test_contrato_lista.html";
    }

    @GetMapping("/estados/{id}")
    public String modificarEstados(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("contrato", contratoServicio.listarContratosPorId(id));
            return "test_contra_lista.html";
        } catch (MiException e) {
            return "test_modificar_contra.html";
        }
    }

    @PostMapping("/estados/{id}")
    public String modificarEstados(@PathVariable String id, Estados estados, ModelMap modelo) {
        try {
            contratoServicio.modificarEstadoContrato(id, estados);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "redirect:..";
        } catch (MiException e) {

            System.out.println("");
            modelo.put("error", "Error al modificar el contrato.");
            return "";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        try {
            contratoServicio.altaBajaContrato(id);
            return "redirect:.../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "";
        }
    }
    
    /*th:href="@{/contrato/listar/cliente}"*/

     @GetMapping("/listar/cliente")
    public String listarPorCliente(ModelMap modelo) throws MiException {
        List<Contrato> contratos = contratoServicio.listarContratos();
        modelo.addAttribute("contratos", contratos);

        return "test_contrato_lista.html";   
    }
    
    
        @GetMapping("/listar/proveedor")
    public String listarPorProveedor(ModelMap modelo) throws MiException {
        List<Contrato> contratos = contratoServicio.listarContratos();
        modelo.addAttribute("contratos", contratos);

        return "test_contrato_lista.html";
    }
    
    
    
    @GetMapping("/modificar/{id}")
    public String modificarContrato(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("contrato", contratoServicio.listarContratosPorId(id));
            return "test_modificar_contra.html";
        } catch (MiException e) {
            return "test_modificar_contra.html";
        }
    }

    @PostMapping("/modificado/{id}")
    public String modificadoContrato(@PathVariable String id, Estados estados, ModelMap modelo) {
        try {
            contratoServicio.estadosDeContratos(id, estados);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "redirect:..";
        } catch (MiException e) {
            System.out.println("");
                  modelo.put("error", "Error al modificar el contrato.");
            return "";
        }
    }

    @PostMapping("/modificar/{id}")
    public String finalizarContrato(@PathVariable String id, Estados estados, Calificacion calificacion, ModelMap modelo) {
        try {
            contratoServicio.contratoFinalizado(id, estados, calificacion);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "redirect:..";
        } catch (MiException e) {
            System.out.println("");
                  modelo.put("error", "Error al modificar el contrato.");
            return "";
        }
    }

}