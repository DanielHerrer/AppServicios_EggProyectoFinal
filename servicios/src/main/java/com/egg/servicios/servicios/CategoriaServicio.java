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

        validar(nombre, nombre);

        try {

            Categoria categoria = new Categoria();

            categoria.setNombre(nombre);

            categoriaRepositorio.save(categoria);

        } catch (Exception e) {

            throw new MiException(e.getMessage());

        }

    }

    public void modificarCategoria(String ID_categoria, String nombre) throws MiException {

        validar(nombre, nombre);

        try {

            Optional<Categoria> resultado = categoriaRepositorio.findById(ID_categoria);

            if (resultado.isPresent()) {

                Categoria categoria = resultado.get();
                categoria.setNombre(nombre);
                categoriaRepositorio.save(categoria);

            }

        } catch (Exception e) {

            throw new MiException(e.getMessage());

        }

    }

    private void validar(String id, String nombre) throws MiException {

        if (id == null) {
            throw new MiException("El ID no puede estar vacío.");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El NOMBRE no puede ser nulo ni estar vacío");
        }

    }

    public Categoria getOne(String id) {
        
        return categoriaRepositorio.getOne(id);
    }

    public List<Categoria> listarCategoria() {

        return categoriaRepositorio.listarCategoriasAlta();
    }
    
     @Transactional
    public void eliminarCategoria(String idCategoria) throws MiException {

        Optional<Categoria> respuesta = categoriaRepositorio.findById(idCategoria);

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
