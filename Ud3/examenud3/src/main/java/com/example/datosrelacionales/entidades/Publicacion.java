package com.example.datosrelacionales.entidades;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacion;
    private String nombre;
    private String texto;
    private int tipo;
    @ManyToOne
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    private Set<Comentario> comentarios = new HashSet<>();

    public Publicacion() {}

    public Publicacion(Long idPublicacion, String nombre, String texto, int tipo, Usuario usuario) {
        this.idPublicacion = idPublicacion;
        this.nombre = nombre;
        this.texto = texto;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public long getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(long idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "idPublicacion=" + idPublicacion +
                ", nombre='" + nombre + '\'' +
                ", texto='" + texto + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
