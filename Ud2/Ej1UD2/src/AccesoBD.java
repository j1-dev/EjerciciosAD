import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AccesoBD {

    private static AccesoBD me=null;/*Tenemos una referencia estática a la propia clase para invocarla desde otras
    partes del proyecto sin necesidad de crearla continuamente*/

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private AccesoBD(){//Constructor

    }

    public static AccesoBD getInstance(){//Con el método getInstance obtenemos la referencia estática de esta clase
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
            Class.forName("com.mysql.cj.jdbc.Driver");//Utilizamos el conector  para MySQL
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+basedatos,
                    usuario, password);//Obtenemos la conexión
        } catch (ClassNotFoundException cnfe) {//Manejo de excepciones
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conexion;
    }

    public Connection conectarPostgreSQL(String basedatos, String usuario, String password){
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");//Utilizamos el conector  para Postgres
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/"+basedatos,
                    usuario, password);//Obtenemos la conexión
        } catch (ClassNotFoundException cnfe) {//Manejo de excepciones
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conexion;
    }

    public void desconectar(Connection conexion){
        try {
            conexion.close();//Cerramos la conexión que nos pasan como argumento
            conexion = null;
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        }
    }

    public ArrayList<Alumno> getAlumnos(boolean mysql){
        Connection conexion = connect(mysql);
        String sentenciaSql = "SELECT idalumno, nombre, apellidos, fechanacimiento, ciclo FROM alumno ORDER BY idalumno ASC";//Sentencia SQL
        PreparedStatement sentencia = null;//Clase auxiliar para crear la consulta y pasarla al conector
        ResultSet resultado = null;//La ejecución de la consulta nos va a devolver un SET
        ArrayList<Alumno> alumnos=new ArrayList<>();//Creamos nuestro arraylist de alumnos vacío
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);//Pasamos la consulta al conector
            resultado = sentencia.executeQuery();
            while (resultado.next()) {/*Mientras haya filas que recorrer, iremos creando nuevos alumnos asignándole
            a cada variable de la clase alumno su correspondiente columna*/
                Alumno alumno=new Alumno();
                alumno.setIdAlumno(resultado.getLong(1));
                alumno.setNombre(resultado.getString(2));
                alumno.setApellidos(resultado.getString(3));
                alumno.setfNacimiento(resultado.getString(4));
                alumno.setCiclo(resultado.getInt(5));
                alumnos.add(alumno);
            }
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {//Cerramos la sentencia y el resultado
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
        String sentenciaSql = "INSERT INTO alumno (idalumno, nombre, apellidos, fechanacimiento, ciclo) VALUES (?,?,?,?,?)";//Sentencia DML
        PreparedStatement sentencia = null;
        try {/*De nuevo, en el orden en que hayamos escrito los parámetros en la consulta asociaremos los valores
        que les correspondan*/
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setLong(1, alumno.getIdAlumno());
            sentencia.setString(2,alumno.getNombre());
            sentencia.setString(3,alumno.getApellidos());
            sentencia.setString(4, alumno.getfNacimiento());
            sentencia.setInt(5, alumno.getCiclo());
            sentencia.executeUpdate();
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {//Cierre de la sentencia y desconexión
                    sentencia.close();
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    public void modificarAlumno(boolean mysql, Alumno alumno){
        Connection conexion = connect(mysql);
        String sentenciaSql = "UPDATE alumno SET nombre=?, apellidos=?, fechanacimiento=?, ciclo=? WHERE idalumno=?";//Sentencia DML
        PreparedStatement sentencia = null;
        try {/*De nuevo, en el orden en que hayamos escrito los parámetros en la consulta asociaremos los valores
        que les correspondan*/
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, alumno.getNombre());
            sentencia.setString(2,alumno.getApellidos());
            sentencia.setString(3, alumno.getfNacimiento());
            sentencia.setInt(4, alumno.getCiclo());
            sentencia.setLong(5,alumno.getIdAlumno());
            sentencia.executeUpdate();
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {//Cierre de la sentencia y desconexión
                    sentencia.close();
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    public void eliminarAlumno(boolean mysql, long ide){
        Connection conexion = connect(mysql);
        String sentenciaSql = "DELETE FROM alumno WHERE idalumno=?";//Sentencia DML
        PreparedStatement sentencia = null;
        try {/*De nuevo, en el orden en que hayamos escrito los parámetros en la consulta asociaremos los valores
        que les correspondan*/
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setLong(1, ide);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {//Cierre de la consulta y de la conexión
                    sentencia.close();
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    public Connection connect(boolean mysql){
        Connection conexion;
        if(mysql){//Elegimos el tipo de conexión
            conexion=this.conectarMySQL("ejercicio1","juan","password");
        }
        else{
            conexion=this.conectarPostgreSQL("ejercicio1","postgres","postgres");
        }
        return conexion;
    }
}
