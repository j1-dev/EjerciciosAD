import java.io.Serializable;
import java.util.Date;

public class Alumno implements Serializable {
    private static long autoInc = 1;
    private long idAlumno;
    private String apellidos;
    private String nombre;
    private String fNacimiento;
    private int ciclo;

    public Alumno() {
    }

    public Alumno(String nombre, String apellidos, String fNacimiento, int ciclo) {
        this.idAlumno = autoInc;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.fNacimiento = fNacimiento;
        this.ciclo = ciclo;
        autoInc++;
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

    public long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(long idAlumno) {
        this.idAlumno = idAlumno;
    }
}
