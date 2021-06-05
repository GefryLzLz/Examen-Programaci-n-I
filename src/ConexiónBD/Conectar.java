// Primera Clase.
package ConexiónBD;


import java.sql.*;
import javax.swing.*;


public class Conectar {
    Connection conect= null;
    
    public Connection conexion (){
        try{
            // Llamar o carga del Driver de MySQL
            Class.forName("com.mysql..jdbc.Driver");
            conect= (Connection) DriverManager.getConnection
        ("jdbc:mysql://localhost/examen?user=user&password=password");
                    
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error"+ e);
             
        }
        return conect;
    }
    
    
}
