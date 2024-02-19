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

    private static AccesoBD me=null;

    private AccesoBD() {}

    public static AccesoBD getInstance(){
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

    public List<Alumno> getAlumnos(){
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        FindIterable<Document> findIterable = db.getCollection("alumno").find();
        Iterator<Document> iter = findIterable.iterator();
        List<Alumno> alumnos = new ArrayList<>();
        while(iter.hasNext()){
            Document dAlumno=iter.next();
            Alumno alumno=new Alumno();
            alumno.set_id(dAlumno.getObjectId("_id"));
            alumno.setNombre(dAlumno.getString("nombre"));
            alumno.setApellidos(dAlumno.getString("apellidos"));
            alumno.setfNacimiento(dAlumno.getString("fNacimiento"));
            alumno.setCiclo(dAlumno.getInteger("ciclo"));
            alumnos.add(alumno);
        }
        desconectar(cliente);
        return alumnos;
    }

    public void insertarAlumno(Alumno alumno){
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        Document dAlumno=new Document()
                .append("nombre",alumno.getNombre())
                .append("apellidos",alumno.getApellidos())
                .append("fNacimiento",alumno.getfNacimiento())
                .append("ciclo",alumno.getCiclo());
        db.getCollection("alumno").insertOne(dAlumno);
        desconectar(cliente);
    }

    public void modificarAlumno(Alumno alumno){
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        Document docBusqueda = new Document("_id", alumno.get_id());
        Document docReemplazo = new Document()
                .append("nombre",alumno.getNombre())
                .append("apellidos",alumno.getApellidos())
                .append("fNacimiento",alumno.getfNacimiento())
                .append("ciclo",alumno.getCiclo());
        db.getCollection("alumno").replaceOne(docBusqueda,docReemplazo);
        desconectar(cliente);
    }

    public void eliminarAlumno(ObjectId _id){
        MongoClient cliente=conectar();
        MongoDatabase db = cliente.getDatabase("ejemplostema4");
        Document docBusqueda = new Document("_id", _id);
        db.getCollection("alumno").deleteOne(docBusqueda);
        desconectar(cliente);
    }

}
