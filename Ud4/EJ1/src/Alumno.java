import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;

public class Alumno implements Serializable {
    private ObjectId _id;
    private String apellidos;
    private String nombre;
    private String fNacimiento;
    private int ciclo;

    public Alumno() {
    }

    public Alumno(String nombre, String apellidos, String fNacimiento, int ciclo) {
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.fNacimiento = fNacimiento;
        this.ciclo = ciclo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(String fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
