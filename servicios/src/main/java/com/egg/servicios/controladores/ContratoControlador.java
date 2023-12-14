package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Contrato;
import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.enumeraciones.Rol;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CalificacionServicio;
import com.egg.servicios.servicios.OfertaServicio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.egg.servicios.servicios.ContratoServicio;
import com.egg.servicios.servicios.OfertaServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Nico
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;
    @Autowired
    private OfertaServicio ofertaServicio;
    @Autowired
    private CalificacionServicio calificacionServicio;

    @PostMapping("/registro")
    public String crearContrato(ModelMap modelo, @PathVariable String idOferta) throws MiException {

        try {
            contratoServicio.crearContrato(idOferta);
            modelo.put("exito", "El contrato se creo correctamente!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "listar-servicios-cliente.html";
        }
        return "listar-servicios-cliente.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROVEEDOR','ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listarContratos(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        List<Contrato> contratos = new ArrayList<>();

        if (usuario.getRol().equals(Rol.CLIENTE)) {
            contratos = contratoServicio.listarContratosPorCliente(usuario.getId());
        } else if (usuario.getRol().equals(Rol.PROVEEDOR)) {
            contratos = contratoServicio.listarContratosPorProveedor(usuario.getId());
        }

        modelo.addAttribute("contratos", contratos);
        return "listar-contratos.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR')")
    @GetMapping("/listar/proveedor")
    public String listarProveedor(ModelMap modelo, HttpSession session) {
      List<Contrato> contratos = contratoServicio.listarContratos();
        modelo.addAttribute("contratos", contratos);
        return "listar-contratos.html";
    }

    @GetMapping("/pendientes")
    public String listarPendientes(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "listar-contratos.html";
    }

    @GetMapping("/rechazados")
    public String listarRechazados(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "listar-contratos.html";
    }

    @GetMapping("/aceptados")
    public String listarAceptados(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
        return "listar-contratos.html";
    }

    @GetMapping("/finalizados")
    public String listarFinalizados(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listarFinalizados();
        modelo.addAttribute("lista", contratos);
        return "listar-contratos.html";
    }

    @GetMapping("/estados/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("contrato", contratoServicio.getOne(id));
        return "HTML";
    }

    @PostMapping("/estados/{id}")
    public String modificar(@PathVariable String id, Estados estado, ModelMap modelo) throws MiException {

        try {
            contratoServicio.modificarContrato(id, estado);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "HTML";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_contrato_registrar";
        }
    }

    @PostMapping("/aceptar/{id}")
    public String aceptarContrato(ModelMap modelo, @PathVariable String id) {
        try {
            contratoServicio.modificarContrato(id, Estados.ACEPTADO);
            modelo.put("exito", "El Contrato fue aceptado correctamente!");
            return "redirect:../listar";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../listar";
        }
    }

    @PostMapping("/rechazar/{id}")
    public String rechazarContrato(ModelMap modelo, @PathVariable String id) {
        try {
            contratoServicio.modificarContrato(id, Estados.RECHAZADO);
            modelo.put("exito", "El Contrato fue rechazado correctamente!");
            return "redirect:../listar";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../listar";
        }
    }

    @PostMapping("/finalizar/{id}")
    public String finalizarContrato(ModelMap modelo, @PathVariable String id) {
        try {
            contratoServicio.modificarContrato(id, Estados.FINALIZADO);
            modelo.put("exito", "El Contrato fue finalizado correctamente!");
            return "redirect:../listar";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../listar";
        }
    }

    @GetMapping("/calificar/{id}")
    public String calificarContrato(@PathVariable String id, ModelMap modelo) {

        modelo.put("contrato", contratoServicio.getOne(id));
        return "registrar-calificacion.html";
    }

    @PostMapping("/calificado/{id}")
    public String calificadoContrato(@PathVariable String id, @RequestParam Integer calificacion,
                                     @RequestParam String comentario, ModelMap modelo) throws MiException {

        try {
            Contrato contrato = contratoServicio.getOne(id);
            modelo.put("contrato", contrato);

            Integer puntaje = (calificacion != null) ? calificacion : 0;

            Calificacion c = calificacionServicio.crearCalificacion(comentario,puntaje);
            contratoServicio.calificarContrato(contrato.getId(),c);

            return "redirect:../listar";

        } catch (Exception ex) {
            throw new MiException(ex.getMessage());
        }
    }

}