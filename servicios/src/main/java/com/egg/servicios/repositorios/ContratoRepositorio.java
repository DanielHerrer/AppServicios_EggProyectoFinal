package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Contrato;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, String> {

    @Query("SELECT c FROM Contrato c WHERE c.id = :id")
    public Contrato listarContratoPorId(@Param("id") String id);

    @Query("SELECT c FROM Contrato c WHERE c.alta = true")
    public List<Contrato> listarContratoActivos();

    @Query("SELECT c FROM Contrato c WHERE c.alta = false")
    public List<Contrato> listarContratoInactivos();

    @Query("""
           SELECT c FROM Contrato c 
           WHERE c.estadoTrabajo = 'PENDIENTE'
           """
           )
    public List<Contrato> listarPendientes();

    @Query("""
           SELECT c FROM Contrato c 
           WHERE c.estadoTrabajo = 'RECHAZADO'
           """)                                                    
    public List<Contrato> listarRechazados();

    @Query("""
           SELECT c FROM Contrato c 
           WHERE c.estadoTrabajo = 'ACEPTADO'
           """)           
    public List<Contrato> listarAceptados();

    @Query("""
           SELECT c FROM Contrato c 
           WHERE c.estadoTrabajo = 'FINALIZADO'
           """)           
    public List<Contrato> listarFinalizado();

    @Query("SELECT c FROM Contrato c WHERE c.oferta.servicio.proveedor.id = :idProveedor")
    public List<Contrato> listarProveedor(@Param("idProveedor") String idProveedor);

}
