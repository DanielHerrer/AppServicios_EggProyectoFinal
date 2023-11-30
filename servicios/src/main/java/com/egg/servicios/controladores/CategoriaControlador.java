package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Categoria;

import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/registrar") // localhost:8080/categoria/registrar
    public String registrarCategoria() {

        return "test_categoria_form.html";
    }

    @PostMapping("/registro") // localhost:8080/categoria/registro
    public String registroCategoria(@RequestParam String nombre, ModelMap modelo) {

        try {
            modelo.put("exito","La Categoria se ha creado correctamente!");
            categoriaServicio.crearCategoria(nombre);

            return "test_categoria_form.html";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());

            modelo.put("nombre",nombre);

            return "test_categoria_form.html";
        }
    }

    @GetMapping("/listar")
    public String listarCategorias(ModelMap modelo) {

        try {
            List<Categoria> categorias = categoriaServicio.listarCategorias();
            modelo.addAttribute("categorias",categorias);

            return "test_categoria_read.html";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "test_categoria_read.html";
        }
    }

    // modificar la categoria
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Categoria> categoria = categoriaServicio.listarCategoria();
        modelo.addAttribute("categoria", categoria);
        return "test_categoria_read.html";
    }

    @GetMapping("/modificar/{id}") // revisar
    public String modificar(@PathVariable String id, ModelMap modelo) {
             
            modelo.put("categoria", categoriaServicio.getOne(id));
            return "test_categoria_modificar.html";
   
    }

    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable String id, String nombre, ModelMap modelo) {

        try {

            categoriaServicio.modificarCategoria(id, nombre);
            return "test_categoria_modificar.html";

        } catch (MiException e) {

            modelo.put("error", e.getMessage());
            return "test_categoria_modificar.html";

        }

    }

    // eliminar categorias
    @PostMapping("/eliminado/{id}")
    public String eliminado(@PathVariable String id, ModelMap modelo) {

       try {

            categoriaServicio.eliminarCategoria(id);
            return "test_index.html";

        } catch (MiException e) {

            modelo.put("error", e.getMessage());
            return "test_index.html";
            
        }
    }

}
