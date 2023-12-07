package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Martin
 */
@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, String> {

    @Query("SELECT c FROM Categoria c WHERE c.nombre LIKE :nombre")
    public List<Categoria> findByNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Categoria c WHERE c.alta = true")
    public List<Categoria> listarCategoriasAlta();

    @Query("SELECT c FROM Categoria c WHERE c.alta = false")
    public List<Categoria> listarCategoriasBaja();

}
