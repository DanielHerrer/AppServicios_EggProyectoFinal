/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.egg.servicios.repositorios;

import com.egg.servicios.entidades.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author joaquin
 */
@Repository
public interface ContratoRepositorios extends JpaRepository<Contrato,String> {
    
     @Query("SELECT c FROM Contrato c WHERE c.id = :id")      
    public Contrato listarContratoPorId(@Param("id")String id);

}
