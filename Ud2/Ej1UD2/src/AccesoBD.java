import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AccesoBD {

    private static AccesoBD me=null;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private AccesoBD(){

    }

    public static AccesoBD getInstance(){
        if (me == null) {
            synchronized(AccesoBD.class){
                if(me==null){
                    me = new AccesoBD();
                }
            }
        }
        return me;
    }

    public Connection conectarMySQL(String basedatos, String usuario, String password){
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+basedatos,
                    usuario, password);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conexion;
    }

    public Connection conectarPostgreSQL(String basedatos, String usuario, String password){
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/"+basedatos,
                    usuario, password);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conexion;
    }

    public void desconectar(Connection conexion){
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public ArrayList<Alumno> getAlumnos(boolean mysql){
        Connection conexion = connect(mysql);
        String sentenciaSql = "SELECT idalumno, nombre, apellidos, fechanacimiento, ciclo FROM alumno ORDER BY idalumno ASC";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        ArrayList<Alumno> alumnos=new ArrayList<>();
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Alumno alumno=new Alumno();
                alumno.setIdAlumno(resultado.getLong(1));
                alumno.setNombre(resultado.getString(2));
                alumno.setApellidos(resultado.getString(3));
                alumno.setfNacimiento(sdf.format(resultado.getDate(4)));
                alumno.setCiclo(resultado.getInt(5));
                alumnos.add(alumno);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                    resultado.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        return alumnos;
    }

    public void insertarAlumno(boolean mysql, Alumno alumno){
        Connection conexion = connect(mysql);
        String sentenciaSql = "INSERT INTO alumno (idalumno, nombre, apellidos, fechanacimiento, ciclo) VALUES (?,?,?,?,?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setLong(1, alumno.getIdAlumno());
            sentencia.setString(2,alumno.getNombre());
            sentencia.setString(3,alumno.getApellidos());
            sentencia.setDate(4, new java.sql.Date(sdf.parse(alumno.getfNacimiento()).getTime()));
            sentencia.setInt(5, alumno.getCiclo());
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    public void modificarAlumno(boolean mysql, Alumno alumno){
        Connection conexion = connect(mysql);
        String sentenciaSql = "UPDATE alumno SET nombre=?, apellidos=?, fechanacimiento=?, ciclo=? WHERE idalumno=?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, alumno.getNombre());
            sentencia.setString(2,alumno.getApellidos());
            sentencia.setDate(3, new java.sql.Date(sdf.parse(alumno.getfNacimiento()).getTime()));
            sentencia.setInt(4, alumno.getCiclo());
            sentencia.setLong(5,alumno.getIdAlumno());
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    public void eliminarAlumno(boolean mysql, long ide){
        Connection conexion = connect(mysql);
        String sentenciaSql = "DELETE FROM alumno WHERE idalumno=?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setLong(1, ide);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    public Connection connect(boolean mysql){
        Connection conexion;
        if(mysql){
            conexion=this.conectarMySQL("ejercicio1","root","poflo123");
        }
        else{
            conexion=this.conectarPostgreSQL("ejercicio1","aplicacion","poflo123");
        }
        return conexion;
    }
}
