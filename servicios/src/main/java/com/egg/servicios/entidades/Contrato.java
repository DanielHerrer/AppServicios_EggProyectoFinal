package com.egg.servicios.entidades;

import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.enumeraciones.Rol;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author joaquin
 */
@Entity
public class Contrato {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Estados estado_trabajo;
    private Oferta oferta;
    private Calificacion aptitud;
    private boolean alta;

    public Contrato() {
        this.alta = true;
    }

    public Contrato(String id, Estados estado_trabajo, Oferta oferta, Calificacion aptitud) {
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

    public Estados getEstado_trabajo() {
        return estado_trabajo;
    }

    public void setEstado_trabajo(Estados estado_trabajo) {
        this.estado_trabajo = estado_trabajo;
    }
    

    
}
