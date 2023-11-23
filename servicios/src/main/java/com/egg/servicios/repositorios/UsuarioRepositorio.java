package com.egg.servicios.repositorios;





import com.egg.servicios.entidades.Usuario;
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
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email= :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("""
            SELECT s FROM Servicio s
            WHERE s.activo = true
            """)
    public List<Usuario> listarUsuariosActivos();

    @Query("""
            SELECT s FROM Servicio s
            WHERE s.activo = false
            """)
    public List<Usuario> listarUsuariosInactivos();

}
