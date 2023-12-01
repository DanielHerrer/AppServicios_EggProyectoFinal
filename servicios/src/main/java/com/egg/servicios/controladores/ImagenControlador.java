package com.egg.servicios.controladores;


import com.egg.servicios.entidades.Servicio;
import com.egg.servicios.entidades.Usuario;
import com.egg.servicios.servicios.ServicioServicio;
import com.egg.servicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
        
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ServicioServicio servicioServicio;
    
    @GetMapping("/perfil/{id}")//se pasa el id del usuario a travez del path
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {//recibe el id del usuario al que esta vinculada la imagen.
       Usuario usuario = usuarioServicio.getOne(id);//trae al usuario por id y lo asignamos a una variable usuario
       
      byte[] imagen = usuario.getImagen().getContenido();//traer la imagen, y de la imagen el contenido, el cual vamos a guardar en un arreglo de bytes.
      HttpHeaders headers = new HttpHeaders();//estas cabeceras le indican al navegador que estan devolviendo una imagen
      
      headers.setContentType(MediaType.IMAGE_JPEG);//SETEAMOS EN EL HEADERS LA IMAGEN, avisa que va a guardar una imagen

      return new ResponseEntity < >(imagen,headers,HttpStatus.OK);//httpstatus ok para que la operacion este confirmada
    }

    @GetMapping("/matricula/{id}")
    public ResponseEntity<byte[]> imagenMatricula(@PathVariable String id) {

        Servicio servicio = servicioServicio.listarPorId(id);

        byte[] imagen = servicio.getMatricula().getContenido();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity < >(imagen,headers,HttpStatus.OK);
    }
    
}