package com.egg.servicios.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_servicio;

    private String descripcion;

    @OneToOne
    private Imagen matricula;

    private Categoria categoria;

    @ManyToOne
    private Usuario proveedor;

    private Boolean activo;

    public Servicio() {
        this.activo = true;
    }

    public Servicio(String descripcion, Imagen matricula, Categoria categoria, Usuario proveedor) {
        this.descripcion = descripcion;
        this.matricula = matricula;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.activo = true;
    }

    public String getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(String id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Imagen getMatricula() {
        return matricula;
    }

    public void setMatricula(Imagen matricula) {
        this.matricula = matricula;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getProveedor() {
        return proveedor;
    }

    public void setProveedor(Usuario proveedor) {
        this.proveedor = proveedor;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}