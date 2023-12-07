package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Contrato;

import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.entidades.Usuario;
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

    @Autowired
    private OfertaServicio ofertaServicio;

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

    public void altaBajaContrato(String idContrato, Boolean alta) {
        Optional<Contrato> respuestaContrato = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();

        if (respuestaContrato.isPresent()) {
            contrato = respuestaContrato.get();
        }
        contrato.setAlta(alta);
        contratoRepositorio.save(contrato);
    }

    //ACEPTADO O RECHAZADO
    public void modificarContrato(String idContrato, Estados estado) {
        Optional<Contrato> respuestaContrato = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();

        if (respuestaContrato.isPresent()) {
            contrato = respuestaContrato.get();
            contrato.setEstadoTrabajo(estado);

            contratoRepositorio.save(contrato);
        }

    }

    public List<Contrato> listarContratosPorProveedor(String idProveedor) {
            List<Contrato> contratos = contratoRepositorio.listarProveedor(idProveedor);
            return contratos;
    }
    
    public List<Contrato> listarClientesAProveedor(Usuario usuario) {
        return null;
    }

//    public String listarContratoCliente(String idContrato) {
//        Contrato contrato = new Contrato();
//        Oferta oferta = new Oferta();
//
//        contrato = getOne(idContrato);//traemos contrato lo guardamos en variable contrato
//        oferta = ofertaServicio.listarPorId(contrato.getOferta().getId());//listamos ofertas por id con el contrato traido
//        return oferta.getCliente().getId();//devolvemos el id correspondiente al cliente de la oferta
//    }
//
//    public String listarContratoProveedor(String idContrato) {
//        Contrato contrato = new Contrato();
//        Oferta oferta = new Oferta();
//
//        contrato = getOne(idContrato);//traemos contrato lo guardamos en variable contrato
//        oferta = ofertaServicio.listarPorId(contrato.getOferta().getId());//listamos ofertas por id con el contrato traido
//
//        return oferta.getServicio().getId();//devolvemos el id correspondiente al proveedor de la oferta
//    }
//    
//    public String listarContratosCliente(String idCliente) {
//        List<Contrato> contratos = contratoRepositorio.findAllById(idCliente);
//        return contratos;
//    }
    //FINALIZADO
    public void calificarContrato(String idContrato, Calificacion calificacion) {
        Optional<Contrato> respuestaContrato = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();

        if (respuestaContrato.isPresent()) {
            contrato = respuestaContrato.get();
            contrato.setEstadoTrabajo(Estados.FINALIZADO);
            contrato.setAlta(false);

            contratoRepositorio.save(contrato);
        }

    }

    public List<Contrato> listarContratos() {
        List<Contrato> contratos = contratoRepositorio.findAll();
        return contratos;
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
        List<Contrato> contratos = contratoRepositorio.listarRechazados();
        return contratos;
    }

    public List<Contrato> listarAceptados() {
        List<Contrato> contratos = contratoRepositorio.listarAceptados();
        return contratos;
    }

    public List<Contrato> listarFinalizados() {
        List<Contrato> contratos = contratoRepositorio.listarFinalizado();
        return contratos;
    }

    public List<Contrato> listarProveedores(String idProveedor) {
        List<Contrato> contratos = listaCompleta();
        return contratos;
    }

    public List<Contrato> ListarClientes(String idCliente) {
        List<Contrato> contratos = listaCompleta();
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
