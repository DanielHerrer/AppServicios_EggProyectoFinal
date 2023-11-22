/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.entidades;

import com.egg.servicios.enumeraciones.Rol;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author joaquin
 */
public class Contrato {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Rol estado_trabajo;
    private Oferta oferta;
    private Calificacion aptitud;

    public Contrato(String id, Rol estado_trabajo, Oferta oferta, Calificacion aptitud) {
        this.id = id;
        this.estado_trabajo = estado_trabajo;
        this.oferta = oferta;
        this.aptitud = aptitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rol getEstado_trabajo() {
        return estado_trabajo;
    }

    public void setEstado_trabajo(Rol estado_trabajo) {
        this.estado_trabajo = estado_trabajo;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Calificacion getAptitud() {
        return aptitud;
    }

    public void setAptitud(Calificacion aptitud) {
        this.aptitud = aptitud;
    }

}
