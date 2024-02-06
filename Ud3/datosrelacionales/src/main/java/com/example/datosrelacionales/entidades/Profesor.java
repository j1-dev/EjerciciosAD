package com.example.datosrelacionales.entidades;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity/*Usamos esta anotación para indicar a nuestro ORM que la clase que estamos creando se va a corresponder
con una tabla en nuestra base de datos*/
public class Profesor {

    @Id/*La etiqueta Id se usa para indicar que la variable que hay debajo será la clave primaria de la tabla que
    se cree. Con GeneratedValue indicamos que esta clave primaria es autoincremental*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfesor;
    private String nombre;
    private Integer tiempoServicio;
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private Set<Parte> partes= new HashSet<>();

    public Profesor() {

    }

    public Profesor(Long idProfesor, String nombre, Integer tiempoServicio) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.tiempoServicio = tiempoServicio;
    }

    public Long getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Long idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTiempoServicio() {
        return tiempoServicio;
    }

    public void setTiempoServicio(Integer tiempoServicio) {
        this.tiempoServicio = tiempoServicio;
    }

    public Set<Parte> getPartes() {
        return partes;
    }

    public void setPartes(Set<Parte> partes) {
        this.partes = partes;
    }
}
