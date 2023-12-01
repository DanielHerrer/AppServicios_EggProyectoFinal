package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Contrato;

import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.enumeraciones.Estados;

import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.ContratoRepositorios;

import java.util.Optional;
import javax.transaction.Transactional;

import com.egg.servicios.repositorios.UsuarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// PARA HACER: añadir metodos { listarContratos(), listarPorId(id) }
/**
 *
 * @author joaquin
 */
@Service
public class ContratoServicios {

    @Autowired
    private ContratoRepositorios contratoRepo;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    public ContratoServicios(ContratoRepositorios contratoRepo, UsuarioRepositorio usuarioRepo) {
        this.contratoRepo = contratoRepo;
        this.usuarioRepo = usuarioRepo;
    }
    
    @Transactional
    public Contrato crearContrato(Oferta oferta) throws MiException {

        try {

            Contrato contrato = new Contrato();
            contrato.setOferta(oferta);
            contrato.setEstadoTrabajo(Estados.PENDIENTE);
            contrato.setAptitud(null);

            return contratoRepo.save(contrato);

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    @Transactional
    public void guardarContrato(Estados estados, Oferta oferta, Calificacion aptitud) throws MiException {

        try {
            validar(estados);
            Contrato contrato = new Contrato();
            contrato.setAptitud(aptitud);
            contrato.setOferta(oferta);
            contratoRepo.save(contrato);

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    
    
    
    
    public void modificarEstadoContrato(String id, Estados state) throws MiException {
        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setEstadoTrabajo(state);
                contratoRepo.save(c);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void contratoFinalizado(String id, Estados state, Calificacion aptitud) throws MiException {

        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setEstadoTrabajo(state);
                c.setAptitud(aptitud);
                contratoRepo.save(c);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void estadosDeContratos(String id, Estados state) throws MiException {

        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setEstadoTrabajo(state);
                contratoRepo.save(c);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }
    
    public void altaBajaContrato(String id) throws MiException {
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

    public List<Contrato> listarContratos() throws MiException {
        try {
            List<Contrato> contratoList = contratoRepo.listarContratosActivos();
            return contratoList;
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }
 
    public Optional<Contrato> listarContratosPorId(String id) throws MiException {
        try {    
            Optional<Contrato> contratoList = contratoRepo.findById(id);
            return contratoList;
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public List<Contrato> listarContratosPorProveedor(String idProveedor) throws MiException {
        try {
            List<Contrato> contratos = contratoRepo.listarContratosPorProveedor(idProveedor);
            return contratos;

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void validar(Estados state) throws MiException {
        if (state.equals(null) || state == null) {
            throw new MiException("El Estado no puede ser nulo !");
        }

    }

    public Contrato getOne(String id) {
        return contratoRepo.getOne(id);
    }

}
