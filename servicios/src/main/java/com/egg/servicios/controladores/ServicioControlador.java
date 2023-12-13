package com.egg.servicios.controladores;

import com.egg.servicios.entidades.*;
import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.ContratoRepositorios;
import com.egg.servicios.repositorios.OfertaRepositorio;

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
import com.egg.servicios.repositorios.ContratoRepositorio;

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
    private ContratoServicio contratoServicio;
    @Autowired
    private ContratoRepositorio contratoRepositorios;
    @Autowired
    private CalificacionServicio calificacionServicio;
    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/registrar") // localhost:8080/servicio/registrar
    public String registrarServicio(ModelMap modelo, HttpSession session) {

        cargarModeloConCategorias(modelo);

        Usuario proveedor = (Usuario) session.getAttribute("usuarioSession");
        modelo.addAttribute("proveedor", proveedor);

        return "registrar-servicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR')")
    @PostMapping("/registro") // localhost:8080/servicio/registro
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


    @PreAuthorize("hasAnyRole('ROLE_CLIENTE')")
    @PostMapping("/contratar") // localhost:8080/servicio/contratar
    public String registroContrato(@RequestParam String descripcion, @RequestParam String idServicio,
            ModelMap modelo, HttpSession session) {


        try {
            Usuario cliente = (Usuario) session.getAttribute("usuarioSession");
            modelo.addAttribute("cliente", cliente);

            Oferta oferta = new Oferta();
            oferta.setDescripcion(descripcion);
            Servicio servicio = servicioServicio.listarPorId(idServicio);
            oferta.setServicio(servicio);
            oferta.setCliente(cliente);
            ofertaRepositorio.save(oferta);


            contratoServicio.crearContrato(oferta.getId());
            return "redirect:/servicio/listar/cliente";


        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/servicio/listar/cliente";
        }

    }

    @GetMapping("/listar")
    public String listarServicios(ModelMap modelo, HttpSession session) {

        try {
            Usuario proveedor = (Usuario) session.getAttribute("usuarioSession");
            // Se carga la lista de servicios en su totalidad
            List<Servicio> servicios = servicioServicio.listarServicios();
            // Se guardara la puntuacion de cada proveedor en orden por cada servicio mostrado
            List<Integer> puntuaciones = cargarListaPuntuacionesServicios(servicios);

            modelo.addAttribute("servicios",servicios);
            modelo.addAttribute("puntuaciones",puntuaciones);

            return "test_servicio_read.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_servicio_read.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE')")
    @GetMapping("/listar/cliente")
    public String listarServiciosCliente(ModelMap modelo, HttpSession session) {

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
            modelo.put("usuario", usuario);

            // Se carga la lista de servicios evitando mostrar servicios ya solicitados por el cliente
            List<Servicio> servicios = servicioServicio.listarServiciosPorCliente(usuario.getId());
            // Se guardara la puntuacion de cada proveedor en orden por cada servicio mostrado
            List<Integer> puntuaciones = cargarListaPuntuacionesServicios(servicios);

            modelo.addAttribute("servicios",servicios);
            modelo.addAttribute("puntuaciones",puntuaciones);

            return "test_servicio_read_cliente.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_servicio_read_cliente.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR')")
    @GetMapping("/listar/proveedor")
    public String listarServiciosProveedor(ModelMap modelo, HttpSession session) {

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
            modelo.put("usuario", usuario);

            // Se carga la lista de servicios evitando mostrar servicios ya solicitados por el cliente
            List<Servicio> servicios = servicioServicio.listarServiciosPorCliente(usuario.getId());
            // Se guardara la puntuacion de cada proveedor en orden por cada servicio mostrado
            List<Integer> puntuaciones = cargarListaPuntuacionesServicios(servicios);

            modelo.addAttribute("servicios",servicios);
            modelo.addAttribute("puntuaciones",puntuaciones);

            return "test_servicio_read_proveedor.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_servicio_read_proveedor.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificarServicio(@PathVariable String id, ModelMap modelo, HttpSession session) {

        Servicio servicio = servicioServicio.listarPorId(id);
        modelo.put("servicio", servicio);

        cargarModeloConCategorias(modelo);

        return "test_servicio_update.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR')")
    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable String id, @RequestParam String descripcion,
            @RequestParam Double honorariosHora, MultipartFile matricula,
            @RequestParam String idCategoria, @RequestParam String idProveedor, ModelMap modelo) {

        try {
            servicioServicio.actualizarServicio(id, descripcion, honorariosHora, matricula, idCategoria, idProveedor);

            cargarModeloConCategorias(modelo);

            modelo.put("exito", "Servicio actualizado correctamente!");

            return "test_servicio_read.html";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("id", id);
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
            modelo.addAttribute("categorias", categorias);

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
        }
    }

    public List<Integer> cargarListaPuntuacionesServicios(List<Servicio> servicios) throws MiException {

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
                    cantCalificaciones++;
                    // Se acumula la cantidad de estrellas recibidas
                    cantEstrellas += contrato.getAptitud().getPuntuacion();
                }
            }

            // Se realiza un promedio de puntuacion
            int promedioProveedor = cantCalificaciones != 0 ? cantEstrellas / cantCalificaciones : 0;
            // Se añade el promedio del proveedor
            puntuaciones.add(promedioProveedor);
        }

        return puntuaciones;
    }
    
    @GetMapping("/categorias")
    public String seleccionarServicio(ModelMap modelo, HttpSession session,String nameCate){       
        try {     
             modelo.addAttribute("categoria",  categoriaServicio.listarCategorias());      
            return "index.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "test_servicio_read_proveedor.html";
        }
        
        
            
    }
            
            
            

}
