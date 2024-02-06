package com.example.datosrelacionales.entidades;

import jakarta.persistence.*;

@Entity
public class Parte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParte;
    private String texto;
    @ManyToOne
    @JoinColumn(name="idProfesor")
    private Profesor profesor;
    @ManyToOne
    @JoinColumn(name="idAlumno")
    private Alumno alumno;

    public Parte() {

    }

    public Parte(Long idParte, String texto, Profesor profesor, Alumno alumno) {
        this.idParte = idParte;
        this.texto = texto;
        this.profesor = profesor;
        this.alumno = alumno;
    }

    public Long getIdParte() {
        return idParte;
    }

    public void setIdParte(Long idParte) {
        this.idParte = idParte;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

}
