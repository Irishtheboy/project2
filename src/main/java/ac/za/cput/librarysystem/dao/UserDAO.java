/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.za.cput.librarysystem.dao;

import ac.za.cput.librarysystem.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @authors Sabura11, oratile
 */
public class UserDAO {
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    
    
    public UserDAO() {
        try {
            this.con = DBConnection.derbyConnection();
            System.out.println("Connection made");
        }
        catch(SQLException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Sign up (Create a new user)
     public void signUp(User user){
        String sql = "INSERT INTO CLIENT (USER_NAME, EMAIL, PHONE, PASSWORD) VALUES(?, ?, ?, ?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUser_Name());
            pstmt.setString(2, user.getUserEmail());
            pstmt.setString(3, user.getUserPhone());
            pstmt.setString(4, user.getUserPassword());
            pstmt.executeUpdate();
        }
        catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
            }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        finally {
            try {
                if (stmt != null)
                    stmt.close();

            }
            catch(SQLException exception){
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }//end finally
    }//end method

     // Login (Verify user credentials)
     public void login(User user){
         String sql = "SELECT * FROM CLIENT WHERE USER_NAME = ? AND PASSWORD = ?";
         try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUser_Name());
            pstmt.setString(2, user.getUserPassword());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                 JOptionPane.showMessageDialog(null,"Login successful");
            } else {
                 JOptionPane.showMessageDialog(null,"Invalid username or password");
            }
        } 
         catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
            }
//        catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//            }
         
         finally {
            try{
            if (pstmt != null) {
                pstmt.close();
                }
            }
         catch(SQLException exception){
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
            }
         }
     }//end method
}
