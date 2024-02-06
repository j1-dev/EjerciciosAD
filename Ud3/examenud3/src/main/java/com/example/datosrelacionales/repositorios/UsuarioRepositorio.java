package com.example.datosrelacionales.repositorios;

import com.example.datosrelacionales.entidades.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepositorio extends CrudRepository<Usuario,Long> {

}
