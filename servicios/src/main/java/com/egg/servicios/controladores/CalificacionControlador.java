package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CalificacionServicio;
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

/**
 *
 * @author jrcon
 */
@Controller
@RequestMapping("/calificacion")

public class CalificacionControlador {

    @Autowired
    private CalificacionServicio calificacionServicio;

    @GetMapping("/registrar")
    public String calificar(String comentario, Integer puntuacion, ModelMap modelo) {
        try {
            calificacionServicio.crearCalificacion(comentario, puntuacion);
            modelo.put("exito", "Muchas gracias por calificar!!");
            return "inicio.html";
        } catch (MiException e) {

            modelo.put("error", "Ingrese una calificacion");
            return "test_calificacion_read.html";
        }
    }

    @GetMapping("/listar")
    public String calificacion(ModelMap modelo) {

        List<Calificacion> listaCali = calificacionServicio.listarCalificaciones();
        modelo.addAttribute("listaContra", listaCali);
        return "test_calificacion_lista.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/baja/{id}")
    public String baja(@PathVariable String id, ModelMap modelo) {

        try {
            Calificacion calificacion = new Calificacion();
            calificacionServicio.baja(id);
            modelo.addAttribute("calificacion", calificacion);
            modelo.put("exito", "Calificacion de baja!");
            return "redirect:../calificacion/listar";

        } catch (MiException ex) {

            return "redirect:../calificacion/listar";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/alta/{id}")
    public String alta(@PathVariable String id, ModelMap modelo) {

        try {
            Calificacion calificacion = new Calificacion();
            calificacionServicio.baja(id);
            modelo.addAttribute("calificacion", calificacion);
            modelo.put("exito", "Calificacion de alta!");
            return "redirect:../calificacion/listar";

        } catch (MiException ex) {

            return "redirect:../calificacion/listar";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificarcalificacion/{id}")
    public String modificarCalificacion(@PathVariable String id, ModelMap modelo) {
        modelo.put("calificacion", calificacionServicio.listarPorId(id));
        return "calificacion_modificar.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/calificacionmodificada/{id}")
    public String modificadoCalificacion(ModelMap modelo,@PathVariable String id, String comentario, Integer puntuacion) {
        try {
        calificacionServicio.modificarCalificacion(comentario, puntuacion, id);
        return "redirect:../calificacion/listar";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:../calificacion/listar";
        }

    }
}
