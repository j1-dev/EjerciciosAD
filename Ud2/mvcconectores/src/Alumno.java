public class Alumno{

    private Long idAlumno;
    private String DNI;
    private String nombre;

    public Alumno() {

    }

    public Alumno(String DNI, String nombre) {
        this.DNI = DNI;
        this.nombre = nombre;
    }

    public Alumno(String DNI, String nombre, Long idAlumno) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.idAlumno = idAlumno;
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

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }
}
