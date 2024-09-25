package ac.za.cput.librarysystem.dao;

import ac.za.cput.librarysystem.connection.DBConnection;  // Ensure this is your correct DB connection class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Sabura11
 */
public class BookDAO {

    public List<Object[]> getAllBooks() {
        String query = "SELECT bookid, title, author, genre, isbn, published_year, available FROM BOOKS";
        List<Object[]> booksList = new ArrayList<>();

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] book = new Object[7];
                book[0] = rs.getInt("bookid");
                book[1] = rs.getString("title");
                book[2] = rs.getString("author");
                book[3] = rs.getString("genre");
                book[4] = rs.getString("isbn");
                book[5] = rs.getInt("published_year");
                book[6] = rs.getBoolean("available");

                booksList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching books: " + e.getMessage());
        }

        return booksList;
    }

    // Method to add a book to the database
    public void addBook(String title, String author, String genre, String isbn, int yearPublished, boolean available) {
        String sql = "INSERT INTO BOOKS (title, author, genre, isbn, published_year, available) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
            pstmt.setString(4, isbn);
            pstmt.setInt(5, yearPublished);
            pstmt.setBoolean(6, available);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        String query = "DELETE FROM BOOKS WHERE bookid = ?";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error deleting book: " + e.getMessage());
        }
    }

    public boolean markBookAsRented(int bookId) {
        String query = "UPDATE BOOKS SET available = false WHERE bookid = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // Return true if a row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating book availability: " + e.getMessage());
            return false;
        }
    }

    // Method to check if the book is available
    public boolean isBookAvailable(int bookId) {
        String query = "SELECT available FROM BOOKS WHERE bookid = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if the book is not found or there's an error
    }

    // Method to mark a book as rented and create a rental record
    public boolean rentBook(int userId, int bookId) {
        String updateAvailabilityQuery = "UPDATE BOOKS SET available = false WHERE bookid = ?";
        String createRentalQuery = "INSERT INTO RENTALS (userid, bookid, rent_date) VALUES (?, ?, CURRENT_DATE)";

        // Check if the user and book exist before proceeding
        if (!isUserExists(userId) || !isBookExists(bookId)) {
            JOptionPane.showMessageDialog(null, "User or Book does not exist.");
            return false; // Early exit if validation fails
        }

        try (Connection conn = DBConnection.derbyConnection()) {
            // First, mark the book as unavailable
            try (PreparedStatement pstmt = conn.prepareStatement(updateAvailabilityQuery)) {
                pstmt.setInt(1, bookId);
                pstmt.executeUpdate();
            }

            // Then, insert a rental record
            try (PreparedStatement pstmt = conn.prepareStatement(createRentalQuery)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, bookId);
                int rowsInserted = pstmt.executeUpdate();
                return rowsInserted > 0; // Return true if the rental was successfully logged
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error renting book: " + e.getMessage());
            return false;
        }
    }

// Method to check if user exists
    private boolean isUserExists(int userId) {
        String query = "SELECT COUNT(*) FROM USERS WHERE userid = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Method to check if book exists
    private boolean isBookExists(int bookId) {
        String query = "SELECT COUNT(*) FROM BOOKS WHERE bookid = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
