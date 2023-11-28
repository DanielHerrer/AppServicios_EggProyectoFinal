package com.egg.servicios.entidades;

import com.egg.servicios.enumeraciones.Rol;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 *
 * @author joaquin un capo el muchacho
 */
@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Rol estadoTrabajo;
    @OneToOne
    private Oferta oferta;
    @OneToOne
    private Calificacion aptitud;
    private boolean alta;

    public Contrato() {
        this.alta = true;
    }

    public Contrato(String id, Rol estadoTrabajo, Oferta oferta, Calificacion aptitud) {
        this.id = id;
        this.estadoTrabajo = estadoTrabajo;
        this.oferta = oferta;
        this.aptitud = aptitud;
        this.alta = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rol getEstadoTrabajo() {
        return estadoTrabajo;
    }

    public void setEstadoTrabajo(Rol estadoTrabajo) {
        this.estadoTrabajo = estadoTrabajo;
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

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }
    

    
}
