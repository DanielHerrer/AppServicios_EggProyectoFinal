
package com.egg.servicios.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Calificacion {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy ="uuid")
    private String id_calificacion;  
    private String comentario;
    private Integer puntuacion;
    private Boolean alta;
}
