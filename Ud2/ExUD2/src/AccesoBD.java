import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class AccesoBD {

    private static AccesoBD me=null;
    final private String MYSQL_USER = "juan";
    final private String MYSQL_PASS = "password";
    final private String PSQL_USER = "postgres";
    final private String PSQL_PASS = "postgres";
    public AccesoBD(){

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

    public ArrayList<Persona> getPersonas(boolean mysql){
        Connection conexion = connect(mysql);
        String sentenciaSql = "SELECT idpersona, nombre, provincia FROM persona ORDER BY idpersona ASC";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        ArrayList<Persona> personas =new ArrayList<>();
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Persona persona =new Persona();
                persona.setIdPersona(resultado.getLong(1));
                persona.setNombre(resultado.getString(2));
                persona.setProvincia(resultado.getInt(3));
                personas.add(persona);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                    assert resultado != null;
                    resultado.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        return personas;
    }

    public void insertarPersona(boolean mysql, Persona persona){
        Connection conexion = connect(mysql);
        String sentenciaSql = "INSERT INTO persona (nombre, provincia) VALUES (?,?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, persona.getNombre());
            sentencia.setInt(2, persona.getProvincia());
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

    public void modificarPersona(boolean mysql, Persona persona){
        Connection conexion = connect(mysql);
        String sentenciaSql = "UPDATE persona SET nombre=?, provincia=? WHERE idpersona=?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, persona.getNombre());
            sentencia.setInt(2, persona.getProvincia());
            sentencia.setLong(3, persona.getIdPersona());
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

    public void eliminarPersona(boolean mysql, long ide){
        Connection conexion = connect(mysql);
        String sentenciaSql = "DELETE FROM persona WHERE idpersona=?";
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
            conexion=this.conectarMySQL("examenud2",MYSQL_USER,MYSQL_PASS);
        }
        else{
            conexion=this.conectarPostgreSQL("examenud2",PSQL_USER,PSQL_PASS);
        }
        return conexion;
    }
}
