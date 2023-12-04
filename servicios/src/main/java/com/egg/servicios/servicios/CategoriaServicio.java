package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.CategoriaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author martin
 */
// PARA HACER: añadir metodos { listarCategorias(), listarPorId(id), eliminarCategoria(id) }
@Service
public class CategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Transactional
    public void crearCategoria(String nombre) throws MiException {
        validar(nombre);
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoriaRepositorio.save(categoria);
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    @Transactional
    public void modificarCategoria(String id, String nombre) throws MiException {
        validar(nombre);
        try {
            Optional<Categoria> resultado = categoriaRepositorio.findById(id);
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
    
        public List<Categoria> listarCategoriasPorNombre(String nombre) throws MiException {
        try {
            return categoriaRepositorio.findByNombre(nombre);
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }
    
    public Categoria getOne(String id) {
        return (Categoria) categoriaRepositorio.getOne(id);
    }

    public boolean existsByNombre(String nombre) throws MiException {
        try {
            List<Categoria> categorias = categoriaRepositorio.findByNombre(nombre);
            if (categorias.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    private void validar(String nombre) throws MiException {

        if (nombre.trim().isEmpty() || nombre == null) {
            throw new MiException("El Nombre no puede ser nulo ni estar vacío.");
        } else if (existsByNombre(nombre)) {
            throw new MiException("Ya existe una Categoria con el mismo nombre.");
        }
    }

    @Transactional
    public void eliminarCategoria(String id) throws MiException {
        Optional<Categoria> respuesta = categoriaRepositorio.findById(id);
        try {
            if (respuesta.isPresent()) {
                Categoria categoria = respuesta.get();
                categoria.setAlta(false);
                categoriaRepositorio.save(categoria);
            } else {
                throw new MiException("El ID Categoría no corresponde a ninguna categoría existente.");
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }
}