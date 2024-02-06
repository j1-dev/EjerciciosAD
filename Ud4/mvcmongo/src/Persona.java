import org.bson.types.ObjectId;

public class Persona {

    private ObjectId _id;
    private String DNI;
    private String nombre;

    public Persona() {

    }

    public Persona(ObjectId _id, String DNI, String nombre) {
        this._id = _id;
        this.DNI = DNI;
        this.nombre = nombre;
    }

    public Persona(String DNI, String nombre) {
        this.DNI = DNI;
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
