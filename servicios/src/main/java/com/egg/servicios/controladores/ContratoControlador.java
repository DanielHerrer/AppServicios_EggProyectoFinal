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
<<<<<<< HEAD
    private ContratoServicio contratoServicio;
=======
    private ContratoServicios contratoService;
>>>>>>> 99a8f37c858b0f07702d79eeeafd21001e67afe5

    @GetMapping("/registrar")
    public String crearContratoAlta(ModelMap modelo) {
        return "test_contrato_registrar";
    }

    @PostMapping("/registro")
    public String crearContrato(ModelMap modelo, @PathVariable String idOferta) throws MiException {

        try {
<<<<<<< HEAD
            contratoServicio.crearContrato(idOferta);
            modelo.put("exito", "El contrato se creo correctamente!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "test_contrato_registrar";
        }
        return "test.html";
=======
            contratoService.guardarContrato(estados, oferta, aptitud);
            modelo.put("exito", "El Contrato fue cargado correctamente!");
            return "inicio.html";
        } catch (MiException e) {
            modelo.put("error", "Error al registrar el contrato.");
            return "registrar.html";
        }
>>>>>>> 99a8f37c858b0f07702d79eeeafd21001e67afe5
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
<<<<<<< HEAD
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("contrato", contratoServicio.getOne(id));
        return "HTML";
=======
    public String modificarEstados(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("contrato", contratoService.listarContratosPorId(id));
            return "test_contra_lista.html";
        } catch (MiException e) {
            return "test_modificar_contra.html";
        }
>>>>>>> 99a8f37c858b0f07702d79eeeafd21001e67afe5
    }

    @PostMapping("/estados/{id}")
    public String modificarEstados(@PathVariable String id, Estados estado, ModelMap modelo) {
        try {
            contratoServicio.modificarContrato(id,estado);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
<<<<<<< HEAD
            return "HTML";
=======
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
            contratoService.altaBajaContrato(id);
            return "redirect:.../lista";
>>>>>>> 99a8f37c858b0f07702d79eeeafd21001e67afe5
        } catch (MiException ex) {

            System.out.println("");
            modelo.put("error", ex.getMessage());
            return "HTML";
        }
    }

<<<<<<< HEAD
}
=======
    @GetMapping("/modificar/{id}")
    public String modificarContrato(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("contrato", contratoService.listarContratosPorId(id));
            return "test_modificar_contra.html";
        } catch (MiException e) {
            return "test_modificar_contra.html";
        }
    }

    @PostMapping("/modificado/{id}")
    public String modificadoContrato(@PathVariable String id, Estados estados, ModelMap modelo) {
        try {
            contratoService.estadosDeContratos(id, estados);
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
            contratoService.contratoFinalizado(id, estados, calificacion);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "redirect:..";
        } catch (MiException e) {
            System.out.println("");
                  modelo.put("error", "Error al modificar el contrato.");
            return "";
        }
    }

}
>>>>>>> 99a8f37c858b0f07702d79eeeafd21001e67afe5
