package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.CalificacionRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalificacionServicio {

    @Autowired
    CalificacionRepositorio calificacionRepositorio;

    @Transactional
    public void crearCalificacion(String comentario, Integer puntuacion) throws MiException {
        validar(puntuacion);
        try {
            Calificacion calificacion = new Calificacion();

            calificacion.setPuntuacion(puntuacion);
            calificacion.setComentario(comentario);

            calificacionRepositorio.save(calificacion);
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public List<Calificacion> listarCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList();

        calificaciones = calificacionRepositorio.findAll();

        return calificaciones;

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

    private void validar(Integer puntuacion) throws MiException {
        if (puntuacion > 5 || puntuacion < 0 || puntuacion == null) {
            throw new MiException("la puntuacion no esta en los parametros ni puede ser nula");
        }
    }
}
