package com.example.datosrelacionales.repositorios;

import com.example.datosrelacionales.entidades.Alumno;
import org.springframework.data.repository.CrudRepository;

public interface AlumnoRepositorio extends CrudRepository<Alumno,Long> /*Extendemos el repositorio CRUD por defecto de
Spring Data indicando la entidad a la que vamos a acceder y el tipo de dato de la clave primaria de esta entidad*/
{

}
