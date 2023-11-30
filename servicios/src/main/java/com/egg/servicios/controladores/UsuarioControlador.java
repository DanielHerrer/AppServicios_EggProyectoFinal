/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nico
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/activos")
    public String listarActivos(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuariosActivos();
        modelo.addAttribute("usuarios", usuarios);

        return "usuarios_list.html";
    }

    @GetMapping("/inactivos")
    public String listarInactivos(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuariosInactivos();
        modelo.addAttribute("usuarios", usuarios);

        return "usuarios_list.html";
    }

    @GetMapping("/clientes")
    public String listarClientes(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuarios_list.html";
    }

    @GetMapping("/proveedores")
    public String listarProveedores(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarProveedores();
        modelo.addAttribute("usuarios", usuarios);

        return "usuarios_list.html";
    }

    @GetMapping("/administradores")
    public String listarAdmin(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarAdmin();
        modelo.addAttribute("usuarios", usuarios);

        return "usuarios_list.html";
    }

    @GetMapping("/listacompleta")
    public String listarTodos(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuarios_list.html";
    }

    @GetMapping("/modificar/{id}")//viaja el id a travez de path variable para modificar, un fragmento de url donde se encuentra determinado recurso
    public String modificar(@PathVariable String id, ModelMap modelo) {//variable string id es variable de path y viaja en url del GetMapping
        modelo.put("usuario", usuarioServicio.getOne(id));//llave autor = lleva el valor del  autorServicio lo que nos trae el metodo getOne

        return "usuario_modificar.html";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, Boolean alta, ModelMap modelo) {
        try {
            usuarioServicio.modificar(id, alta);

            return "redirect:../lista";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

            return "usuario_modificar.html";
        }

    }

}
