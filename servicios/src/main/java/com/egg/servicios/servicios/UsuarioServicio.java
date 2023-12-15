package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Imagen;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.enumeraciones.Rol;
import com.egg.servicios.enumeraciones.Ubicacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.UsuarioRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

// PARA HACER: añadir metodos { listarUsuariosActivos(), listarUsuarioPorId(id), eliminarUsuario(id) }
/**
 * @author Nico
 */
@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    public void registrar(MultipartFile archivo, String accUsuario, Rol rol, String nombre, String email, Ubicacion ubicacion, String password, String password2) throws MiException {

        validar(nombre, email, accUsuario, ubicacion, password, password2);

        Usuario usuario = new Usuario();

        usuario.setAccUsuario(accUsuario);
        usuario.setNombre(nombre);
        usuario.setEmail(email);

        usuario.setUbicacion(ubicacion);

        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRol(rol);

        Imagen imagen = imagenServicio.guardar(archivo);

        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
    }

    public void actualizar(MultipartFile archivo, String idUsuario, Rol rol, Ubicacion ubicacion, String nombre, String accUsuario, String email, String password, String password2) throws MiException {

        validar(nombre, email, accUsuario, ubicacion, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setUbicacion(ubicacion);

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuario.setRol(rol);

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = (Imagen) imagenServicio.actualizar(archivo, idImagen);

            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }

    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

    public List<Usuario> listarUsuariosActivos() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.listarUsuariosActivos();

        return usuarios;
    }

    public List<Usuario> listarUsuariosInactivos() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.listarUsuariosInactivos();

        return usuarios;

    }

    public List<Usuario> listarClientes() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.listarClientes();

        return usuarios;

    }

    public List<Usuario> listarProveedores() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.listarProveedores();

        return usuarios;

    }

    public List<Usuario> listarAdmin() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.listarAdmin();

        return usuarios;

    }
    
    public void alta(String id) {
        Optional<Usuario> usuarioRespuesta = usuarioRepositorio.findById(id);
        //Persistimos con repositorio, buscamos por id, verificamos que la respuesta este presente y la asignamos a una variable usuario,
        // en esta se setea el alta como falso("eliminado") y se vuelve a persistir para guardar en el repositorio.
        if (usuarioRespuesta.isPresent()) {
            Usuario usuario = usuarioRespuesta.get();
            usuario.setAlta(true);

            usuarioRepositorio.save(usuario);
        }

    }
    
        
    public void baja(String id) {
        Optional<Usuario> usuarioRespuesta = usuarioRepositorio.findById(id);
        //Persistimos con repositorio, buscamos por id, verificamos que la respuesta este presente y la asignamos a una variable usuario,
        // en esta se setea el alta como falso("eliminado") y se vuelve a persistir para guardar en el repositorio.
        if (usuarioRespuesta.isPresent()) {
            Usuario usuario = usuarioRespuesta.get();
            usuario.setAlta(false);

            usuarioRepositorio.save(usuario);
        }

    }

    public void modificar(String id, Boolean alta) {
        Optional<Usuario> usuarioRespuesta = usuarioRepositorio.findById(id);
        //Persistimos con repositorio, buscamos por id, verificamos que la respuesta este presente y la asignamos a una variable usuario,
        // en esta se setea el alta como falso("eliminado") y se vuelve a persistir para guardar en el repositorio.
        if (usuarioRespuesta.isPresent()) {
            Usuario usuario = usuarioRespuesta.get();
            usuario.setAlta(alta);

            usuarioRepositorio.save(usuario);
        }

    }

    public void modificarRolAdmin(String id) {
        Optional<Usuario> usuarioRespuesta = usuarioRepositorio.findById(id);
        //Persistimos con repositorio, buscamos por id, verificamos que la respuesta este presente y la asignamos a una variable usuario,
        // en esta se setea el alta como falso("eliminado") y se vuelve a persistir para guardar en el repositorio.
        if (usuarioRespuesta.isPresent()) {
            Usuario usuario = usuarioRespuesta.get();
            usuario.setRol(Rol.ADMIN);

            usuarioRepositorio.save(usuario);
        }

    }

    public Usuario getOne(String id) {
        return (Usuario) usuarioRepositorio.getOne(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> user = usuarioRepositorio.findByEmail(email);

        if (user.isPresent()) {

            Usuario usuario = user.get();
            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            // Utilizamos los atributos que nos otorga el pedido al servlet, para poder guardar la
            // información de nuestra HttpSession.
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuarioSession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

    }

    public boolean existsByAccUsuario(String accUsuario) throws MiException {
        try {
            Optional<Usuario> usuario = usuarioRepositorio.findByAccUsuario(accUsuario);
            if (usuario.isPresent()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public boolean existsByEmail(String email) throws MiException {
        try {
            Optional<Usuario> usuario = usuarioRepositorio.findByEmail(email);
            if (usuario.isPresent()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public boolean configurarUsuario(MultipartFile archivo, String nombre, String email, String id, String password, String password2, String accUsuario, Ubicacion ubicacion) throws MiException {
        try {

            validarUsuario(email, id, password, password2);
            Optional<Usuario> userResponse = usuarioRepositorio.findById(id);
            if (userResponse.isPresent()) {
                Usuario user = userResponse.get();

                if (!email.isEmpty()) {
                    user.setEmail(email);
                }
                if (!nombre.isEmpty()) {
                    user.setNombre(nombre);
                }
                if (!password.isEmpty()) {
                    user.setPassword(new BCryptPasswordEncoder().encode(password));
                }
                if (ubicacion != null) {
                    user.setUbicacion(ubicacion);
                }
                if (!accUsuario.isEmpty()) {
                    user.setAccUsuario(accUsuario);
                }
                String idImagen = "";

                if (!archivo.isEmpty()) {
                    if (user.getImagen() != null) {
                        idImagen = user.getImagen().getId();
                    }
                    Imagen imagen = (Imagen) imagenServicio.actualizar(archivo, idImagen);
                    user.setImagen(imagen);
                }

                usuarioRepositorio.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    private void validarUsuario(String email, String id, String password, String password2) throws MiException {
        Optional<Usuario> user1 = usuarioRepositorio.findByEmail(email);
        Usuario u1 = user1.get();
        Optional<Usuario> user2 = usuarioRepositorio.findById(id);
        Usuario u2 = user2.get();
        if (u1.getEmail() != u2.getEmail()) {
            throw new MiException("Estas ingresando con un email que no pertenece a esta cuenta");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede ser nulo o estar vacio.");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales.");
        }

    }

    private void validar(String nombre, String email, String accUsuario, Ubicacion ubicacion, String password, String password2) throws MiException {
        
        byte bites = (byte) 1048576;
        
        if (nombre.trim().isEmpty() || nombre == null) {
            throw new MiException("El Nombre no puede ser nulo o estar vacío");
        }
        if (accUsuario.trim().isEmpty() || accUsuario == null) {
            throw new MiException("El Nombre de usuario no puede ser nulo o estar vacio");
        } else if (existsByAccUsuario(accUsuario)) {
            throw new MiException("Ya existe una cuenta con ese Nombre de usuario registrado..");
        }
        if (ubicacion.equals("") || ubicacion == null) {
            throw new MiException("La Ubicacion no puede ser nula o estar vacia");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El Email no puede ser nulo o estar vacio");
        } else if (existsByEmail(email)) {
            throw new MiException("Ya existe una cuenta con ese Email registrado..");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La Contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las Contraseñas ingresadas deben ser iguales");
        }

    }

}
