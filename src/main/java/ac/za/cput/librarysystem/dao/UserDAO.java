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

    public void addUser(String username, String email, String role) {
        String query = "INSERT INTO USERS (username, email, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error adding user: " + e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        String query = "DELETE FROM USERS WHERE userid = ?";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage());
        }
    }

    public String getUserUsername(String username) {
        String query = "SELECT username FROM USERS WHERE username = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching user: " + e.getMessage());
        }
        return null; // User not found
    }

    public boolean isBookAvailable(int bookId) {
        String query = "SELECT COUNT(*) FROM RENTS WHERE book_id = ? AND return_date IS NULL";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Book is available if no rentals found
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking book availability: " + e.getMessage());
        }
        return false; // Book is not available
    }

    public boolean rentBookByUsername(String username, int bookId) {
        String insertRentSQL = "INSERT INTO RENTALS (username, bookid, rent_date) VALUES (?, ?, CURRENT_DATE)";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(insertRentSQL)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, bookId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if rental was successful
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error renting book: " + e.getMessage());
            return false; // Rental failed
        }
    }

    public List<Object[]> getUserAccountDetails(String username) {
        String query = "SELECT COUNT(CASE WHEN return_date IS NULL THEN 1 END) AS overdue_books, "
                + "SUM(CASE WHEN return_date IS NULL THEN fine ELSE 0 END) AS total_fine, "
                + "COUNT(*) AS total_loaned_books "
                + "FROM RENTALS WHERE username = ?";
        List<Object[]> accountDetails = new ArrayList<>();

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Object[] details = new Object[3];
                details[0] = rs.getInt("overdue_books");      // Number of overdue books
                details[1] = rs.getDouble("total_fine");      // Total fine amount
                details[2] = rs.getInt("total_loaned_books"); // Total loaned books
                accountDetails.add(details);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching account details: " + e.getMessage());
        }
        return accountDetails;
    }
    
      public boolean payFine(int userId) {
        String query = "UPDATE FINES SET IS_PAID = TRUE WHERE USERID = ? AND IS_PAID = FALSE";
        
        try (Connection conn = DBConnection.derbyConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                return true; // Fine paid successfully
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Failed to pay fine
    }

    // Method to get the fine amount for the user
    public double getFineAmount(int userId) {
        String query = "SELECT SUM(FINE_AMOUNT) AS totalFine FROM FINES WHERE USERID = ? AND IS_PAID = FALSE";
        double fineAmount = 0;

        try (Connection conn = DBConnection.derbyConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                fineAmount = rs.getDouble("totalFine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fineAmount;
    }
    
    

}
