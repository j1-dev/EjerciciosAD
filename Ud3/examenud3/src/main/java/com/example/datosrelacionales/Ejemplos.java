package com.example.datosrelacionales;

import com.example.datosrelacionales.entidades.*;
import com.example.datosrelacionales.repositorios.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Ejemplos {
    private Long ID_HECTOR = 15L;
    private Long ID_MARIA = 16L;

    private Long ID_PUB1 = 1L;
    private Long ID_PUB2 = 2L;
    private Long ID_PUB3 = 3L;

    private Long ID_COM1 = 1L;
    private Long ID_COM2 = 2L;
    private Long ID_COM3 = 3L;
    private Long Id_COM4 = 4L;

    public Ejemplos() {

    }

    public void insertarUsuarios(UsuarioRepositorio ur) {
        Usuario u1 = new Usuario(null, "Hector", "Rodriguez Perez", 25, 1);
        Usuario u2 = new Usuario(null, "Maria", "Sanchez Polvorinos", 21, 2);

        ur.save(u1);
        ur.save(u2);
    }

    public void insertarPublicaciones(UsuarioRepositorio ur,PublicacionRepositorio pr) {
        Optional<Usuario> hectorOp = ur.findById(ID_HECTOR);
        Optional<Usuario> mariaOp = ur.findById(ID_MARIA);

        Usuario hector = null;
        Usuario maria = null;

        if(hectorOp.isPresent()) {
            hector = hectorOp.get();
        }
        if(mariaOp.isPresent()) {
            maria = mariaOp.get();
        }

        if(hector != null && maria != null) {
            Publicacion p1 = new Publicacion(null, "Los claveles", "Estas flores fueron un símbolo de revolución en el siglo pasado", 1, hector);
            Publicacion p2 = new Publicacion(null, "Contencion", "Es difícil encontrar el equilibrio entre ser una persona moderada y no dejar que te pisoteen", 2, hector);
            Publicacion p3 = new Publicacion(null, "La vida", "Disfruta de los momentos buenos, aprende de los momentos malos y no dejes que nadie te amargue", 1, maria);

            pr.save(p1);
            pr.save(p2);
            pr.save(p3);
        }
    }

    public void insertarComentarios(UsuarioRepositorio ur, PublicacionRepositorio pr, ComentarioRepositorio cr) {
        Optional<Usuario> hectorOp = ur.findById(ID_HECTOR);
        Optional<Usuario> mariaOp = ur.findById(ID_MARIA);
        Optional<Publicacion> publicacionLosClavelesOp = pr.findById(ID_PUB1);
        Optional<Publicacion> publicacionContencionOp = pr.findById(ID_PUB2);
        Optional<Publicacion> publicacionLaVidaOp = pr.findById(ID_PUB3);

        Usuario hector = hectorOp.orElse(null);
        Usuario maria = mariaOp.orElse(null);
        Publicacion publicacionLosClaveles = publicacionLosClavelesOp.orElse(null);
        Publicacion publicacionContencion = publicacionContencionOp.orElse(null);
        Publicacion publicacionLaVida = publicacionLaVidaOp.orElse(null);

        if (hector != null && maria != null && publicacionLosClaveles != null && publicacionContencion != null && publicacionLaVida != null) {
            Comentario c1 = new Comentario(null, "¿En qué país fue la revolución de los claveles?", maria, publicacionLosClaveles);
            Comentario c2 = new Comentario(null, "En Portugal.", hector, publicacionLosClaveles);
            Comentario c3 = new Comentario(null, "Mantente firme, amigo", maria, publicacionContencion);
            Comentario c4 = new Comentario(null, "Sabias palabras.", hector, publicacionLaVida);

            cr.save(c1);
            cr.save(c2);
            cr.save(c3);
            cr.save(c4);
        }
    }

    public void actualizarUsuario(UsuarioRepositorio ur) {
        Optional<Usuario> hectorOp = ur.findById(ID_HECTOR);

        if (hectorOp.isPresent()) {
            Usuario hector = hectorOp.get();
            hector.setEdad(26);
            ur.save(hector);
        }
    }

    public void actualizarPublicacion(PublicacionRepositorio pr) {
        Optional<Publicacion> publicacionLaVidaOp = pr.findById(ID_PUB3);

        if (publicacionLaVidaOp.isPresent()) {
            Publicacion publicacionLaVida = publicacionLaVidaOp.get();
            publicacionLaVida.setTipo(2);
            pr.save(publicacionLaVida);
        }
    }

    public void listarTodo(UsuarioRepositorio ur, PublicacionRepositorio pr, ComentarioRepositorio cr) {
        Iterable<Usuario> usIterable = ur.findAll();
        Iterable<Publicacion> pbIterable = pr.findAll();
        Iterable<Comentario> cmIterable = cr.findAll();

        System.out.println("***Usuarios***");
        Iterator<Usuario> usIterator = usIterable.iterator();
        while(usIterator.hasNext()) {
            Usuario aux = usIterator.next();
            System.out.println(aux.toString());
        }

        System.out.println("***Publicaciones***");
        Iterator<Publicacion> pbIterator = pbIterable.iterator();
        while(pbIterator.hasNext()) {
            Publicacion aux = pbIterator.next();
            System.out.println(aux.toString());
        }

        System.out.println("***Comentarios***");
        Iterator<Comentario> cmIterator = cmIterable.iterator();
        while(cmIterator.hasNext()) {
            Comentario aux = cmIterator.next();
            System.out.println(aux.toString());
        }
    }

    public void mostrarInformacionUsuario(UsuarioRepositorio ur) {
        Optional<Usuario> mariaOp = ur.findById(ID_MARIA);

        if(mariaOp.isPresent()) {
            Usuario maria = mariaOp.get();
            System.out.println("Información de María:");
            System.out.println(maria);
        }
    }

    public void listarPublicacionesTipo2(PublicacionRepositorio pr) {
        Iterable<Publicacion> iterable = pr.findAll();
        Iterator<Publicacion> iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Publicacion aux = iterator.next();
            if(aux.getTipo() == 2) {
                System.out.println(aux.toString());
            }
        }
    }

    public void listarComentariosHector(ComentarioRepositorio cr, UsuarioRepositorio ur) {
        Iterable<Comentario> iterable = cr.findAll();
        Iterator<Comentario> iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Comentario aux = iterator.next();
            Usuario user = aux.getUsuario();
            if(user.getIdUsuario().equals(ID_HECTOR)) {
                System.out.println(aux);
            }
        }
    }

    public void listarPublicacionesUsuarioEdad21(PublicacionRepositorio pr) {
        List<Publicacion> publicacionesUsuarioEdad21 = pr.findByUsuario_Edad(21);

        publicacionesUsuarioEdad21.forEach(System.out::println);
    }

    public void cambiarTextoPublicacionesTipo1(PublicacionRepositorio pr) {
        pr.cambiarTextoPublicacionesTipo1();
    }

    public void eliminarComentariosUsuarioHector(ComentarioRepositorio cr) {
        cr.deleteByUsuarioId(ID_HECTOR);
    }

    public void borrarTodo(UsuarioRepositorio ur, PublicacionRepositorio pr, ComentarioRepositorio cr) {
        ur.deleteAll();
        pr.deleteAll();
        cr.deleteAll();
    }
}
