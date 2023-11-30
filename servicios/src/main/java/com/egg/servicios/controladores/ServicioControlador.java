package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Servicio;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import com.egg.servicios.servicios.ServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Daniel
 */
@Controller
@RequestMapping("/servicio")
public class ServicioControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;
    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/registrar") // localhost:8080/servicio/registrar
    public String registrar(ModelMap modelo) {

        cargarModeloConCategorias(modelo);

        return "test_servicio_create.html";
    }

    @PostMapping("/registro") // localhost:8080/libro/registro
    public String registro(@RequestParam String descripcion, @RequestParam Double honorariosHora,
                           @RequestParam MultipartFile matricula, @RequestParam String idCategoria,
                           @RequestParam String idProveedor, ModelMap modelo) {
        try {

            servicioServicio.crearServicio(descripcion, honorariosHora, matricula, idCategoria, idProveedor);

            cargarModeloConCategorias(modelo);

            modelo.put("exito", "El Servicio fue registrado correctamente!"); // carga el modelo con un mensaje exitoso

            return "test_servicio_create.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage()); // carga el modelo con un mensaje de error

            modelo.put("descripcion", descripcion);
            modelo.put("honorariosHora", honorariosHora);
            modelo.put("matricula", matricula);
            modelo.put("idCategoria", idCategoria);
            modelo.put("idProveedor", idProveedor);

            return "test_servicio_create.html"; // volvemos a cargar el formulario
        }

    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("servicios",servicios);

        return "test_servicio_read.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, HttpSession session) {

//        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
//        modelo.put("usuario", usuario);

        cargarModeloConCategorias(modelo);

        return "test_servicio_update.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable String id, @RequestParam String descripcion,
                            @RequestParam Double honorariosHora, @RequestParam MultipartFile matricula,
                            @RequestParam String idCategoria, @RequestParam String idProveedor, ModelMap modelo) {

        try {
            servicioServicio.actualizarServicio(id, descripcion, honorariosHora, matricula, idCategoria, idProveedor);

            cargarModeloConCategorias(modelo);

            modelo.put("exito","Servicio actualizado correctamente!");

            return "test_servicio_update.html";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("descripcion", descripcion);
            modelo.put("honorariosHora", honorariosHora);
            modelo.put("matricula", matricula);
            modelo.put("idCategoria", idCategoria);
            modelo.put("idProveedor", idProveedor);

            return "test_servicio_update.html";
        }
    }

    public void cargarModeloConCategorias(ModelMap modelo) {
        
//        List<Categoria> categorias = categoriaServicio.listarCategorias();

//        modelo.addAttribute("categorias",categorias);
    }

}