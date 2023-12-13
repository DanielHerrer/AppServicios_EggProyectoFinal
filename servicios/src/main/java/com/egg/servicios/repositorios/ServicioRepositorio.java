package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Servicio;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Daniel
 */
@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {

    @Query("SELECT s FROM Servicio s WHERE s.alta = true ")
    public List<Servicio> listarServiciosActivos();

    @Query("SELECT s FROM Servicio s WHERE s.alta = false")
    public List<Servicio> listarServiciosInactivos();

    @Query("SELECT s FROM Servicio s WHERE s.descripcion LIKE :descripcion")
    public List<Servicio> findByDescripcion(@Param("descripcion") String descripcion);

    // Selecciona los servicios que estan asociados al proveedor proporcionado
    @Query("SELECT s FROM Servicio s WHERE s.proveedor.id = :idProveedor")
    public List<Servicio> listarServiciosPorProveedor(@Param("idProveedor") String idProveedor);

    // Selecciona los servicios activos que no tienen un contrato asociado al cliente proporcionado
    @Query("SELECT s FROM Servicio s WHERE s.alta = true " +
            "AND s.id NOT IN (SELECT c.oferta.servicio.id FROM Contrato c WHERE c.oferta.cliente.id = :idCliente)")
    public List<Servicio> listarServiciosActivosPorCliente(@Param("idCliente") String idCliente);

    // Selecciona servicios relacionados según la lupa de búsqueda
    @Query("SELECT s FROM Servicio s " +
            "WHERE s.alta = true " +
            "AND (s.categoria.nombre LIKE %:input% " +
            "OR s.proveedor.nombre LIKE %:input% " +
            "OR s.descripcion LIKE %:input%)")
    public List<Servicio> listarServiciosBusqueda(@Param("input") String input);

    // Selecciona servicios relacionados según la lupa de búsqueda y que el cliente NO HAYA SOLICITADO
    @Query("SELECT s FROM Servicio s WHERE s.alta = true " +
            "AND s.id NOT IN (SELECT c.oferta.servicio.id FROM Contrato c WHERE c.oferta.cliente.id = :idCliente) " +
            "AND (s.categoria.nombre LIKE %:input% " +
            "OR s.proveedor.nombre LIKE %:input% " +
            "OR s.descripcion LIKE %:input%)")
    List<Servicio> listarServiciosBusquedaCliente(@Param("idCliente") String idCliente, @Param("input") String input);

}
