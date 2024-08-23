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
    
    //Naqeebah code

//    public boolean rentBook(int userId, int bookId) {
//        String insertRentalSQL = "INSERT INTO Rentals (user_id, book_id, rental_date) VALUES (?, ?, CURRENT_DATE)";
//
//        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(insertRentalSQL)) {
//
//            pstmt.setInt(1, userId);
//            pstmt.setInt(2, bookId);
//
//            int rowsAffected = pstmt.executeUpdate();
//            return rowsAffected > 0;
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error renting book: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public String getUserUsername(String username) {
//        String query = "SELECT username FROM USERS WHERE username = ?";
//
//        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setString(1, username);
//
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return rs.getString("username"); // Return the username instead of id
//            }
//            return null;
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error fetching user: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Book> getRentedBooks(int userId) {
//        List<Book> rentedBooks = new ArrayList<>();
//        String query = "SELECT b.* FROM Rentals r JOIN Books b ON r.book_id = b.id WHERE r.user_id = ?";
//
//        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setInt(1, userId);
//
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String author = rs.getString("author");
//                String imagePath = rs.getString("image_path"); // Adjust based on your schema
//                rentedBooks.add(new Book(name, author, imagePath));
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error fetching rented books: " + e.getMessage());
//        }
//        return rentedBooks;
//    }
//
//    public boolean isBookAvailable(int bookId) {
//        String query = "SELECT COUNT(*) AS available FROM Rentals WHERE book_id = ?";
//
//        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setInt(1, bookId);
//
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("available") == 0; // If count is 0, the book is available
//            }
//            return false;
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error checking book availability: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean rentBookByUsername(String username, int bookId) {
//        String insertRentalSQL = "INSERT INTO Rentals (user_ID, book_id, rental_date) "
//                + "SELECT u.username, ?, CURRENT_DATE "
//                + "FROM USERS u WHERE u.username = ?";
//
//        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(insertRentalSQL)) {
//
//            pstmt.setInt(1, bookId);
//            pstmt.setString(2, username);
//
//            int rowsAffected = pstmt.executeUpdate();
//            return rowsAffected > 0;
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error renting book: " + e.getMessage());
//            return false;
//        }
//    }
    
    //Franco Code Piece
    
    

}
