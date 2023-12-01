package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.CategoriaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void modificarCategoria(String idCategoria, String nombre) throws MiException {
        validar(nombre);
        try {
            Optional<Categoria> resultado = categoriaRepositorio.findById(idCategoria);
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
    
        public Optional<Categoria> listarCategoriasPorNombre(String nombre, Pageable pageable) throws MiException {
        try {
            return categoriaRepositorio.findByNombre(nombre, pageable);
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
<<<<<<< HEAD
            throw new MiException("El Nombre no puede estar vacío.");
        } else if (listarCategoriaPorNombre(nombre) != null) {
=======
            throw new MiException("El Nombre no puede ser nulo ni estar vacío.");
        } else if (existsByNombre(nombre)) {
>>>>>>> cde8afc8418c6e042cfa6912d8026baca0ac11a8
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