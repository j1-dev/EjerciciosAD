package com.example.datosrelacionales.repositorios;

import com.example.datosrelacionales.entidades.Comentario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ComentarioRepositorio extends CrudRepository<Comentario, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comentario WHERE id_usuario = :userId", nativeQuery = true)
    void deleteByUsuarioId(@Param("userId") Long userId);
}
