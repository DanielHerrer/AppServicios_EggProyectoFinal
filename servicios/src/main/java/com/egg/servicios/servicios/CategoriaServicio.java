package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.CategoriaRepositorio;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// PARA HACER: añadir metodos { listarCategorias(), listarPorId(id), eliminarCategoria(id) }

@Service
public class CategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Transactional
    public void crearCategoria(String nombre) throws MiException{

        validar(nombre);

        try {
            
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);

            categoriaRepositorio.save(categoria);
        
        } catch (Exception e) {
            
            throw new MiException(e.getMessage());
        }
    }

    public void modificarCategoria(String idCategoria, String nombre) throws MiException {

        validar(nombre);
        
        try {

            Optional<Categoria>resultado = categoriaRepositorio.findById(idCategoria);

            if (resultado.isPresent()) {

                Categoria categoria = resultado.get();
                categoria.setNombre(nombre);
                categoriaRepositorio.save(categoria);
            }
                
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
        
    }

    public List<Categoria> listarCategorias() throws MiException {

        try {
            return categoriaRepositorio.listarCategoriasAlta();

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    private void validar(String nombre) throws MiException {

        if (nombre.trim().isEmpty() || nombre == null) {
            throw new MiException("El Nombre no puede ser nulo ni estar vacío.");
        } else if (categoriaRepositorio.findByNombre(nombre).isPresent()) {
            throw new MiException("Ya existe una Categoria con el mismo nombre.");
        }
    }

}