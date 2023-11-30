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

import com.egg.servicios.servicios.ContratoServicios;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author joaquin
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicios contratoService;

    @GetMapping("/registrar")
    public String guardarContrato(Estados estados, Oferta oferta, Calificacion aptitud, ModelMap modelo) {

        try {
            contratoService.guardarContrato(estados, oferta, aptitud);
            modelo.put("exito", "El Contrato fue cargado correctamente!");
            return "inicio.html";
        } catch (MiException e) {
            modelo.put("error", "Error al registrar el contrato.");
            return "registrar.html";
        }

    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) throws MiException  {
        List<Contrato> listaContra = contratoService.listarContratos();
        modelo.addAttribute("listaContra", listaContra);
        return "test_contrato_lista.html";

    }

     @GetMapping("/modificar/{id}")
    public String modificarContrato(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("contrato", contratoService.listarContratosPorId(id));
            return "test_modificar_contra.html";
        } catch (MiException e) {
            return "test_modificar_contra.html";
        }

    }

    
    @PostMapping("/modificar/{id}")
    public String estadosDeContratos(@PathVariable String id, Estados estados, ModelMap modelo) {
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
    
/*
    @PostMapping("/modificar/{id}")
    public String modificarContrato(@PathVariable String id, Estados estados, Calificacion calificacion, ModelMap modelo) {
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
*/
}
