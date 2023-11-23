package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Imagen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{

}
