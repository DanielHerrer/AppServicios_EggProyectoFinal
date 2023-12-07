package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author martin
 */
@Controller
@RequestMapping("/categoria")
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar") // localhost:8080/categoria/registrar
    public String registrarCategoria() {
        return "test_categoria_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/registro") // localhost:8080/categoria/registro
    public String registroCategoria(@RequestParam String nombre, ModelMap modelo) {
        try {
            categoriaServicio.crearCategoria(nombre);
            modelo.put("exito", "La Categoria se ha creado correctamente!");
            return "test_categoria_form.html";
        } catch (MiException e) {
            modelo.addAttribute("nombre", nombre);
            modelo.put("error", e.getMessage());
            return "test_categoria_form.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar") // localhost:8080/categoria/listar
    public String listarCategorias(ModelMap modelo) {
        try {
            List<Categoria> categorias = categoriaServicio.listarCategorias();
            modelo.addAttribute("categorias", categorias);
            return "test_categoria_lista.html";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "test_categoria_lista.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("categoria", categoriaServicio.getOne(id));
        return "test_categoria_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable String id, @RequestParam String nombre, ModelMap modelo) {
        try {
            categoriaServicio.modificarCategoria(id, nombre);
            modelo.put("exito", "La categoría se ha modificado con éxito!.");
            return "redirect:../listar";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "test_categoria_modificar.html";
        }
    }

    public void cargarModeloCategorias(ModelMap modelo) {
        try {
            List<Categoria> categorias = categoriaServicio.listarCategorias();
            modelo.addAttribute("categorias", categorias);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminado/{id}")
    public String eliminado(@PathVariable String id, ModelMap modelo) {
        try {
            categoriaServicio.eliminarCategoria(id);
            return "redirect:../listar";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:../listar";
        }
    }

    @GetMapping("/estado")
    public String listarCategoriasEstado(ModelMap modelo) {
        try {
            List<Categoria> categorias = categoriaServicio.listarCategoriasBaja();
            modelo.addAttribute("categorias", categorias);
            return "test_categoria_eliminados.html";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "test_categoria_eliminados.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/estado/{id}")
    public String estado(@PathVariable String id, ModelMap modelo
    ) {
        try {
            categoriaServicio.estadoCategoria(id);
            return "redirect:../listar";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:../listar";
        }
    }

}
