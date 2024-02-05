package com.egg.servicios.repositories;

import com.egg.servicios.entities.Contrato;
import java.util.List;

import com.egg.servicios.enums.Estados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public interface ContratoRepository extends JpaRepository<Contrato, String> {

    @Query("SELECT c FROM Contrato c WHERE c.id = :id")
    public Contrato getContratoById(@Param("id") String id);

    @Query("SELECT c FROM Contrato c WHERE c.alta = true ORDER BY c.fecha DESC")
    public List<Contrato> findContratosByAltaTrue();

    @Query("SELECT c FROM Contrato c WHERE c.alta = false")
    public List<Contrato> findContratosByAltaFalse();

    @Query("SELECT c FROM Contrato c WHERE c.alta = true AND c.estadoTrabajo = com.egg.servicios.enums.Estados.PENDIENTE ORDER BY c.fecha DESC")
    public List<Contrato> findContratosByEstadoTrabajoPendiente();

    @Query("SELECT c FROM Contrato c WHERE c.alta = true AND c.estadoTrabajo = com.egg.servicios.enums.Estados.RECHAZADO ORDER BY c.fecha DESC")
    public List<Contrato> findContratosByEstadoTrabajoRechazado();

    @Query("SELECT c FROM Contrato c WHERE c.alta = true AND c.estadoTrabajo = com.egg.servicios.enums.Estados.ACEPTADO ORDER BY c.fecha DESC")
    public List<Contrato> findContratosByEstadoTrabajoAceptado();

    @Query("SELECT c FROM Contrato c WHERE c.alta = true AND c.estadoTrabajo = com.egg.servicios.enums.Estados.FINALIZADO ORDER BY c.fecha DESC")
    public List<Contrato> findContratosByEstadoTrabajoFinalizado();

    @Query("SELECT c FROM Contrato c WHERE c.alta = true AND c.oferta.servicio.proveedor.id = :idProveedor ORDER BY c.fecha DESC")
    public List<Contrato> findContratosByIdProveedor(@Param("idProveedor") String idProveedor);

    @Query("SELECT c FROM Contrato c WHERE c.alta = true AND c.oferta.cliente.id = :idCliente ORDER BY c.fecha DESC")
    public List<Contrato> findContratosByIdCliente(@Param("idCliente") String idCliente);

    @Query("SELECT c FROM Contrato c " +
            "WHERE c.alta = true " +
            "  AND c.aptitud IS NULL " +
            "  AND c.estadoTrabajo IN :estados " +
            "  AND (c.oferta.servicio.proveedor.id = :idUsuario OR c.oferta.cliente.id = :idUsuario)")
    public List<Contrato> countNotificacionesByIdUsuario(
            @Param("idUsuario") String idUsuario, @Param("estados") Estados[] estados);

}