package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.entidades.Servicio;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.ContratoRepositorios;
import com.egg.servicios.servicios.CategoriaServicio;
import com.egg.servicios.servicios.ContratoServicios;
import com.egg.servicios.servicios.OfertaServicio;
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
    @Autowired
    private OfertaServicio ofertaServicio;
    @Autowired
    private ContratoServicios contratoServicio;
    @Autowired
    private ContratoRepositorios contratoRepositorios;

    @GetMapping("/proveedor/registrar") // localhost:8080/servicio/proveedor/registrar
    public String registrarServicio(ModelMap modelo) {

        cargarModeloConCategorias(modelo);

        return "test_servicio_create.html";
    }

    @PostMapping("/proveedor/registro") // localhost:8080/servicio/proveedor/registro
    public String registroServicio(@RequestParam String descripcion, @RequestParam Double honorariosHora,
                           MultipartFile matricula, @RequestParam String idCategoria,
                           @RequestParam String idProveedor, ModelMap modelo) {
        try {

            servicioServicio.crearServicio(descripcion, honorariosHora, matricula, idCategoria, idProveedor);

            cargarModeloConCategorias(modelo);

            modelo.put("exito", "El Servicio fue registrado correctamente!"); // carga el modelo con un mensaje exitoso

            return "test_servicio_create.html";

        } catch (MiException ex) {

            cargarModeloConCategorias(modelo);

            modelo.put("error", ex.getMessage()); // carga el modelo con un mensaje de error

            modelo.put("descripcion", descripcion);
            modelo.put("honorariosHora", honorariosHora);
            modelo.put("matricula", matricula);
            modelo.put("idCategoria", idCategoria);
            modelo.put("idProveedor", idProveedor);

            return "test_servicio_create.html"; // volvemos a cargar el formulario
        }

    }

    @PostMapping("/cliente/contratar") // localhost:8080/servicio/cliente/contratar
    public String registroContrato(@RequestParam String descripcion, @RequestParam String idServicio,
                                  @RequestParam String idCliente, ModelMap modelo) {

        try {

            Oferta oferta = ofertaServicio.crearOferta(descripcion, idServicio, idCliente);
            contratoServicio.crearContrato(oferta);
            return "test_servicio_read.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "test_servicio_read.html";
        }

    }

    @GetMapping("/listar")
    public String listarServiciosInvitado(ModelMap modelo) {

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("servicios",servicios);

        return "test_servicio_read.html";
    }

//    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/proveedor/listar")
    public String listadoServiciosProveedor(ModelMap modelo, HttpSession session) {

        Usuario proveedor = (Usuario) session.getAttribute("usuariosession");

        List<Servicio> servicios = servicioServicio.listarServiciosProveedor(proveedor.getId());

        modelo.addAttribute("servicios",servicios);

        return "test_servicio_read.html";
    }

//    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/proveedor/modificar/{id}")
    public String modificarServicio(@PathVariable String id, ModelMap modelo, HttpSession session) {

//        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
//        modelo.put("usuario", usuario);

        modelo.put("id", id);

        cargarModeloConCategorias(modelo);

        return "test_servicio_update.html";
    }

//    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @PostMapping("/proveedor/modificado/{id}")
    public String modificado(@PathVariable String id, @RequestParam String descripcion,
                            @RequestParam Double honorariosHora, MultipartFile matricula,
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

        try {
            List<Categoria> categorias = categoriaServicio.listarCategorias();
            modelo.addAttribute("categorias",categorias);

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
        }
    }

}