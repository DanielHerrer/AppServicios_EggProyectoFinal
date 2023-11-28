package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/registrarCategoria")
    public String registrarCategoria() {

        return "test_categoria_form.html";
    }

    @PostMapping("/registroCategoria")
    public String registroCategoria(@RequestParam(required = false) String nombre, ModelMap modelo) {

        try {

            categoriaServicio.crearCategoria(nombre);

            return "inicio.html";

        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            return "inicio.html";

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
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        modelo.put("categoria", categoriaServicio.getOne(id));

        return "test_categoria_modificar.html";
    }

    @DeleteMapping("/eliminado/{id}")
    public String eliminado(@PathVariable String id, ModelMap modelo) {

        modelo.remove(id);

        return "inicio.html";
    }

}
