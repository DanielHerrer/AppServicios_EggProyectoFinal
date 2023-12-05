package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Contrato;

import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.enumeraciones.Estados;

import com.egg.servicios.excepciones.MiException;
import com.egg.servicios.repositorios.CalificacionRepositorio;
import com.egg.servicios.repositorios.OfertaRepositorio;

import java.util.Optional;
import javax.transaction.Transactional;

import com.egg.servicios.repositorios.UsuarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.servicios.repositorios.ContratoRepositorio;

// PARA HACER: añadir metodos { listarContratos(), listarPorId(id) }
/**
 *
 * @author joaquin
 */
@Service
public class ContratoServicio {
    
    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @Autowired
    private CalificacionRepositorio calificacionRepositorio;

    @Transactional
    public void crearContrato(String idOferta) throws MiException {
        Optional<Oferta> respuestaOferta = ofertaRepositorio.findById(idOferta);
        Oferta oferta = new Oferta();

        if (respuestaOferta.isPresent()) {
            oferta = respuestaOferta.get();
        }

        try {
            Contrato contrato = new Contrato();
            contrato.setOferta(oferta);
            contrato.setEstadoTrabajo(Estados.PENDIENTE);
            contrato.isAlta();

            contratoRepositorio.save(contrato);
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void modificarContrato(String idContrato, Estados estado) throws MiException {
        Optional<Contrato> respuestaContrato = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();
        

        if (respuestaContrato.isPresent()) {
            contrato = respuestaContrato.get();
            contrato.setEstadoTrabajo(estado);

            contratoRepositorio.save(contrato);
        }

        try {

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void calificarContrato(String idContrato, Calificacion calificacion, Estados estado) throws MiException {
        Optional<Contrato> respuestaContrato = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();

        if (respuestaContrato.isPresent()) {
            contrato = respuestaContrato.get();
            contrato.setEstadoTrabajo(estado.FINALIZADO);

            contratoRepositorio.save(contrato);
        }

        try {

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public List<Contrato> listarActivos() {
        List<Contrato> contratos = contratoRepositorio.listarContratoActivos();
        return contratos;
    }

    public List<Contrato> listarInactivos() {
        List<Contrato> contratos = contratoRepositorio.listarContratoInactivos();
        return contratos;
    }

    public List<Contrato> listarPendientes() {
        List<Contrato> contratos = contratoRepositorio.listarPendientes();
        return contratos;
    }

    public List<Contrato> listarRechazados() {
        List<Contrato> contratos = contratoRepositorio.listarRechazados() ;
        return contratos;
    }

    public List<Contrato> listarAceptados() {
        List<Contrato> contratos = contratoRepositorio.listarAceptados();
        return contratos;
    }

    public List<Contrato> listarFinalizados() {
        List<Contrato> contratos = contratoRepositorio.listarFinalizado() ;
        return contratos;
    }

    public List<Contrato> listarProveedores(String idProveedor) {
        List<Contrato> contratos = contratoRepositorio.listarProveedor(idProveedor);
        return contratos;
    }
    
    public void validar(Estados estado) throws MiException {
        if (estado.equals(null) || estado == null) {
            throw new MiException("El Estado no puede ser nulo !");
        }

    }
    
        public List<Contrato> listaCompleta() {
        List<Contrato> contratos = contratoRepositorio.findAll();
        return contratos;
    }
    
    public Contrato getOne(String id) {
        return contratoRepositorio.getOne(id);
    }

}
