/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.entidades;

import com.egg.servicios.enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Nico
 */
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String accUsuario;
    private String password;
    private String nombre;
    
    @OneToOne
    private Imagen imagen;
    private String email;
    private String ubicacion;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    private boolean alta;

    public Usuario() {
    }

    public Usuario(String id, String accUsuario, String password, String nombre, Imagen imagen, String email, String ubicacion, Rol rol, boolean alta) {
        this.id = id;
        this.accUsuario = accUsuario;
        this.password = password;
        this.nombre = nombre;
        this.imagen = imagen;
        this.email = email;
        this.ubicacion = ubicacion;
        this.rol = rol;
        this.alta = alta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccUsuario() {
        return accUsuario;
    }

    public void setAccUsuario(String accUsuario) {
        this.accUsuario = accUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

}
