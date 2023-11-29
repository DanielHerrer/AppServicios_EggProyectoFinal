
package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Calificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jose
 */
@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion, String> {
    
    @Query("""
            SELECT c FROM Calificacion  c
            WHERE c.alta = true
            """)
    public List<Calificacion> listarCalificacionesActivos();
}
