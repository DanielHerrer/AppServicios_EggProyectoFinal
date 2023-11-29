package com.egg.servicios.controladores;

import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.servicios.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categoria")
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping("/registroCategoria")
    public String registroCategoria() {

        return "categoria_form.html";
    }

    @PostMapping("/registrarCategoria")
    public String registrarCategoria(@RequestParam(required = false) String nombre, ModelMap modelo) {

        try {

            categoriaServicio.crearCategoria(nombre);

            return "inicio.html";

        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            return "inicio.html";

        }

    }

}
