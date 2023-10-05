import java.io.Serializable;
import java.util.ArrayList;

public class Receta implements Serializable {
    public static final long serialVersionUID = 1L;
    private String nombre;
    private String descripcion;
    private ArrayList<String> ingredientes;

    public Receta(String nombre, String descripcion, ArrayList<String> ingredientes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
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

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
