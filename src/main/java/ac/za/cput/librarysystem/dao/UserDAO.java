package ac.za.cput.librarysystem.dao;

import ac.za.cput.librarysystem.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UserDAO {

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

    public boolean validateLogin(String username, String password) {
        String query = "SELECT username FROM USERS WHERE CAST(username AS VARCHAR(255)) = ? AND CAST(password AS VARCHAR(255)) = ?";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error logging in: " + e.getMessage());
            return false;
        }
    }

    public boolean rentBook(int userId, int bookId) {
        String insertRentalSQL = "INSERT INTO Rentals (user_id, book_id, rental_date) VALUES (?, ?, CURRENT_DATE)";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(insertRentalSQL)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error renting book: " + e.getMessage());
            return false;
        }
    }

    public int getUserId(String username) {
        String query = "SELECT id FROM USERS WHERE username = ?";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1; // User not found

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching user ID: " + e.getMessage());
            return -1;
        }
    }
    
    
}
