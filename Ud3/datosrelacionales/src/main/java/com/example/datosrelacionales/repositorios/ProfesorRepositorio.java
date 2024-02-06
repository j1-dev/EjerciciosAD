package com.example.datosrelacionales.repositorios;

import com.example.datosrelacionales.entidades.Profesor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfesorRepositorio extends CrudRepository<Profesor,Long> {
    List<Profesor> findProfesorByTiempoServicio(Integer tiempoServicio);
    List<Profesor> findAllByNombre(String nombre);

    /*Recordemos que en nuestra clase de entidad Parte usamos una variable de la clase Profesor como clava foránea.
    * Esto nos permite acceder al atributo nombre de la clase profesor únicamente con la referencia que existe a
    * dicha clase en la clase de entidad Parte (fijaos que en la consulta SQL no necesitamos mencionar la tabla
    * Profesor para hacer uso de sus campos.
    *
    * Usamos la anotación Param especificando el nombre del parámetro en la consulta para introducir parámetros en SQL*/
    @Query("SELECT DISTINCT p.profesor.nombre FROM Parte p WHERE p.texto = :texto")
    List<String> encontrarProfesoresPorTextoDeParte(@Param("texto") String texto);

    //Las etiquetas transactional y modifying se usan para evitar problemas de permisos usando consultas nativas
    @Transactional
    @Modifying
    @Query(value = "UPDATE profesor SET tiempo_servicio = :nuevoValor WHERE nombre = :condicion", nativeQuery = true)
    int actualizarPorCondicion(@Param("nuevoValor") Integer nuevoValor, @Param("condicion") String condicion);
}
