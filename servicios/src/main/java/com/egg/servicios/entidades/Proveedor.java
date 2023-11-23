/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author joaquin
 */
@Entity
public class Proveedor {
   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name ="uuid", strategy = "uuid")
   private String id;
   
  
}
