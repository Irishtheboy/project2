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
        String query = "SELECT bookid, title, author, genre FROM BOOKS"; // Ensure the column name matches
        List<Object[]> booksList = new ArrayList<>();

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] book = new Object[4];
                book[0] = rs.getInt("bookid");
                book[1] = rs.getString("title");
                book[2] = rs.getString("author");
                book[3] = rs.getString("genre");
                booksList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error fetching books: " + e.getMessage());
        }

        return booksList;
    }

    // Method to add a book to the database
    public void addBook(String title, String author, String genre) {
        String sql = "INSERT INTO BOOKS (title, author, genre) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
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

}
