/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.enumeraciones.Rol;
import com.egg.servicios.enumeraciones.Ubicacion;
import com.egg.servicios.servicios.UsuarioServicio;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nico
 */
@Controller
@RequestMapping("/usuario")
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
    

       @GetMapping("/restablecer/proveedor")
    public String modificarProveedor(HttpSession session, ModelMap modelo) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
            modelo.addAttribute("ubicaciones", Ubicacion.values());
              modelo.put("rol", Rol.PROVEEDOR);
              modelo.put("usuario", usuario);
            if (usuario != null) {
             return "test_modificar_pass.html";    
            }
          return "index.html";
           
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_modificar_pass.html";
        }
    }
    
      @GetMapping("/restablecer/cliente")
    public String modificarCliente(HttpSession session, ModelMap modelo) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
            modelo.addAttribute("ubicaciones", Ubicacion.values());
              modelo.put("rol", Rol.CLIENTE);
              modelo.put("usuario", usuario);
            if (usuario != null) {
             return "test_modificar_pass.html";    
            }
          return "index.html";
           
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_modificar_pass.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @PostMapping("/restablecer/{id}")
    public String modificarUsuario1(@PathVariable String id, MultipartFile archivo,String nombre,String email, String password,String password2,String accUsuario,Ubicacion ubicacion,HttpSession session, Rol rol,ModelMap modelo) {
        try {
        
            Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
            modelo.put("usuario", usuario);
              usuarioServicio.configurarUsuario(archivo, nombre, email,id, password, password2, accUsuario, ubicacion);              
                  if (usuario != null) {
             return "redirect:/logout";    
                  }
               return "index.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("accUsuario", accUsuario);
            modelo.put("email", email);
            modelo.put("ubicacion", ubicacion);
            
             if (rol.equals(Rol.PROVEEDOR)) {
                modelo.put("rol", Rol.PROVEEDOR);
                modelo.addAttribute("ubicaciones", Ubicacion.values());
                return "test_modificar_pass.html";
            } 
            if (rol.equals(Rol.CLIENTE)) {
                modelo.put("rol", Rol.CLIENTE);
                modelo.addAttribute("ubicaciones", Ubicacion.values());
                return "test_modificar_pass.html";    
            }
           return "test_modificar_pass.html";    
        }

    }
}
