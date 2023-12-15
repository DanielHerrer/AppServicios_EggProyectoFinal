package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.accUsuario = :accUsuario")
    public Optional<Usuario> findByAccUsuario(@Param("accUsuario") String accUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.alta = true ORDER BY u.nombre ASC")
    public List<Usuario> listarUsuariosActivos();

    @Query("SELECT u FROM Usuario u WHERE u.alta = false ORDER BY u.nombre ASC")
    public List<Usuario> listarUsuariosInactivos();

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'CLIENTE' ORDER BY u.nombre ASC")
    public List<Usuario> listarClientes();

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'PROVEEDOR' ORDER BY u.nombre ASC")
    public List<Usuario> listarProveedores();

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'ADMIN'")
    public List<Usuario> listarAdmin();
}
