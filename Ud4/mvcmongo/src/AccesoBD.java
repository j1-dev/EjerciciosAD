import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
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

    public List<Persona> getPersonas(){/*Listar las personas es igual que como ya hemos visto en los ejemplos. Como la colección personas
    aún no existe, Mongo la creará automáticamente*/
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        FindIterable<Document> findIterable = db.getCollection("persona").find();
        Iterator<Document> iter = findIterable.iterator();
        List<Persona> personas = new ArrayList<>();
        while(iter.hasNext()){
            Document dPersona=iter.next();
            Persona persona=new Persona();
            persona.set_id(dPersona.getObjectId("_id"));
            persona.setNombre(dPersona.getString("nombre"));
            persona.setDNI(dPersona.getString("dni"));
            personas.add(persona);
        }
        desconectar(cliente);
        return personas;
    }

    public void insertarPersona(Persona persona){//La inserción no varía con respecto a lo visto en los ejemplos
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        Document dPersona=new Document()
                .append("nombre",persona.getNombre())
                .append("dni",persona.getDNI());
        db.getCollection("persona").insertOne(dPersona);
        desconectar(cliente);
    }

    public void modificarPersona(Persona persona){//La modificación se hace reemplazando el objeto persona por completo
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        Document docBusqueda = new Document("_id", persona.get_id());
        Document docReemplazo = new Document()
                .append("nombre",persona.getNombre())
                .append("dni",persona.getDNI());
        db.getCollection("persona").replaceOne(docBusqueda,docReemplazo);
        desconectar(cliente);
    }

    public void eliminarPersona(ObjectId _id){//Igualmente, la eliminación de un documento no varía con respecto a lo visto en los ejemplos
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        Document docBusqueda = new Document("_id", _id);
        db.getCollection("persona").deleteOne(docBusqueda);
        desconectar(cliente);
    }

}
