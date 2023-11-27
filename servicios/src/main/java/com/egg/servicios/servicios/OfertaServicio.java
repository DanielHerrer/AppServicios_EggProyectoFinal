package com.egg.servicios.servicios;

import com.egg.servicios.entidades.*;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.OfertaRepositorio;
import com.egg.servicios.repositorios.ServicioRepositorio;
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
public class OfertaServicio {

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Transactional
    public void crearOferta(String descripcion, String idServicio, String idCliente) throws MiException {

        validar(descripcion, idServicio, idCliente);

        try {

            Oferta oferta = new Oferta();

            oferta.setDescripcion(descripcion);

            Servicio servicio = servicioRepositorio.findById(idServicio).get();
            oferta.setServicio(servicio);

            Usuario cliente = usuarioRepositorio.findById(idCliente).get();
            oferta.setCliente(cliente);

            servicioRepositorio.save(servicio);

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    @Transactional
    public void actualizarOferta(String idOferta, String descripcion, String idServicio, String idCliente) throws MiException {

        validar(descripcion, idServicio, idCliente);

        try {
            Optional<Oferta> respuesta = ofertaRepositorio.findById(idOferta);

            if(respuesta.isPresent()) {

                Oferta oferta = respuesta.get();

                oferta.setDescripcion(descripcion);

                Servicio servicio = servicioRepositorio.findById(idServicio).get();
                oferta.setServicio(servicio);

                Usuario cliente = usuarioRepositorio.findById(idCliente).get();
                oferta.setCliente(cliente);

                ofertaRepositorio.save(oferta);

            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public Oferta listarPorId(String idOferta) {
        return ofertaRepositorio.getReferenceById(idOferta);
    }

    public List<Oferta> listarOfertas() {
        return ofertaRepositorio.listarOfertasActivas();
    }

    @Transactional
    public void eliminarOferta(String idOferta) throws MiException {

        Optional<Oferta> respuesta = ofertaRepositorio.findById(idOferta);

        try {
            if (respuesta.isPresent()) {

                Oferta oferta = respuesta.get();
                oferta.setAlta(false);
                ofertaRepositorio.save(oferta);

            } else {
                throw new MiException("El ID Oferta no corresponde a ninguna oferta existente.");
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    public void validar(String descripcion, String idServicio, String idCliente) throws MiException {

        if (descripcion.trim().isEmpty() || descripcion == null) {
            throw new MiException("La descripcion no puede ser nula o estar vacia.");
        } else if (servicioRepositorio.buscarPorDescripcion(descripcion) != null) {
            throw new MiException("Ya existe un servicio publicado con la misma descripcion.");
        }
        if (idServicio.trim().isEmpty() || idServicio == null){
            throw new MiException("El ID Servicio no puede ser nulo o estar vacio.");
        } else if (!servicioRepositorio.findById(idServicio).isPresent()) {
            throw new MiException("El ID Servicio no corresponde a ningun servicio existente.");
        }
        if (idCliente.trim().isEmpty() || idCliente == null){
            throw new MiException("El ID Cliente no puede ser nulo o estar vacio.");
        } else if (!usuarioRepositorio.findById(idCliente).isPresent()) {
            throw new MiException("El ID Cliente no corresponde a ningun cliente existente.");
        }

    }

}
