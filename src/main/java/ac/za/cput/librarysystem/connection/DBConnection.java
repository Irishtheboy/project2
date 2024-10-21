/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.za.cput.librarysystem.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    
    public static Connection derbyConnection() throws SQLException{
     String dbURL = "jdbc:derby://localhost:1527/LibraryDatabse";
        String username = "administrator";
        String password = "admin";
    
        Connection connection = DriverManager.getConnection(dbURL,username, password);
        return connection;
    }
    
}
