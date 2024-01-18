package com.egg.servicios.controllers;

import com.egg.servicios.entities.Usuario;
import com.egg.servicios.enums.Rol;
import com.egg.servicios.enums.Ubicacion;
import com.egg.servicios.exceptions.MiException;
import com.egg.servicios.services.UsuarioService;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nico
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioService usuarioService;

    // Primer metodo que se va a ejecutar en el localhost
    @Transactional
    @GetMapping("/")// Mapea url cuando se ingresa la / asi se ejecuta el cuerpo del metodo
    public String index(ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");

        if (logueado != null) {
            if (logueado.getRol().equals(Rol.ADMIN)) {
                return "redirect:/admin/dashboard";
//                return "panel.html";
            } else if (logueado.getRol().equals(Rol.PROVEEDOR) || logueado.getRol().equals(Rol.CLIENTE)) {
                return "redirect:/inicio";
//                modelo.put("notificaciones", usuarioService.countNotificaciones(logueado.getId()));
//                modelo.put("ubicaciones",Ubicacion.values());
//                return "inicio.html";
            }
        }
        return "index.html";
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        modelo.put("notificaciones", usuarioService.countNotificaciones(logueado.getId()));
        modelo.put("ubicaciones",Ubicacion.values());

        System.out.println("Usuario en sesión: " + logueado);
        return "inicio.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");

        if (logueado != null) {
            return "redirect:/";
        }
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña inválidos!");
        }
        if (logout != null) {
            modelo.put("exito", "Cierre de sesión exitoso!");
        }
        return "login.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        if (logueado != null) {
            return "redirect:/";
        }
        return "eleccion-usuario.html";

    }

    @GetMapping("/registrar/cliente")
    public String registrarCliente(ModelMap modelo, HttpSession session) {

        modelo.put("rol", Rol.CLIENTE);

        modelo.addAttribute("ubicaciones", Ubicacion.values());

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        if (logueado != null) {
            return "redirect:/";
        }

        return "registrar-usuario.html";
    }

    @GetMapping("/registrar/proveedor")
    public String registrarProveedor(ModelMap modelo, HttpSession session) {

        modelo.put("rol", Rol.PROVEEDOR);

        modelo.addAttribute("ubicaciones", Ubicacion.values());

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        if (logueado != null) {
            return "redirect:/";
        }

        return "registrar-proveedor.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String accUsuario, @RequestParam Rol rol, @RequestParam Ubicacion ubicacion, @RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            String password2, ModelMap modelo, MultipartFile archivo) throws MiException {

        try {

            usuarioService.createUsuario(archivo, accUsuario, rol, nombre, email, ubicacion, password, password2);
            modelo.put("exito", "Usuario registrado correctamente!");

            return "redirect:/";

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("accUsuario", accUsuario);
            modelo.put("email", email);
            modelo.put("ubicacionSelect", ubicacion);

            if (rol.equals(Rol.PROVEEDOR)) {
                modelo.put("rol", Rol.PROVEEDOR);
                modelo.addAttribute("ubicaciones", Ubicacion.values());
                return "registrar-proveedor.html";

            } else if (rol.equals(Rol.CLIENTE)) {
                modelo.put("rol", Rol.CLIENTE);
                modelo.addAttribute("ubicaciones", Ubicacion.values());

                return "registrar-usuario.html";

            } else {
                return "eleccion-usuario.html";
            }
        }
    }

}