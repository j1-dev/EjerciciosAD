package com.example.datosrelacionales;

import com.example.datosrelacionales.repositorios.AlumnoRepositorio;
import com.example.datosrelacionales.repositorios.LibroRepositorio;
import com.example.datosrelacionales.repositorios.ParteRepositorio;
import com.example.datosrelacionales.repositorios.ProfesorRepositorio;
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
    public CommandLineRunner run(AlumnoRepositorio alumnoRepositorio, ProfesorRepositorio profesorRepositorio,
                                 LibroRepositorio libroRepositorio, ParteRepositorio parteRepositorio) {
        return (args) -> {
            Ejemplos ejemplos=new Ejemplos();
            ejemplos.pruebaInsercion(alumnoRepositorio, profesorRepositorio, libroRepositorio, parteRepositorio);
            //ejemplos.pruebaActualizacion(profesorRepositorio);
            //ejemplos.pruebaTodosProfesores(profesorRepositorio);
            //ejemplos.pruebaUnProfesor(profesorRepositorio);
            //ejemplos.pruebaProfesorParametro(profesorRepositorio);
            //ejemplos.pruebaDosTablas(profesorRepositorio);
            //ejemplos.pruebaModificacionSQL(profesorRepositorio);
            //ejemplos.pruebaEliminar(profesorRepositorio);
        };
    }

}
