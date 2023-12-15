package com.egg.servicios.servicios;

import com.egg.servicios.repositorios.ImagenRepositorio;
import com.egg.servicios.entidades.Imagen;
import com.egg.servicios.excepciones.MiException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nico
 */
@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardar(MultipartFile archivo) throws MiException {
        
        try {

            if (archivo == null || archivo.isEmpty()) { //(if archivo == null || archivo.isEmpty())

                Imagen imagen = new Imagen();

                // Cargar imagen predeterminada desde la carpeta resources/static/img/
                ClassPathResource defaultImageResource = new ClassPathResource("static/img/default.jpeg");
                byte[] defaultImageBytes = StreamUtils.copyToByteArray(defaultImageResource.getInputStream());

                imagen.setMime("image/jpeg");  // Establecer el tipo MIME de la imagen predeterminada
                imagen.setNombre("default.jpeg");  // Nombre de la imagen predeterminada
                imagen.setContenido(defaultImageBytes);

                return imagenRepositorio.save(imagen);

            } else if (archivo != null) {
//                
                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return (Imagen) imagenRepositorio.save(imagen);
            }

        } catch (Exception e) {

            throw new MiException(e.getMessage());
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return (Imagen) imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }

    @Transactional(readOnly = true)
    public List<Imagen> listarTodos() {
        return imagenRepositorio.findAll();
    }
}
