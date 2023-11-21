package com.egg.servicios.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Categoria {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name= "uuid", strategy = "uuid2")
    private String ID_categorio;
    
    private String nombre;
    private boolean alta;

    public Categoria() {
    }

    public String getID_categorio() {
        return ID_categorio;
    }

    public void setID_categorio(String ID_categorio) {
        this.ID_categorio = ID_categorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }   
    
}
