package ac.za.cput.librarysystem.dao;

import ac.za.cput.librarysystem.connection.DBConnection;
import ac.za.cput.librarysystem.domain.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UserDAO {

    // Oratile / Franco lukhele / Naqeebah
    public boolean signUpUser(String username, String email, String phone, String password) {
        String insertUserSQL = "INSERT INTO USERS (username, email, phone, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error signing up user: " + e.getMessage());
            return false;
        }

    }

    //Teyana & Franco & Mfana
    public boolean signUpUser(String username, String email, String phone, String password, String role) {
        String insertUserSQL = "INSERT INTO USERS (username, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);
            pstmt.setString(5, role);  // Set the role parameter

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error signing up user: " + e.getMessage());
            return false;
        }
    }

    public String validateLogin(String username, String password) {
        String query = "SELECT role FROM USERS WHERE CAST(username AS VARCHAR(255)) = CAST(? AS VARCHAR(255)) "
                + "AND CAST(password AS VARCHAR(255)) = CAST(? AS VARCHAR(255))";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");  // Return the user's role if the login is successful
            }
            return null;  // Return null if no user is found
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error logging in: " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> getAllUsers() {
        String query = "SELECT userid, username, email, role FROM USERS";
        List<Object[]> usersList = new ArrayList<>();

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] user = new Object[4];
                user[0] = rs.getInt("userid");
                user[1] = rs.getString("username");
                user[2] = rs.getString("email");
                user[3] = rs.getString("role");
                usersList.add(user);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching users: " + e.getMessage());
        }
        return usersList;
    }

}
