package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Categoria;
import com.egg.servicios.entidades.Imagen;
import com.egg.servicios.entidades.Servicio;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.repositorios.CategoriaRepositorio;
import com.egg.servicios.repositorios.ServicioRepositorio;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.CategoriaRepositorio;

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
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    
    @Transactional
    public void crearServicio(String descripcion, Double honorariosHora, MultipartFile matricula, String idCategoria, String idProveedor) throws MiException {

        validar(descripcion, honorariosHora, matricula, idCategoria, idProveedor);

        try {
            Servicio servicio = new Servicio();

            servicio.setDescripcion(descripcion);

            servicio.setHonorariosPorHora(honorariosHora);

            Imagen imagen = imagenServicio.guardar(matricula);
            servicio.setMatricula(imagen);

            Categoria categoria = categoriaRepositorio.findById(idCategoria).get();
            servicio.setCategoria(categoria);

            Usuario proveedor = usuarioRepositorio.findById(idProveedor).get();
            servicio.setProveedor(proveedor);

            servicioRepositorio.save(servicio);

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    @Transactional
    public void actualizarServicio(String idServicio, String descripcion, Double honorariosHora,  MultipartFile archivo, String idCategoria, String idProveedor) throws MiException {

        validar(descripcion, honorariosHora, archivo, idCategoria, idProveedor);

        try {
            Optional<Servicio> respuesta = servicioRepositorio.findById(idServicio);

            if(respuesta.isPresent()) {

                Servicio servicio = respuesta.get();

                String idImagen = null;
                if (servicio.getMatricula() != null) {
                    idImagen = servicio.getMatricula().getId();
                }
                Imagen matricula = imagenServicio.actualizar(archivo, idImagen);
                servicio.setMatricula(matricula);

                servicio.setDescripcion(descripcion);

                servicio.setHonorariosPorHora(honorariosHora);

                Categoria categoria = categoriaRepositorio.findById(idCategoria).get();
                servicio.setCategoria(categoria);

                Usuario proveedor = usuarioRepositorio.findById(idProveedor).get();
                servicio.setProveedor(proveedor);

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

    public List<Servicio> listarServiciosProveedor(String idProveedor) {
        return servicioRepositorio.listarServiciosActivosPorProveedor(idProveedor);
    }

    @Transactional
    public void eliminarServicio(String idServicio) throws MiException {

        Optional<Servicio> respuesta = servicioRepositorio.findById(idServicio);

        try {
            if (respuesta.isPresent()) {

                Servicio servicio = respuesta.get();
                servicio.setAlta(false);
                servicioRepositorio.save(servicio);

            } else {
                throw new MiException("El ID Servicio no corresponde a ningun servicio existente.");
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    public void validar(String descripcion, Double honorariosHora, MultipartFile archivo, String idCategoria, String idProveedor) throws MiException {

//        if(archivo.isEmpty() || archivo == null) {
//            throw new MiException("El archivo no puede ser nulo o estar vacio.");
//        }
        if (descripcion.trim().isEmpty() || descripcion == null) {
            throw new MiException("La descripcion no puede ser nula o estar vacia.");
        }
        if (honorariosHora < 1 || honorariosHora.isNaN() || honorariosHora == null) {
            throw new MiException("Los honorarios no deben ser nulos y deben ser un numero valido.");
        }
        if (idCategoria.trim().isEmpty() || idCategoria == null){
            throw new MiException("El ID Categoria no puede ser nulo o estar vacio.");
        } else if (!categoriaRepositorio.findById(idCategoria).isPresent()) {
            throw new MiException("El ID Categoria no corresponde a ninguna categoria existente.");
        }
        if (idProveedor.trim().isEmpty() || idProveedor == null){
            throw new MiException("El ID Proveedor no puede ser nulo o estar vacio.");
        } else if (!usuarioRepositorio.findById(idProveedor).isPresent()) {
            throw new MiException("El ID Proveedor no corresponde a ningun proveedor existente.");
        }
        if (servicioRepositorio.findByDescripcion(descripcion).isPresent()) {
            throw new MiException("Existe un servicio publicado con la misma descripcion!");
        }

    }

}