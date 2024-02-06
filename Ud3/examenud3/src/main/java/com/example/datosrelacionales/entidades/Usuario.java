package com.example.datosrelacionales.entidades;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombre;
    private String apellidos;
    private int edad;
    private int sexo;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Publicacion> publicaciones = new HashSet<>();
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Publicacion> comentarios = new HashSet<>();

    public Usuario() {}

    public Usuario(Long idUsuario, String nombre, String apellidos, int edad, int sexo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sexo = sexo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public Set<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(Set<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public Set<Publicacion> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Publicacion> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", sexo=" + sexo +
                '}';
    }
}
