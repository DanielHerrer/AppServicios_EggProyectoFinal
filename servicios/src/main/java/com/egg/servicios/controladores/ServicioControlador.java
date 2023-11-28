package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import com.egg.servicios.servicios.ServicioServicio;
import com.egg.servicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Daniel
 */
@Controller
@RequestMapping("/servicio")
public class ServicioControlador {

    @Autowired
    private ServicioServicio servicioServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping("/servicio")
    public String servicio(ModelMap modelo) {

        return "servicio_form.html";
    }

    @PostMapping("/registroServicio")
    private String registro(MultipartFile archivo, @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Double honariosHora, @RequestParam(required = false) String idCategoria,
            @RequestParam(required = false) String idProovedor, ModelMap modelo) {

        try {
            
            servicioServicio.crearServicio(archivo, descripcion, honariosHora, idCategoria, idProovedor);         
            return "inicio.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "inicio.html";

        }

    }

}
