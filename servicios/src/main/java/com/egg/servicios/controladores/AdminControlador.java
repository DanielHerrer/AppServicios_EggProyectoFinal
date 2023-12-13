/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.controladores;

import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import com.egg.servicios.servicios.ServicioServicio;
import com.egg.servicios.servicios.UsuarioServicio;
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
    @GetMapping("/listar/categorias") // localhost:8080/categoria/listar
    public String listarCategorias(ModelMap modelo) {
        return "redirect:../categoria/listar";

    }

    //USUARIOS -------------------------------------->
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar/usuarios")
    public String listarTodos(ModelMap modelo) {
        return "redirect:../usuario/listacompleta";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar/usuario/proveedores")
    public String listarProfesionales(ModelMap modelo) {
        return "redirect:../usuario/listacompleta";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar/usuario/clientes")
    public String listarClientes(ModelMap modelo) {
        return "redirect:../usuario/listacompleta";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")//viaja el id a travez de path variable para modificar, un fragmento de url donde se encuentra determinado recurso
    public String modificarRol(@PathVariable String id, ModelMap modelo) {//variable string id es variable de path y viaja en url del GetMapping
        modelo.put("usuario", usuarioServicio.getOne(id));//llave autor = lleva el valor del  autorServicio lo que nos trae el metodo getOne

        return "usuario_modificar.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificarRolAdmin(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicio.modificarRolAdmin(id);

            return "redirect:../listacompleta";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

            return "usuario_modificar.html";
        }

    }

    //SERVICIOS ------------------------------------->
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar/servicios")
    public String listarServicios(ModelMap modelo) {
        return "redirect:../servicios/listarservicios";
    }
    
    
    //BOTON DAR DE BAJA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/darbaja/{id}")
    public String darBaja(@PathVariable String id,ModelMap modelo) throws MiException {
        servicioServicio.darBaja(id);
        return "redirect:../listar/servicios";
    }
    
    

}
