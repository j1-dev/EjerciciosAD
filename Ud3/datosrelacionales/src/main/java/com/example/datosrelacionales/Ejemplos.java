package com.example.datosrelacionales;

import com.example.datosrelacionales.entidades.Alumno;
import com.example.datosrelacionales.entidades.Libro;
import com.example.datosrelacionales.entidades.Parte;
import com.example.datosrelacionales.entidades.Profesor;
import com.example.datosrelacionales.repositorios.AlumnoRepositorio;
import com.example.datosrelacionales.repositorios.LibroRepositorio;
import com.example.datosrelacionales.repositorios.ParteRepositorio;
import com.example.datosrelacionales.repositorios.ProfesorRepositorio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Ejemplos {

    public Ejemplos() {

    }

    public void pruebaInsercion(AlumnoRepositorio alumnoRepositorio, ProfesorRepositorio profesorRepositorio,
                                LibroRepositorio libroRepositorio, ParteRepositorio parteRepositorio){
        /*Vamos a insertar un objeto de cada clase en la base de datos*/
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date fechaN1= null;
        try {
            fechaN1 = sdf.parse("2004-12-24");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Alumno alumno=new Alumno(null,"Juan","Moreno Márquez",fechaN1,"34189406K");
        alumno=alumnoRepositorio.save(alumno);/*Usando los repositorios, el método save inserta nuevas entidades en
        la base de datos siempre que la clave primaria proporcionada sea nula. Si se proporciona una clave primaria
        que coincida con datos de la tabla, actualizará esta información en lugar de realizar una nueva inserción*/
        Profesor profesor=new Profesor(null,"Gerardo",6);
        profesor=profesorRepositorio.save(profesor);
        Libro libro=new Libro(null,"El Silmarillion",alumno);
        libroRepositorio.save(libro);
        Parte parte=new Parte(null,"No para de hablar",profesor,alumno);
        parteRepositorio.save(parte);
    }

    public void pruebaActualizacion(ProfesorRepositorio profesorRepositorio){
        Profesor profesorbis=new Profesor(null,"Gopal",5);
        profesorbis=profesorRepositorio.save(profesorbis);
        profesorbis.setTiempoServicio(6);
        profesorRepositorio.save(profesorbis);
    }

    public void pruebaTodosProfesores(ProfesorRepositorio profesorRepositorio){
        //La consulta de toda la información de una tabla devuelve por defecto un objeto de la clase Iterable
        Iterable<Profesor> profesoresIterables= profesorRepositorio.findAll();
        Iterator<Profesor> iterus=profesoresIterables.iterator();
        while(iterus.hasNext()){
            Profesor teacher=iterus.next();
            System.out.println("Nombre del profesor: "+teacher.getNombre());
        }
    }

    public void pruebaUnProfesor(ProfesorRepositorio profesorRepositorio){
        /*Cuando usamos la consulta del repositorio por defecto para obtener un único elemento, nos devuelve un objeto
        * de la clase Optional*/
        Optional<Profesor> optionalProfesor=profesorRepositorio.findById(1L);
        if(optionalProfesor.isPresent()){
            Profesor gopal=optionalProfesor.get();
            System.out.println("Nombre del profesor localizado por id: "+gopal.getNombre());
        }
    }

    public void pruebaProfesorParametro(ProfesorRepositorio profesorRepositorio){
        List<Profesor> listaProfesores= profesorRepositorio.findProfesorByTiempoServicio(6);
        for(Profesor profesor : listaProfesores) {
            System.out.println("Profesor con 6 años de tiempo de servicio: "+profesor.getNombre());
        }
    }

    public void pruebaDosTablas(ProfesorRepositorio profesorRepositorio){
        List<String> listaNombres= profesorRepositorio.encontrarProfesoresPorTextoDeParte("No para de hablar");
        for(String nombre : listaNombres) {
            System.out.println("Profesor que ha puesto el parte \"No para de hablar\": "+nombre);
        }
    }

    public void pruebaModificacionSQL(ProfesorRepositorio profesorRepositorio){
        profesorRepositorio.actualizarPorCondicion(10,"Gopal");
    }

    public void pruebaEliminar(ProfesorRepositorio profesorRepositorio){
        Optional<Profesor> optionalProfesor=profesorRepositorio.findById(1L);//Comprobar el ID
        if(optionalProfesor.isPresent()){
            Profesor gerardo=optionalProfesor.get();
            profesorRepositorio.delete(gerardo);
        }
    }

}
