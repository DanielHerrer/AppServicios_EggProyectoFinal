
package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CalificacionServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author daniel
 */
@Controller
@RequestMapping("/calificacion")

public class CalificacionControlador {
    @Autowired
    private CalificacionServicio calificacionServicio;

    @GetMapping("/registrar/{idContrato}")
    public String calificar(@PathVariable String idContrato, @RequestParam String comentario, @RequestParam Integer puntuacion, ModelMap modelo){
        try {

            calificacionServicio.crearCalificacion(comentario, puntuacion);
            modelo.put("exito", "Muchas gracias por calificar!");

            return "redirect:/inicio";

        } catch (MiException e) {
            modelo.put("error", "Ingrese una calificacion");
            return "registrar-calificacion.html";
        }
    }

//    @PostMapping("/calificado/{id}")
//    public String calificarContrato(@PathVariable String id, @RequestParam String comentario, @RequestParam Integer puntuacion, ModelMap modelo) throws MiException {
//        try {
//            contratoServicio.calificarContrato(id, calificacion);
//            modelo.put("exito", "El Contrato fue modificado correctamente!");
//
//            return "redirect:/inicio";
//
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//            return "registrar-calificacion.html";
//        }
//    }
 
    @GetMapping("/listar")
    public String listarCalificaciones(ModelMap modelo){
       
        List<Calificacion> listaCali = calificacionServicio.listarCalificaciones();
        modelo.addAttribute("listaContra", listaCali);
        return "test_calificacion_lista.html";
    }

}