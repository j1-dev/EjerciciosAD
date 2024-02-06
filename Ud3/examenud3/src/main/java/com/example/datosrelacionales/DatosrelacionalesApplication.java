package com.example.datosrelacionales;

import com.example.datosrelacionales.repositorios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatosrelacionalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatosrelacionalesApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UsuarioRepositorio usuarioRepositorio, PublicacionRepositorio publicacionRepositorio,
                                 ComentarioRepositorio comentarioRepositorio) {
        return (args) -> {
            Ejemplos ejemplos=new Ejemplos();
            // ejemplos.insertarUsuarios(usuarioRepositorio);
            // ejemplos.insertarPublicaciones(usuarioRepositorio, publicacionRepositorio);
            // ejemplos.insertarComentarios(usuarioRepositorio, publicacionRepositorio, comentarioRepositorio);
            // ejemplos.actualizarUsuario(usuarioRepositorio);
            // ejemplos.actualizarPublicacion(publicacionRepositorio);
            // ejemplos.listarTodo(usuarioRepositorio, publicacionRepositorio, comentarioRepositorio);
            // ejemplos.mostrarInformacionUsuario(usuarioRepositorio);
            // ejemplos.listarPublicacionesTipo2(publicacionRepositorio);
            // ejemplos.listarComentariosHector(comentarioRepositorio, usuarioRepositorio);
            // ejemplos.listarPublicacionesUsuarioEdad21(publicacionRepositorio);
            // ejemplos.cambiarTextoPublicacionesTipo1(publicacionRepositorio);
             ejemplos.eliminarComentariosUsuarioHector(comentarioRepositorio);
        };
    }

}
