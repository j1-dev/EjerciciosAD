import java.io.Serializable;

public class Persona implements Serializable {
    private static long autoInc = 1;
    private long idPersona;
    private String nombre;
    private int provincia;

    public Persona() {
    }

    public Persona(String nombre, int provincia) {
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public static long getAutoInc() {
        return autoInc;
    }

    public static void setAutoInc(long autoInc) {
        Persona.autoInc = autoInc;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }
}
