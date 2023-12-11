package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.enumeraciones.Rol;
import com.egg.servicios.enumeraciones.Ubicacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.UsuarioServicio;
import java.io.IOException;
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
@RequestMapping("/") // localhost:8080/
public class PortalControlador {

    private static final String CARPETA_IMAGENES = "src/main/resources/static/img/";
    private static final String IMAGEN_POR_DEFECTO = "default.jpg";

    @Autowired
    UsuarioServicio usuarioServicio;

    //primer metodo que se va a ejecutar en el localhost
    @GetMapping("/")//mapea url cuando se ingresa la / asi se ejecuta el cuerpo del metodo
    public String index(ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        if (logueado != null) {
            return "redirect:/inicio";
        }
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");

        if (logueado != null) {
            return "redirect:/inicio";
        }
        return "eleccion-usuario.html";

    }

    @GetMapping("/registrar/cliente")
    public String registrarCliente(ModelMap modelo, HttpSession session) {

        modelo.put("rol", Rol.CLIENTE);

        modelo.addAttribute("ubicaciones", Ubicacion.values());

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");

        if (logueado != null) {
            return "redirect:/inicio";
        }

        return "registrar-usuario.html";
    }

    @GetMapping("/registrar/proveedor")
    public String registrarProveedor(ModelMap modelo, HttpSession session) {

        modelo.put("rol", Rol.PROVEEDOR);

        modelo.addAttribute("ubicaciones", Ubicacion.values());

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");

        if (logueado != null) {
            return "redirect:/inicio";
        }

        return "registrar-proveedor.html";

    }

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

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }
        if (logueado != null) {
            return "redirect:/inicio";
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROVEEDOR','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");

        System.out.println("Usuario en sesión: " + logueado);
        modelo.addAttribute("logueado", logueado);

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROVEEDOR','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
         modelo.addAttribute("ubicaciones", Ubicacion.values());
        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        modelo.put("usuario", usuario);
        return "usuario-modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROVEEDOR','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @RequestParam Rol rol, @RequestParam Ubicacion ubicacion, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String accUsuario, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            usuarioServicio.actualizar(archivo, id, rol, ubicacion, nombre, accUsuario, email, password, password2);

            modelo.put("exito", "Usuario actualizado correctamente!");

            return "inicio.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario-modificar.html";
        }

    }
}
