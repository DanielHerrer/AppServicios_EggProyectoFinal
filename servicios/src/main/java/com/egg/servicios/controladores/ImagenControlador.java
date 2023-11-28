/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.servicios.controladores;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Nico
 */
public class ImagenControlador {
        
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/perfil/{id}")//se pasa el id del usuario a travez del path
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {//recibe el id del usuario al que esta vinculada la imagen.
       Usuario usuario = usuarioServicio.getOne(id);//trae al usuario por id y lo asignamos a una variable usuario
       
      byte[] imagen = usuario.getImagen().getContenido();//traer la imagen, y de la imagen el contenido, el cual vamos a guardar en un arreglo de bytes.
      HttpHeaders headers = new HttpHeaders();//estas cabeceras le indican al navegador que estan devolviendo una imagen
      
      headers.setContentType(MediaType.IMAGE_JPEG);//SETEAMOS EN EL HEADERS LA IMAGEN, avisa que va a guardar una imagen

      return new ResponseEntity < >(imagen,headers,HttpStatus.OK);//httpstatus ok para que la operacion este confirmada
}
    
}
