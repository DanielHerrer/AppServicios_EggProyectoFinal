package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Imagen;
import com.egg.servicios.entidades.Servicio;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.repositorios.ServicioRepositorio;
import com.egg.servicios.excepciones.MiException;

import com.egg.servicios.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Daniel
 */
@Service
public class ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearServicio(String descripcion, MultipartFile archivo, String idCategoria, String idUsuario) throws MiException {

        validar(descripcion, idCategoria, archivo);

        try {
            Servicio servicio = new Servicio();

            servicio.setDescripcion(descripcion);

            Imagen matricula = imagenServicio.guardar(archivo);
            servicio.setMatricula(matricula);

            Categoria categoria = categoriaRepositorio.findById(idCategoria).get();
            servicio.setCategoria(categoria);

            Usuario proveedor = usuarioRepositorio.findById(idUsuario).get();
            servicio.setProveedor(proveedor);

            servicioRepositorio.save(servicio);

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    // Permite actualizar descripcion, categoria y/o matricula
    @Transactional
    public void actualizarServicio(String idServicio, String descripcion, MultipartFile archivo, String idCategoria) throws MiException {

        validar(descripcion, idCategoria, archivo);

        try {
            Optional<Servicio> respuesta = servicioRepositorio.findById(idServicio);

            if(respuesta.isPresent()) {

                Servicio servicio = respuesta.get();

                servicio.setDescripcion(descripcion);

                Categoria categoria = categoriaRepositorio.findById(idCategoria).get();
                servicio.setCategoria(categoria);

                // --------------------

                String idImagen = null;

                if (servicio.getMatricula() != null) {
                    idImagen = servicio.getMatricula().getId();
                }

                Imagen matricula = imagenServicio.actualizar(archivo, idImagen);
                servicio.setMatricula(matricula);

                // --------------------

                servicioRepositorio.save(servicio);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public Servicio listarPorId(String idServicio) {
        return servicioRepositorio.getReferenceById(idServicio);
    }

    public List<Servicio> listarServicios() {
        return servicioRepositorio.listarServiciosActivos();
    }

    public void validar(String descripcion, String idCategoria, MultipartFile archivo) throws MiException {

        if (descripcion.trim().isEmpty() || descripcion == null) {
            throw new MiException("La descripcion no puede ser nula o estar vacia.");
        }
        if (servicioRepositorio.buscarPorDescripcion(descripcion) != null) {
            throw new MiException("Existe un servicio publicado con la misma descripcion!");
        }
        if(idCategoria.trim().isEmpty() || idCategoria == null){
            throw new MiException("La categoria no puede ser nula o estar vacia.");
        }
        if(archivo.isEmpty() || archivo == null) {
            throw new MiException("El archivo no puede ser nulo o estar vacio.");
        }
    }

}