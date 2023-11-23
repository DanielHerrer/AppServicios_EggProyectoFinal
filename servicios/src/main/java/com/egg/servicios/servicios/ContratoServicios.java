/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.servicios;

import com.egg.servicios.entidades.Contrato;
import com.egg.servicios.enumeraciones.Rol;
import com.egg.servicios.repositorios.ContratoRepositorios;
import java.util.Optional;
import javax.transaction.Transactional;
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

    private UsuarioRepositorios usuarioRepo;

    public ContratoServicios(ContratoRepositorios contratoRepo, UsuarioRepositorios usuarioRepo) {
        this.contratoRepo = contratoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Transactional
    public void guardarContrato(Rol estados, Oferta ofertas, Calificacion aptitud) {
        try {
            Contrato contrato = new Contrato();
            contrato.setAptitud(aptitud);
            contrato.setOferta(ofertas);
            contratoRepo.save(contrato);
        } catch (MiException e) {
            System.out.println(e.getMessege());
        }
    }

    public void actualizarContrato(String id, Rol state) {
        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setEstado_trabajo(state);
                contratoRepo.save(c);
            }
        } catch (MiException e) {
            System.out.println(e.getMessege());
        }
    }

    public void actualizarContrato(String id, Rol state, Integer puntaje) {
        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setEstado_trabajo(state);
                c.setAptitud(id);
                contratoRepo.save(c);
            }
        } catch (MiException e) {
            System.out.println(e.getMessege());
        }
    }

    public void eliminarContrato(String id) {
        try {
            Optional<Contrato> presente = contratoRepo.findById(id);
            if (presente.isPresent()) {
                Contrato c = presente.get();
                c.setAlta(false);
                contratoRepo.save(c);
            }
        } catch (MiException e) {
            System.out.println(e.getMessege());
        }

    }

}
