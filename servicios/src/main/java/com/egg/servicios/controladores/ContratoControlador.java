package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Contrato;
import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.OfertaServicio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.servicios.servicios.ContratoServicio;
import com.egg.servicios.servicios.OfertaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;

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
 * @author Nico
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;

    @Autowired
    private OfertaServicio ofertaServicio;

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

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR')")
    @GetMapping("/listar/proveedor")
    public String listarProveedor(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        List<Contrato> contratos = contratoServicio.listarContratosPorProveedor(usuario.getId());

        modelo.addAttribute("lista", contratos);
        return "test_contrato_lista.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE')")
    @GetMapping("/listar/cliente")
    public String listarCliente(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        List<Contrato> contratos = contratoServicio.listarContratosPorCliente(usuario.getId());

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
        List<Contrato> contratos = contratoServicio.listarFinalizados();
        modelo.addAttribute("lista", contratos);
        return "test_contrato_lista.html";
    }

    @GetMapping("/estados/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("contrato", contratoServicio.getOne(id));
        return "HTML";
    }

    @PostMapping("/estados/{id}")
    public String modificar(@PathVariable String idContrato, Estados estado, ModelMap modelo) throws MiException {

        try {
            contratoServicio.modificarContrato(idContrato, estado);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "HTML";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_contrato_registrar";
        }
    }

    @PostMapping("/aceptar/{id}")
    public String AceptarContrato(@PathVariable String idContrato, ModelMap modelo) throws MiException {

        try {
            contratoServicio.modificarContrato(idContrato, Estados.ACEPTADO);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "HTML";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_contrato_registrar";
        }
    }

    @PostMapping("/rechazar/{id}")
    public String RechazarContrato(@PathVariable String idContrato, ModelMap modelo) throws MiException {

        try {
            contratoServicio.modificarContrato(idContrato, Estados.RECHAZADO);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "HTML";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_contrato_registrar";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String EliminarContrato(@PathVariable String idContrato, ModelMap modelo) throws MiException {

        try {
            contratoServicio.modificarContrato(idContrato, Estados.ACEPTADO);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "HTML";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_contrato_registrar";
        }
    }


    /*th:href="@{/contrato/listar/cliente}"*/
    @GetMapping("/calificar/{id}")
    public String calificarContrato(@PathVariable String idContrato, ModelMap modelo) {

        modelo.put("contrato", contratoServicio.getOne(idContrato));
        return "test_modificar_contra.html";
    }

    @PostMapping("/calificado/{id}")
    public String calificarContrato(@PathVariable String id, Calificacion calificacion, ModelMap modelo) throws MiException {
        try {
            contratoServicio.calificarContrato(id, calificacion);
            modelo.put("exito", "El Contrato fue modificado correctamente!");

            return "redirect:..";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "";
        }
    }

    @PostMapping("/aceptarcontrato/{id}")
    public String aceptarContrato(ModelMap modelo, String idContrato) {
        try {
            contratoServicio.modificarContrato(idContrato, Estados.ACEPTADO);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "redirect:..";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "";
        }
    }

    @PostMapping("/rechazarcontrato/{id}")
    public String rechazarContrato(ModelMap modelo, String idContrato) {
        try {
            contratoServicio.modificarContrato(idContrato, Estados.RECHAZADO);
            return "";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "";
        }
    }

}
