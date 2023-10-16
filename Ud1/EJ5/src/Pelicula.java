import java.util.ArrayList;

public class Pelicula {
    private String nombre;
    private String fecha;
    private String genero;
    private String sinopsis;
    private ArrayList<String> actores;

    public Pelicula(String nombre, String fecha, String genero, String sinopsis, ArrayList<String> actores) {
        this.nombre = nombre;
        this.fecha = fecha;
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

    public ArrayList<String> getActores() {
        return actores;
    }

    public void setActores(ArrayList<String> actores) {
        this.actores = actores;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", genero='" + genero + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", actores=" + actores.toString() +
                '}';
    }
}
