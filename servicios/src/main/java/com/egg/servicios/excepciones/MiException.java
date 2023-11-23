package com.egg.servicios.excepciones;

/**
 *
 * @author Nico
 */
public class MiException extends Exception{
    //se genera esta clase para aislar las exepciones propias del negocio a los generales
    public MiException(String msg) {
        super(msg);
    }

}