import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ejemplos {
    public Connection conectarMySQL(String basedatos, String usuario, String password){
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jbdc:mysql://localhost:3306/"+basedatos, usuario, password);
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return conexion;
    }

    public Connection conectarPostgresSQL(String basedatos, String usuario, String password){
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jbdc:postgresql://localhost:5433/"+basedatos, usuario, password);
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return conexion;
    }

    public void desconectar(Connection conexion){
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public ArrayList<Usuario> getUsuarios(Connection conexion){
        String sentenciaSql = "SELECT * FROM usuario";
        
    }
}
