package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.entidades.Servicio;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar") // localhost:8080/categoria/registrar
    public String registrarCategoria() {

        return "registrar-categoria.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/registro") // localhost:8080/categoria/registro
    public String registroCategoria(@RequestParam String nombre, ModelMap modelo) {

        try {
            modelo.put("exito","La Categoria se ha creado correctamente!");
            categoriaServicio.crearCategoria(nombre);

            return "registrar-categoria.html";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre",nombre);

            return "registrar-categoria.html";
        }
    }

    @GetMapping("/listar") // localhost:8080/categoria/listar
    public String listarCategorias(ModelMap modelo) {

        try {
            List<Categoria> categorias = categoriaServicio.listarCategorias();
            modelo.addAttribute("categorias",categorias);

            return "listar-categorias.html";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "listar-categorias.html";
        }
    }

}
