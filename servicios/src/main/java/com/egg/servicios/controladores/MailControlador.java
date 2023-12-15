package com.egg.servicios.controladores;

import com.egg.servicios.servicios.MailServicio;
import com.egg.servicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Martin
 */
@Controller
@RequestMapping("/email")
public class MailControlador {

    @Autowired
    private MailServicio mailServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/email")
    public String recuperarPassword() {

        return "test_recuperar_password.html";
    }

    @PostMapping("/enviado")
    public String autenticar(@RequestParam String email) {

        String valor = mailServicio.generarNumeroAleatorio();
        usuarioServicio.actualizarPassword(email, valor);
        mailServicio.sendMessageUser(email, valor);

        return "login.html";
    }

}
