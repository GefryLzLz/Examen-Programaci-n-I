//Segunda clase.
package ConexiónBD;

import java.sql.*;

public class Conexion {
    
    private String db="examen" ; 
   private String user="root";
   private String Password="";
   private String url="jdbc:mysql://localhost/"+db;
   private Connection conn= null;
   //metodo de conexion
   public Conexion (){
       this.url="jdbc:mysql://localhost:3306/"
               + "examen?serverTunezone=UTC"; 
       try{
           // El llamado de las libreria
           Class.forName("com.mysql.jdbc.Driver");
           conn=DriverManager.getConnection(this.url,this.user, this.Password);
           if(conn!=null){
               System.out.println("Se ha conectado a la"
                       +  "base de datos"+db+ " Bienvenido");
           }
       }catch (SQLException e){
           System.err.println(e.getMessage());
           
       }catch (ClassNotFoundException e){
           System.out.println(e.getMessage());
       }
   }
   public Connection obtenerConexion (){ 
       return conn;
   } 
}
