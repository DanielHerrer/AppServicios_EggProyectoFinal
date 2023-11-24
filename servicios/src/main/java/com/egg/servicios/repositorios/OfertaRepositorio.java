package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Oferta;
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
public interface OfertaRepositorio extends JpaRepository<Oferta, String> {

    @Query("""
            SELECT o FROM Oferta o
            WHERE o.id = :id
            """)
    public Oferta listarOfertaPorId(@Param("id") String idOferta);

    @Query("""
            SELECT o FROM Oferta o
            WHERE o.alta = true
            """)
    public List<Oferta> listarOfertasActivas();

    @Query("""
            SELECT o FROM Oferta o
            WHERE o.alta = false
            """)
    public List<Oferta> listarOfertasInactivas();

    @Query("""
            SELECT o FROM Oferta o
            WHERE o.servicio.id = (SELECT s.id FROM Servicio s WHERE s.proveedor.id = :id_proveedor)
            """)
    public List<Oferta> listarOfertasPorIdProveedor(@Param("id_proveedor") String idProveedor);

    @Query("""
            SELECT o FROM Oferta o
            WHERE o.cliente.id = :id_cliente
            """)
    public List<Oferta> listarOfertasPorIdCliente(@Param("id_cliente") String idCliente);

}