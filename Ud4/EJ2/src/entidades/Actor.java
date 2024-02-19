package entidades;

import org.bson.types.ObjectId;

public class Actor {
    private ObjectId _id;
    private String nombre;
    private ObjectId idpelicula;

    public Actor() {}

    public Actor(ObjectId _id, String nombre, ObjectId idpelicula) {
        this._id = _id;
        this.nombre = nombre;
        this.idpelicula = idpelicula;
    }

    public Actor( String nombre, ObjectId idpelicula) {
        this.nombre = nombre;
        this.idpelicula = idpelicula;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ObjectId getIdpelicula() {
        return idpelicula;
    }

    public void setIdpelicula(ObjectId idpelicula) {
        this.idpelicula = idpelicula;
    }
}
