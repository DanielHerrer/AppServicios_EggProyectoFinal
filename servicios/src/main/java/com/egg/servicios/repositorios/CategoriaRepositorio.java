package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Categoria;
import java.util.List;
import java.util.Optional;

import com.egg.servicios.entidades.Servicio;
import org.springframework.data.domain.Pageable;
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

    // Pageable se utiliza para agregar la funcionalidad de paginación y limitar los resultados a 1.
    @Query("SELECT c FROM Categoria c WHERE c.nombre LIKE :nombre")
    public Optional<Categoria> findByNombre(@Param("nombre") String nombre, Pageable pageable);

    @Query("SELECT c FROM Categoria c WHERE c.alta = true")
    public List<Categoria> listarCategoriasAlta();
    
}
