package com.example.datosrelacionales.repositorios;

import com.example.datosrelacionales.entidades.Publicacion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PublicacionRepositorio extends CrudRepository<Publicacion,Long> {
    List<Publicacion> findByUsuario_Edad(@Param("edad") int edad);

    @Transactional
    @Modifying
    @Query(value = "UPDATE publicacion SET texto = 'No sé qué escribir' WHERE tipo = 1", nativeQuery = true)
    void cambiarTextoPublicacionesTipo1();
}
