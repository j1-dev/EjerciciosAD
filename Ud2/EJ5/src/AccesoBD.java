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

    public ArrayList<Pelicula> getPeliculas(boolean mysql){
        Connection conexion = connect(mysql);
        String sentenciaSql = "SELECT idalumno, nombre, apellidos, fechanacimiento, ciclo FROM alumno ORDER BY idalumno ASC";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        ArrayList<Pelicula> peliculas=new ArrayList<>();
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Pelicula pelicula=new Pelicula();
                pelicula.setIdAlumno(resultado.getLong(1));
                pelicula.setNombre(resultado.getString(2));
                pelicula.setApellidos(resultado.getString(3));
                pelicula.setfNacimiento(sdf.format(resultado.getDate(4)));
                pelicula.setCiclo(resultado.getInt(5));
                alumnos.add(pelicula);
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
        return peliculas;
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
