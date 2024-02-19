package utils;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import entidades.Actor;
import entidades.Pelicula;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccesoBD {

    private static AccesoBD me=null;/*Tenemos una referencia estática a la propia clase para invocarla desde otras
    partes del proyecto sin necesidad de crearla continuamente*/

    private AccesoBD(){//Constructor

    }

    public static AccesoBD getInstance(){//Con el método getInstance obtenemos la referencia estática de esta clase
        if (me == null) {
            synchronized(AccesoBD.class){
                if(me==null){
                    me = new AccesoBD();
                }
            }
        }
        return me;
    }

    public MongoClient conectar(){
        MongoClient cliente= MongoClients.create();
        return cliente;
    }

    public void desconectar(MongoClient cliente){
        cliente.close();
    }

    public List<Pelicula> getPeliculas(){
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        FindIterable<Document> findIterable = db.getCollection("pelicula").find();
        Iterator<Document> iter = findIterable.iterator();
        List<Pelicula> peliculas = new ArrayList<>();
        while(iter.hasNext()){
            Document dPelicula=iter.next();
            Pelicula pelicula=new Pelicula();
            pelicula.set_id(dPelicula.getObjectId("_id"));
            pelicula.setNombre(dPelicula.getString("nombre"));
            pelicula.setGenero(dPelicula.getString("genero"));
            pelicula.setFecha(dPelicula.getString("fecha"));
            pelicula.setSinopsis(dPelicula.getString("sinopsis"));
            peliculas.add(pelicula);
        }
        desconectar(cliente);
        return peliculas;
    }

    public List<Actor> getActores(){
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        FindIterable<Document> findIterable = db.getCollection("actor").find();
        Iterator<Document> iter = findIterable.iterator();
        List<Actor> actores = new ArrayList<>();
        while(iter.hasNext()){
            Document dActor=iter.next();
            Actor actor=new Actor();
            actor.set_id(dActor.getObjectId("_id"));
            actor.setNombre(dActor.getString("nombre"));
            actor.setIdpelicula(dActor.getObjectId("idpelicula"));
            actores.add(actor);
        }
        desconectar(cliente);
        return actores;
    }

    public void eliminarPelicula(ObjectId _id){//Igualmente, la eliminación de un documento no varía con respecto a lo visto en los ejemplos
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        Document docBusqueda = new Document("_id", _id);
        db.getCollection("pelicula").deleteOne(docBusqueda);
        desconectar(cliente);
    }

}
