/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.controladores;

import com.egg.servicios.entidades.Calificacion;
import com.egg.servicios.entidades.Oferta;
import com.egg.servicios.enumeraciones.Estados;
import com.egg.servicios.excepciones.MiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.servicios.servicios.ContratoServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
/**
 *
 * @author joaquin
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {
  
  @Autowired
  private ContratoServicios contrato;
    

  @GetMapping("/listar-contrato") 
  public String guardarContrato(Estados estados, Oferta oferta, Calificacion aptitud,ModelMap modelo) {
  
      try{
     
          contrato.guardarContrato(estados, oferta, aptitud);  
          
          return "/contrato.html";
        }catch (MiException e) {           
            modelo.put("error", e.getMessage());    
            return "inicio.html";
        }
          
    }
      
}
