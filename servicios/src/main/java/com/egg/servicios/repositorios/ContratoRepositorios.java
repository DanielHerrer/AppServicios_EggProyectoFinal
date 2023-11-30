package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Contrato;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author joaquin
 */
@Repository
public interface ContratoRepositorios extends JpaRepository<Contrato, String> {

    @Query("SELECT c FROM Contrato c WHERE c.id = :id")
    public Contrato listarContratoPorId(@Param("id") String id);

    @Query("""
            SELECT c FROM Contrato c
            WHERE c.alta = true
            """)
    public List<Contrato> listarContratoActivos();

    @Query("""
            SELECT c FROM Contrato c
            WHERE c.alta = false
            """)
    public List<Contrato> listarContratoInactivos();

}
