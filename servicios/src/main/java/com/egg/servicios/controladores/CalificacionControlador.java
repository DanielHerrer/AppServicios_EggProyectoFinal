package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CalificacionServicio;
import com.egg.servicios.servicios.ContratoServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private ContratoServicio contratoServicio;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listarCalificaciones(ModelMap modelo){
        List<Calificacion> calificaciones = calificacionServicio.listarCalificaciones();
        modelo.put("calificaciones", calificaciones);
        return "listar-calificaciones-adm.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/baja/{id}")
    public String baja(@PathVariable String id, ModelMap modelo) {

        try {
            Calificacion calificacion = new Calificacion();
            calificacionServicio.baja(id);
            modelo.addAttribute("calificacion", calificacion);
            modelo.put("exito", "Calificacion de baja!");
            return "redirect:/calificacion/listar";

        } catch (MiException ex) {

            return "redirect:/calificacion/listar";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/alta/{id}")
    public String alta(@PathVariable String id, ModelMap modelo) {

        try {
            Calificacion calificacion = new Calificacion();
            calificacionServicio.alta(id);
            modelo.addAttribute("calificacion", calificacion);
            modelo.put("exito", "Calificacion de alta!");
            return "redirect:/calificacion/listar";

        } catch (MiException ex) {

            return "redirect:/calificacion/listar";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificarCalificacion(@PathVariable String id, ModelMap modelo) {
        modelo.put("calificacion", calificacionServicio.listarPorId(id));
        return "modificar-calificacion-adm.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificado/{id}")
    public String modificadoCalificacion(ModelMap modelo,@PathVariable String id, String comentario) {
        try {
            Calificacion calificacion = calificacionServicio.listarPorId(id);
            calificacion.setComentario(comentario);

            calificacionServicio.modificarCalificacion(comentario, id);
            return "redirect:/calificacion/listar";
        } catch (MiException e) {
            modelo.put("calificacion", calificacionServicio.listarPorId(id));
            modelo.put("error", e.getMessage());
            return "modificar-calificacion-adm.html";
        }

    }
}

