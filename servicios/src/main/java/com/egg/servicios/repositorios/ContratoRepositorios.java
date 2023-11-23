package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Contrato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author joaquin
 */
@Repository
public interface ContratoRepositorios extends JpaRepository<Contrato,String> {

   
}
