package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.CalificacionRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// PARA HACER: añadir metodos { listarPorId(id), eliminarCalificacion(id) }
/**
 *
 * @author jose
 */
@Service
public class CalificacionServicio {

    @Autowired
    CalificacionRepositorio calificacionRepositorio;

    @Transactional
    public Calificacion crearCalificacion(String comentario, Integer puntuacion) throws MiException {
        validar(puntuacion);
        try {
            Calificacion calificacion = new Calificacion();

            calificacion.setPuntuacion(puntuacion);
            calificacion.setComentario(comentario);

            return calificacionRepositorio.save(calificacion);
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public List<Calificacion> listarCalificaciones() {
        List<Calificacion> calificaciones = calificacionRepositorio.listarCalificacionesActivos();
        return calificaciones;
    }

    public Calificacion listarPorId(String id) {
        return calificacionRepositorio.getById(id);
    }

    public void modificarCalificacion(String comentario, String id) throws MiException {

        try {
            Optional<Calificacion> respuesta = calificacionRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Calificacion calificacion = respuesta.get();
                calificacion.setComentario(comentario);
                calificacionRepositorio.save(calificacion);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    public void modificarCalificacion(String comentario, Integer puntuacion, String id) throws MiException {
        validar(puntuacion);

        try {
            Optional<Calificacion> respuesta = calificacionRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Calificacion calificacion = respuesta.get();
                calificacion.setPuntuacion(puntuacion);
                calificacion.setComentario(comentario);
                calificacionRepositorio.save(calificacion);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    public void alta(String id) throws MiException {

        try {
            Optional<Calificacion> respuesta = calificacionRepositorio.findById(id);

            if (respuesta.isPresent()) {
                Calificacion calificacion = respuesta.get();
                calificacion.setAlta(true);

                calificacionRepositorio.save(calificacion);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    public void baja(String id) throws MiException {

        try {
            Optional<Calificacion> respuesta = calificacionRepositorio.findById(id);

            if (respuesta.isPresent()) {
                Calificacion calificacion = respuesta.get();
                calificacion.setAlta(false);

                calificacionRepositorio.save(calificacion);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

    private void validar(Integer puntuacion) throws MiException {

        if (puntuacion > 5 || puntuacion < 1 || puntuacion == null) {
            throw new MiException("La puntuacion no esta en los parametros ni puede ser nula");
        }

    }
}
