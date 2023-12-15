package com.egg.servicios.servicios;

import com.egg.servicios.infraestructura.MailManager;
import org.springframework.stereotype.Service;

/**
 *
 * @Martin
 */
@Service
public class MailServicio {

    MailManager mailManager;

    public MailServicio(MailManager mailManager) {
        this.mailManager = mailManager;
    }

    public void sendMessageUser(String email, String code) {
        mailManager.sendMessage(email, code);
    }

    public String generarNumeroAleatorio() {
        // Generar un número aleatorio de 6 dígitos
        int numeroAleatorio = (int) (Math.random() * 900000) + 100000;

        // Convertir el número a cadena (String)
        return String.valueOf(numeroAleatorio);
    }

}
