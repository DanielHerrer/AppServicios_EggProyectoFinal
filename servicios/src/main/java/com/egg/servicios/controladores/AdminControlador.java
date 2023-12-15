/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Servicio;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.enumeraciones.Rol;
import com.egg.servicios.enumeraciones.Ubicacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import com.egg.servicios.servicios.ServicioServicio;
import com.egg.servicios.servicios.UsuarioServicio;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
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
    CategoriaServicio categoriaServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ServicioServicio servicioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    //CATEGORIAS------------------------------------->
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listadmcategorias") // localhost:8080/categoria/listar
    public String listarCategorias(ModelMap modelo) {
        return "redirect:../categoria/listar";

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

            usuarioServicio.registrar(archivo, accUsuario, rol, nombre, email, ubicacion, password, password2);
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
        modelo.put("usuario", usuarioServicio.getOne(id));//llave autor = lleva el valor del  autorServicio lo que nos trae el metodo getOne

        return "usuario_modificar.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/rol/{id}")
    public String modificarRolAdmin(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicio.modificarRolAdmin(id);

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

    //BOTON DAR DE BAJA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/usuariodarbaja/{id}")
    public String baja(@PathVariable String id, ModelMap modelo) throws MiException {
        usuarioServicio.baja(id);
        return "redirect:../listadmusuarios";
    }

    //BOTON DAR DE ALTA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/usuariodaralta/{id}")
    public String alta(@PathVariable String id, ModelMap modelo) throws MiException {
        usuarioServicio.alta(id);
        return "redirect:../listadmusuarios";
    }

    //SERVICIOS ------------------------------------->
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listarservicios")
    public String listarServiciosADM(ModelMap modelo) {
        List<Servicio> servicios = servicioServicio.listarServiciosTodos();
        modelo.addAttribute("servicios", servicios);
        return "listar-servicios-adm.html";
    }

    //BOTON DAR DE BAJA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/baja/{id}")
    public String darBaja(@PathVariable String id, ModelMap modelo) throws MiException {
        servicioServicio.darBaja(id);
        return "redirect:../listarservicios";
    }

    //BOTON DAR DE ALTA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/alta/{id}")
    public String darAlta(@PathVariable String id, ModelMap modelo) throws MiException {
        servicioServicio.darAlta(id);
        return "redirect:../listarservicios";
    }

}
