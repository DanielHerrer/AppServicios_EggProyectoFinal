package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Contrato;
import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.enumeraciones.Rol;
import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.ContratoRepositorios;

import java.util.Optional;
import javax.transaction.Transactional;

import com.egg.servicios.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author joaquin
 */
@Service
public class ContratoServicios {

    @Autowired
    private ContratoRepositorios contratoRepo;

    private UsuarioRepositorio usuarioRepo;

    public ContratoServicios(ContratoRepositorios contratoRepo, UsuarioRepositorio usuarioRepo) {
        this.contratoRepo = contratoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Transactional
    public void guardarContrato(Rol estados, Oferta oferta, Calificacion aptitud) throws MiException {
        try {
            Contrato contrato = new Contrato();
            contrato.setAptitud(aptitud);
            contrato.setOferta(oferta);
            contratoRepo.save(contrato);
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void actualizarContrato(String id, Rol state) throws MiException {
        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setEstado_trabajo(state);
                contratoRepo.save(c);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void actualizarContrato(String id, Rol state, Calificacion aptitud) throws MiException {
        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setEstado_trabajo(state);
                c.setAptitud(aptitud);
                contratoRepo.save(c);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void eliminarContrato(String id) throws MiException {
        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setAlta(false);
                contratoRepo.save(c);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

    }

}
