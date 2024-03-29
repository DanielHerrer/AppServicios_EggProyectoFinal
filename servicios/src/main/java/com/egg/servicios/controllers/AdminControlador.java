package com.egg.servicios.controllers;

import com.egg.servicios.entities.Calificacion;
import com.egg.servicios.entities.Contrato;
import com.egg.servicios.entities.Oferta;
import com.egg.servicios.entities.Servicio;
import com.egg.servicios.enums.Rol;
import com.egg.servicios.enums.Ubicacion;
import com.egg.servicios.exceptions.MiException;
import com.egg.servicios.services.*;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nico
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private ContratoService contratoService;
    @Autowired
    private CalificacionService calificacionService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ServicioService servicioService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    //USUARIOS -------------------------------------->
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar/cliente")
    public String registrarCliente(ModelMap modelo) {

        modelo.put("rol", Rol.CLIENTE);

        modelo.addAttribute("ubicaciones", Ubicacion.values());

        return "registrar-usuario.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar/proveedor")
    public String registrarProveedor(ModelMap modelo) {

        modelo.put("rol", Rol.PROVEEDOR);

        modelo.addAttribute("ubicaciones", Ubicacion.values());

        return "registrar-proveedor.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/registro")
    public String registro(@RequestParam String accUsuario, @RequestParam Rol rol, @RequestParam Ubicacion ubicacion, @RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            String password2, ModelMap modelo, MultipartFile archivo) throws IOException {

        try {

            usuarioService.createUsuario(archivo, accUsuario, rol, nombre, email, ubicacion, password, password2);
            modelo.put("exito", "Usuario registrado correctamente!");

            return "index.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("accUsuario", accUsuario);
            modelo.put("email", email);
            modelo.put("ubicacion", ubicacion);

            if (rol.equals(Rol.PROVEEDOR)) {
                modelo.put("rol", Rol.PROVEEDOR);
                modelo.addAttribute("ubicaciones", Ubicacion.values());
                return "registrar-proveedor.html";

            } else if (rol.equals(Rol.CLIENTE)) {
                modelo.put("rol", Rol.CLIENTE);
                modelo.addAttribute("ubicaciones", Ubicacion.values());

                return "registrar-usuario.html";

            } else {
                return "registrar-usuario.html";
            }
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listadmusuarios")
    public String listarTodos(ModelMap modelo) {
        return "redirect:../usuario/listacompleta";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listadmproveedores")
    public String listarProfesionales(ModelMap modelo) {
        return "redirect:../usuario/proveedores";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listadmclientes")
    public String listarClientes(ModelMap modelo) {
        return "redirect:../usuario/clientes";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/rol/{id}")//viaja el id a travez de path variable para modificar, un fragmento de url donde se encuentra determinado recurso
    public String modificarRol(@PathVariable String id, ModelMap modelo) {//variable string id es variable de path y viaja en url del GetMapping
        modelo.put("usuario", usuarioService.getUsuarioById(id));//llave autor = lleva el valor del  autorServicio lo que nos trae el metodo getOne

        return "usuario_modificar.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/rol/{id}")
    public String modificarRolAdmin(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioService.updateUsuarioRolAdmin(id);

            return "redirect:../listacompleta";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

            return "usuario_modificar.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificacc/{id}")
    public String modificaracc(@PathVariable String id, ModelMap modelo) throws MiException {
        return "redirect:../usuario/restablecer/"+id;
    }

    //BOTON DAR DE ALTA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/usuariodaralta/{id}")
    public String alta(@PathVariable String id, ModelMap modelo) throws MiException {
        usuarioService.updateUsuarioAltaTrue(id);
        return "redirect:../listadmusuarios";
    }

    //BOTON DAR DE BAJA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/usuariodarbaja/{id}")
    public String baja(@PathVariable String id, ModelMap modelo) throws MiException {
        usuarioService.deleteUsuarioById(id);
        return "redirect:../listadmusuarios";
    }

    //CATEGORIAS------------------------------------->
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/categoria/listar")
    public String listarCategorias(ModelMap modelo) {
        return "redirect:/categoria/listar";

    }

    //BOTON DAR DE ALTA OFERTA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/categoria/alta/{id}")
    public String darAltaCategoria(@PathVariable String id, ModelMap modelo) throws MiException {
        categoriaService.updateCategoriaAltaTrue(id);
        return "redirect:/categoria/listar";
    }

    //BOTON DAR DE BAJA OFERTA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/categoria/baja/{id}")
    public String darBajaCategoria(@PathVariable String id, ModelMap modelo) throws MiException {
        categoriaService.deleteCategoriaById(id);
        return "redirect:/categoria/listar";
    }

    //SERVICIOS ------------------------------------->
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listarservicios")
    public String listarServiciosADM(ModelMap modelo) {
        List<Servicio> servicios = servicioService.getServiciosAll();
        modelo.addAttribute("servicios", servicios);
        return "listar-servicios-adm.html";
    }

    //BOTON DAR DE BAJA SERVICIO
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/servicio/baja/{id}")
    public String darBajaServicio(@PathVariable String id, ModelMap modelo) throws MiException {
        servicioService.deleteServicioById(id);
        return "redirect:/admin/listarservicios";
    }

    //BOTON DAR DE ALTA SERVICIO
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/servicio/alta/{id}")
    public String darAltaServicio(@PathVariable String id, ModelMap modelo) throws MiException {
        servicioService.updateServicioAltaTrue(id);
        return "redirect:/admin/listarservicios";
    }

    // CONTRATOS ------------------------------------->
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/contrato/listar")
    public String listarContratosAdm(ModelMap modelo) {
        List<Contrato> contratos = contratoService.getContratosAll();
        modelo.addAttribute("contratos", contratos);
        return "listar-contratos-adm.html";
    }

    //BOTON DAR DE BAJA OFERTA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/contrato/baja/{id}")
    public String darBajaContrato(@PathVariable String id, ModelMap modelo) throws MiException {
        contratoService.deleteContratoById(id);
        return "redirect:/admin/contrato/listar";
    }

    //BOTON DAR DE ALTA OFERTA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/contrato/alta/{id}")
    public String darAltaContrato(@PathVariable String id, ModelMap modelo) throws MiException {
        contratoService.updateContratoAlta(id,true);
        return "redirect:/admin/contrato/listar";
    }

    // CALIFICACIONES ------------------------------------->

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/calificacion/listar")
    public String listarCalificacionesAdm(ModelMap modelo) {
        List<Calificacion> calificaciones = calificacionService.getCalificaciones();
        modelo.put("calificaciones", calificaciones);
        return "listar-calificaciones-adm.html";
    }

    //BOTON DAR DE ALTA CALIFICACION
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/calificacion/alta/{id}")
    public String darAltaCalificacion(@PathVariable String id, ModelMap modelo) {
        try {
            calificacionService.updateCalificacionAltaTrue(id);
            return "redirect:/calificacion/listar";
        } catch (MiException ex) {
            return "redirect:/calificacion/listar";
        }
    }

    //BOTON DAR DE BAJA CALIFICACION
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/calificacion/baja/{id}")
    public String darBajaCalificacion(@PathVariable String id, ModelMap modelo) {
        try {
            calificacionService.deleteCalificacion(id);
            return "redirect:/calificacion/listar";
        } catch (MiException ex) {
            return "redirect:/calificacion/listar";
        }
    }


}