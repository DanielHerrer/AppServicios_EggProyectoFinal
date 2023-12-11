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

    @Query("SELECT c FROM Contrato c WHERE c.alta = true")
    public List<Contrato> listarContratosActivos();

    @Query("SELECT c FROM Contrato c WHERE c.alta = false")
    public List<Contrato> listarContratosInactivos();

    @Query("SELECT c FROM Contrato c WHERE c.oferta.servicio.proveedor.id = :idProveedor")
    public List<Contrato> listarContratosPorProveedor(@Param("idProveedor") String idProveedor);

    @Query("SELECT c FROM Contrato c WHERE c.oferta.cliente.id = :idCliente")
    public List<Contrato> listarProveedor(@Param("idCliente") String idCliente);

}