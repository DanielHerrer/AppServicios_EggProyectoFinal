package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Daniel
 */
@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {

    @Query("""
            SELECT s FROM Servicio s
            WHERE s.activo = true
            """)
    public List<Servicio> listarServiciosActivos();

    @Query("""
            SELECT s FROM Servicio s
            WHERE s.activo = false
            """)
    public List<Servicio> listarServiciosInactivos();

    @Query("""
            SELECT s FROM Servicio s
            WHERE s.descripcion = :descripcion
            """)
    public List<Servicio> buscarPorDescripcion(@Param("descripcion") String descripcion);

}
