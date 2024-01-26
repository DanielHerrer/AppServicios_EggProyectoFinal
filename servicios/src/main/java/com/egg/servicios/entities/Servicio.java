package com.egg.servicios.entities;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String descripcion;
    private Double honorariosHora;
    @OneToOne
    private Imagen matricula;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Usuario proveedor;
    private LocalDateTime fecha;
    private Boolean alta;

    public Servicio() {
        fecha = LocalDateTime.now();
        alta = true;
    }

    public Servicio(String descripcion, Double honorariosHora, Imagen matricula, Categoria categoria, Usuario proveedor, LocalDateTime fecha) {
        this.descripcion = descripcion;
        this.honorariosHora = honorariosHora;
        this.matricula = matricula;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.fecha = fecha;
        alta = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getHonorariosHora() {
        return honorariosHora;
    }

    public void setHonorariosPorHora(Double honorariosHora) {
        this.honorariosHora = honorariosHora;
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

    public void setHonorariosHora(Double honorariosHora) {
        this.honorariosHora = honorariosHora;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

}