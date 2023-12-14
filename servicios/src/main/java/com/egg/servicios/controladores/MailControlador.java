package com.egg.servicios.controladores;

import com.egg.servicios.servicios.MailServicio;
import com.egg.servicios.entidades.RecuperarPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Martin
 */
@RestController
@RequestMapping("/email")
public class MailControlador {
    
    @Autowired
    private MailServicio mailServicio;

    @PostMapping("/enviado")
    public ResponseEntity<Object> autenticar(@RequestBody RecuperarPassword recuperarPassword){
        
        String valor = mailServicio.generarNumeroAleatorio();
        
        recuperarPassword.setCode(valor);
        
        mailServicio.sendMessageUser(recuperarPassword.getEmailUser(), recuperarPassword.getCode());
        
        return ResponseEntity.ok().body("Mensaje enviado con éxito");
    }
    
}
