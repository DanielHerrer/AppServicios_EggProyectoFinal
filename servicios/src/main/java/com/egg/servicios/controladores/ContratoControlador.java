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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.servicios.servicios.ContratoServicio;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Nico
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;

    @GetMapping("/registrar")
    public String crearContratoAlta(ModelMap modelo) {
        return "test_contrato_registrar";
    }

    @PostMapping("/registro")
    public String crearContrato(ModelMap modelo, @PathVariable String idOferta) throws MiException {

        try {
            contratoServicio.crearContrato(idOferta);
            modelo.put("exito", "El contrato se creo correctamente!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "test_contrato_registrar";
        }
        return "test.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "test_contrato_lista.html";
    }

    @GetMapping("/pendientes")
    public String listarPendientes(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "test_contrato_lista.html";
    }

    @GetMapping("/rechazados")
    public String listarRechazados(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "test_contrato_lista.html";
    }

    @GetMapping("/aceptados")
    public String listarAceptados(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "test_contrato_lista.html";
    }

    @GetMapping("/finalizados")
    public String listarFinalizados(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "test_contrato_lista.html";
    }

    @GetMapping("/estados/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("contrato", contratoServicio.getOne(id));
        return "HTML";
    }

    @PostMapping("/estados/{id}")
    public String modificarEstados(@PathVariable String id, Estados estados, ModelMap modelo) {
        try {
            contratoServicio.modificarContrato(id);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "HTML";
        } catch (MiException ex) {

            System.out.println("");
            modelo.put("error", ex.getMessage());
            return "HTML";
        }

    }

}
