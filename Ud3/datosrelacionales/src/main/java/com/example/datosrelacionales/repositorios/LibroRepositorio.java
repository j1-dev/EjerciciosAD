package com.example.datosrelacionales.repositorios;

import com.example.datosrelacionales.entidades.Libro;
import org.springframework.data.repository.CrudRepository;

public interface LibroRepositorio extends CrudRepository<Libro,Long> {
}
