/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

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