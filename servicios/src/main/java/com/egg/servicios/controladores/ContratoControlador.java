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

import com.egg.servicios.servicios.ContratoServicio;
import com.egg.servicios.servicios.OfertaServicio;
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
 * @author Nico
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
<<<<<<< HEAD
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

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listaCompleta();
        modelo.addAttribute("lista", contratos);
=======
    private ContratoServicios contratoServicio;
    @Autowired
    private OfertaServicio ofertaServicio;

    @GetMapping("/lista")
    public String listar(ModelMap modelo) throws MiException {
        List<Contrato> listaContra = contratoServicio.listarContratos();
        modelo.addAttribute("listaContra", listaContra);

>>>>>>> ae25500036477b55ad4fc1c07a5ad4b41bd01254
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

//    @GetMapping("/contratosproveedores")
//    public String listarProveedores(ModelMap modelo) {
//        List<Contrato> contratos = contratoServicio.listaCompleta();
//        modelo.addAttribute("lista", contratos);
//        return "";
//    }
//
//    @GetMapping("/contratosclientes")
//    public String listarClientes(ModelMap modelo) {
//        List<Contrato> contratos = contratoServicio.getOne(id);
//        modelo.addAttribute("lista", contratos);
//        return "";
//    }

    @GetMapping("/estados/{id}")
<<<<<<< HEAD
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("contrato", contratoServicio.getOne(id));
        return "HTML";
=======
    public String modificarEstados(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("contrato", contratoServicio.listarContratosPorId(id));
            return "test_contra_lista.html";
        } catch (MiException e) {
            return "test_modificar_contra.html";
        }
>>>>>>> ae25500036477b55ad4fc1c07a5ad4b41bd01254
    }

    @PostMapping("/estados/{id}")
    public String modificarEstados(@PathVariable String id, Estados estado, ModelMap modelo) {
        try {
<<<<<<< HEAD
            contratoServicio.modificarContrato(id, estado);
=======
            contratoServicio.modificarEstadoContrato(id, estados);
>>>>>>> ae25500036477b55ad4fc1c07a5ad4b41bd01254
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "HTML";

        } catch (MiException e) {
            modelo.put("error", "Error al modificar el contrato.");
<<<<<<< HEAD
            return "HTML";
=======
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
>>>>>>> ae25500036477b55ad4fc1c07a5ad4b41bd01254
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
<<<<<<< HEAD
        modelo.put("contrato", contratoServicio.getOne(id));

        return "test_modificar_contra.html";
=======
        try {
            modelo.put("contrato", contratoServicio.listarContratosPorId(id));
            return "test_modificar_contra.html";
        } catch (MiException e) {
            return "test_modificar_contra.html";
        }
>>>>>>> ae25500036477b55ad4fc1c07a5ad4b41bd01254
    }

    @PostMapping("/modificado/{id}")
    public String calificarContrato(@PathVariable String id, Calificacion calificacion, Estados estado, ModelMap modelo) {
        try {
<<<<<<< HEAD
            contratoServicio.calificarContrato(id, calificacion, estado);
=======
            contratoServicio.estadosDeContratos(id, estados);
>>>>>>> ae25500036477b55ad4fc1c07a5ad4b41bd01254
            modelo.put("exito", "El Contrato fue modificado correctamente!");

            return "redirect:..";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "";
        }
    }

    @PostMapping("/aceptarcontrato/{id}")
    public String aceptarContrato(ModelMap modelo, String idContrato) {
        try {
<<<<<<< HEAD
            contratoServicio.modificarContrato(idContrato, Estados.ACEPTADO);
            return "";
=======
            contratoServicio.contratoFinalizado(id, estados, calificacion);
            modelo.put("exito", "El Contrato fue modificado correctamente!");
            return "redirect:..";
>>>>>>> ae25500036477b55ad4fc1c07a5ad4b41bd01254
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "";
        }
    }

    @PostMapping("/rechazarcontrato/{id}")
    public String rechazarContrato(ModelMap modelo, String idContrato) {
        try {
            contratoServicio.modificarContrato(idContrato, Estados.RECHAZADO);
            return "";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "";
        }
    }

}
