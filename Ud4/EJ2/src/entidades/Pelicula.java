package entidades;

import org.bson.types.ObjectId;

public class Pelicula {
    private ObjectId _id;
    private String nombre;
    private String fecha;
    private String genero;
    private String sinopsis;

    public Pelicula() {}

    public Pelicula(ObjectId _id, String nombre, String fecha, String genero, String sinopsis) {
        this._id = _id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.genero = genero;
        this.sinopsis = sinopsis;
    }

    public Pelicula(String nombre, String fecha, String genero, String sinopsis) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.genero = genero;
        this.sinopsis = sinopsis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "entidades.Pelicula{" +
                "nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", genero='" + genero + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                '}';
    }
}
