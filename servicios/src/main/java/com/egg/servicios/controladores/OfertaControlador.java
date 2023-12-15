package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Contrato;
import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.OfertaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Daniel
 */
@Controller
@RequestMapping("/oferta")
public class OfertaControlador {

    @Autowired
    OfertaServicio ofertaServicio;

    @PostMapping("/registro") // localhost:8080/oferta/registro
    public String registro(@RequestParam String descripcion, @RequestParam String idServicio,
            @RequestParam String idCliente, ModelMap modelo) {
        try {

            ofertaServicio.crearOferta(descripcion, idServicio, idCliente);

            modelo.put("exito", "La Oferta fue registrada correctamente!"); // carga el modelo con un mensaje exitoso

            return "test_oferta_create.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage()); // carga el modelo con un mensaje de error

            modelo.put("descripcion", descripcion);
            modelo.put("idServicio", idServicio);
            modelo.put("idCliente", idCliente);

            return "test_oferta_create.html"; // volvemos a cargar el formulario
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<Oferta> ofertas = ofertaServicio.listarOfertas();
        modelo.addAttribute("ofertas", ofertas);

        return "listar-ofertas-adm.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo, HttpSession session) {

//        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
//        modelo.put("usuario", usuario);

        Oferta oferta = ofertaServicio.listarPorId(id);
        modelo.put("oferta",oferta);

        return "modificar-oferta-adm.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable String id, @RequestParam String descripcion,
            @RequestParam String idServicio, @RequestParam String idCliente, ModelMap modelo) {

        try {

            ofertaServicio.actualizarOferta(id, descripcion, idServicio, idCliente);

            modelo.put("exito", "Oferta actualizada correctamente!");

            return "redirect:../listar";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("descripcion", descripcion);
            modelo.put("idServicio", idServicio);
            modelo.put("idCliente", idCliente);
            return "modificar-oferta-adm.html";
        }
    }

    
    @GetMapping("/msj")
    public String notifications(ModelMap modelo, HttpSession session) {
        List<Oferta> ofertas = ofertaServicio.listarOfertas();
        modelo.addAttribute("ofertas", ofertas);
        return "notificaciones_app.html";
    }

}
