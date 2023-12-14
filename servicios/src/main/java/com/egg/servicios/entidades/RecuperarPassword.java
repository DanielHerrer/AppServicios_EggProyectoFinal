package com.egg.servicios.entidades;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @Martin
 */
@Data
@Builder
public class RecuperarPassword {

    private String emailUser;
    private String message;
    private String code;

}
