import java.util.ArrayList;

public class Pelicula {
    private String nombre;
    private String descripcion;
    private String genero;
    private String sinopsis;
    private ArrayList<String> actores;

    public Pelicula(String nombre, String descripcion, String genero, String sinopsis, ArrayList<String> actores) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.actores = actores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public ArrayList<String> getActores() {
        return actores;
    }

    public void setActores(ArrayList<String> actores) {
        this.actores = actores;
    }
}
