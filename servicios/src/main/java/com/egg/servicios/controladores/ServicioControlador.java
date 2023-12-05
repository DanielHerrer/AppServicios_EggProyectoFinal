package com.egg.servicios.controladores;

import com.egg.servicios.entidades.*;
import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.ContratoRepositorios;
import com.egg.servicios.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    @Autowired
    private CalificacionServicio calificacionServicio;

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/proveedor/registrar") // localhost:8080/servicio/proveedor/registrar
    public String registrarServicio(ModelMap modelo, HttpSession session) {

        cargarModeloConCategorias(modelo);

        Usuario proveedor = (Usuario) session.getAttribute("usuarioSession");
        modelo.addAttribute("proveedor", proveedor);

        return "registrar-servicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @PostMapping("/proveedor/registro") // localhost:8080/servicio/proveedor/registro
    public String registroServicio(@RequestParam String descripcion, @RequestParam Double honorariosHora,
                           MultipartFile matricula, @RequestParam String idCategoria,
                           @RequestParam String idProveedor, ModelMap modelo, HttpSession session) {
        try {

            servicioServicio.crearServicio(descripcion, honorariosHora, matricula, idCategoria, idProveedor);

            cargarModeloConCategorias(modelo);

            Usuario proveedor = (Usuario) session.getAttribute("usuarioSession");
            modelo.addAttribute("proveedor", proveedor);

            modelo.put("exito", "El Servicio fue registrado correctamente!"); // carga el modelo con un mensaje exitoso

            return "registrar-servicio.html";

        } catch (MiException ex) {

            cargarModeloConCategorias(modelo);

            Usuario proveedor = (Usuario) session.getAttribute("usuarioSession");
            modelo.addAttribute("proveedor", proveedor);

            modelo.put("error", ex.getMessage()); // carga el modelo con un mensaje de error

            modelo.put("descripcion", descripcion);
            modelo.put("honorariosHora", honorariosHora);
            modelo.put("matricula", matricula);
            modelo.put("idCategoria", idCategoria);
            modelo.put("idProveedor", idProveedor);

            return "registrar-servicio.html"; // volvemos a cargar el formulario
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN')")
    @PostMapping("/cliente/contratar") // localhost:8080/servicio/cliente/contratar
    public String registroContrato(@RequestParam String descripcion, @RequestParam String idServicio,
                                  ModelMap modelo, HttpSession session) {

        try {
            Usuario cliente = (Usuario) session.getAttribute("usuarioSession");
            modelo.addAttribute("cliente", cliente);

            Oferta oferta = ofertaServicio.crearOferta(descripcion, idServicio, cliente.getId());

            contratoServicio.crearContrato(oferta);
            return "test_servicio_read.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "test_servicio_read.html";
        }

    }

    @GetMapping("/listar")
    public String listarServicios(ModelMap modelo) {

        try {
            List<Servicio> servicios = servicioServicio.listarServicios();
            // Se guardara la puntuacion de cada proveedor en orden por cada servicio mostrado
            List<Integer> puntuaciones = new ArrayList<>();

            // Recorre cada servicio mostrado
            for (Servicio servicio : servicios) {

                // Del servicio actual, busca el id del proveedor, y retorna los contratos de ese proveedor
                List<Contrato> contratos = contratoServicio.listarContratosPorProveedor(servicio.getProveedor().getId());
                int cantEstrellas = 0;
                int cantCalificaciones = 0;

                // Recorre cada contrato del proveedor actual
                for (Contrato contrato : contratos) {

                    // Si el contrato fue finalizado y su puntuacion ya esta publicada
                    if (contrato.getEstadoTrabajo().equals(Estados.FINALIZADO) && contrato.getAptitud() != null) {
                        // 1 puntuacion mas
                        cantCalificaciones ++;
                        // Se acumula la cantidad de estrellas recibidas
                        cantEstrellas += contrato.getAptitud().getPuntuacion();
                    }
                }

                // Se realiza un promedio de puntuacion
                int promedioProveedor = cantCalificaciones != 0 ? cantEstrellas / cantCalificaciones : 0;
                // Se añade el promedio del proveedor
                puntuaciones.add(promedioProveedor);
            }

            modelo.addAttribute("servicios",servicios);
            modelo.addAttribute("puntuaciones",puntuaciones);

            return "test_servicio_read.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "listar-servicios.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/proveedor/listar")
    public String listarServiciosProveedor(ModelMap modelo, HttpSession session) {

        Usuario proveedor = (Usuario) session.getAttribute("usuarioSession");

        List<Servicio> servicios = servicioServicio.listarServiciosPorProveedor(proveedor.getId());

        modelo.addAttribute("servicios",servicios);

        return "listar-servicios.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/proveedor/modificar/{id}")
    public String modificarServicio(@PathVariable String id, ModelMap modelo, HttpSession session) {

//        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
//        modelo.put("usuario", usuario);

        modelo.put("id", id);

        cargarModeloConCategorias(modelo);

        return "test_servicio_update.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
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