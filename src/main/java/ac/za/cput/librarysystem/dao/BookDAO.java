package ac.za.cput.librarysystem.dao;

import ac.za.cput.librarysystem.connection.DBConnection;  // Ensure this is your correct DB connection class
import ac.za.cput.librarysystem.domain.Book;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
    
    
    public List<Book> getAllBookss() {
    List<Book> bookList = new ArrayList<>();
    String query = "SELECT title, author, image_path FROM books"; // Ensure this matches your actual table and column names

    try (Connection conn = DBConnection.derbyConnection(); 
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) { // Execute the query and get a ResultSet

        while (rs.next()) { // Iterate through the ResultSet
            String title = rs.getString("title");
            String author = rs.getString("author");
            String imagePath = rs.getString("image_path");
            Book book = new Book(title, author, imagePath);
            bookList.add(book);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return bookList;
}

    public List<Object[]> getBorrowHistory(int userId) {
        List<Object[]> borrowHistory = new ArrayList<>();
        String query = "SELECT b.BOOKID, b.TITLE, b.AUTHOR, r.RENT_DATE, r.RETURN_DATE "
                + "FROM BOOKS b "
                + "JOIN RENTALS r ON b.BOOKID = r.BOOKID "
                + "WHERE r.USERID = ? AND r.STATUS = 'returned'"; 

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] historyEntry = new Object[5];
                historyEntry[0] = rs.getInt("BOOKID");
                historyEntry[1] = rs.getString("TITLE");
                historyEntry[2] = rs.getString("AUTHOR");
                historyEntry[3] = rs.getDate("RENT_DATE"); // Borrow date
                historyEntry[4] = rs.getDate("RETURN_DATE"); // Return date
                borrowHistory.add(historyEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowHistory;
    }

    // Method to add a book to the database
public void addBook(String title, String author, String genre, String isbn, int yearPublished, boolean available, String imagePath) {
    String sql = "INSERT INTO BOOKS (title, author, genre, isbn, published_year, available, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setString(3, genre);
        pstmt.setString(4, isbn);
        pstmt.setInt(5, yearPublished);
        pstmt.setBoolean(6, available);
        pstmt.setString(7, imagePath); // Set the image path
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
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting book: " + e.getMessage());
        }
    }

    public boolean markBookAsRented(int bookId) {
        String query = "UPDATE BOOKS SET available = false WHERE bookid = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating book availability: " + e.getMessage());
            return false;
        }
    }

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
        return false;
    }

    public boolean rentBook(int userId, int bookId) {
        String updateBookAvailability = "UPDATE books SET available = false WHERE bookid = ?";
        String insertRentalRecord = "INSERT INTO rentals (userid, bookid, rent_date, return_date, status) VALUES (?, ?, CURRENT_DATE, ?, 'active')";

        try (Connection connection = DBConnection.derbyConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Update the book availability
            try (PreparedStatement updateBookStmt = connection.prepareStatement(updateBookAvailability)) {
                updateBookStmt.setInt(1, bookId);
                int rowsUpdated = updateBookStmt.executeUpdate();

                // If book availability update was successful, insert the rental record
                if (rowsUpdated > 0) {
                    LocalDate rentDate = LocalDate.now();
                    LocalDate returnDate = rentDate.plusDays(14); // Calculate return date
                    Date sqlReturnDate = Date.valueOf(returnDate); // Convert LocalDate to java.sql.Date

                    try (PreparedStatement insertRentalStmt = connection.prepareStatement(insertRentalRecord)) {
                        insertRentalStmt.setInt(1, userId);
                        insertRentalStmt.setInt(2, bookId);
                        insertRentalStmt.setDate(3, sqlReturnDate); // Set return date
                        insertRentalStmt.executeUpdate();
                    }
                    connection.commit(); // Commit transaction
                    return true;
                } else {
                    connection.rollback(); // Rollback transaction if book is not updated
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public List<Object[]> getRentedBooks(int userId) {
        String query = "SELECT b.bookid, b.title, b.author, r.return_date "
                + "FROM rentals r JOIN books b ON r.bookid = b.bookid "
                + "WHERE r.userid = ? AND r.status = 'active'";
        List<Object[]> rentedBooksList = new ArrayList<>();

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] rentedBook = new Object[4];
                rentedBook[0] = rs.getInt("bookid");
                rentedBook[1] = rs.getString("title");
                rentedBook[2] = rs.getString("author");
                rentedBook[3] = rs.getDate("return_date");

                rentedBooksList.add(rentedBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching rented books: " + e.getMessage());
        }

        return rentedBooksList;
    }

    public boolean returnBook(int userId, int bookId) {
        String updateBookAvailability = "UPDATE books SET available = true WHERE bookid = ?";
        String updateRentalStatus = "UPDATE rentals SET status = 'returned' WHERE userid = ? AND bookid = ?";

        try (Connection connection = DBConnection.derbyConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Update the book availability
            try (PreparedStatement updateBookStmt = connection.prepareStatement(updateBookAvailability)) {
                updateBookStmt.setInt(1, bookId);
                int rowsUpdated = updateBookStmt.executeUpdate();

                // If book availability update was successful, update rental status
                if (rowsUpdated > 0) {
                    try (PreparedStatement updateRentalStmt = connection.prepareStatement(updateRentalStatus)) {
                        updateRentalStmt.setInt(1, userId);
                        updateRentalStmt.setInt(2, bookId);
                        updateRentalStmt.executeUpdate();
                    }
                    connection.commit(); // Commit transaction
                    return true;
                } else {
                    connection.rollback(); // Rollback transaction if book is not updated
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getOverdueBooksCount(int userId) {
        int overdueCount = 0;
        String query = "SELECT COUNT(*) FROM RENTALS WHERE USERID = ? AND RETURN_DATE < CURRENT_DATE AND STATUS = 'active'";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                overdueCount = rs.getInt(1); // Get the count of overdue books
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching overdue books: " + e.getMessage());
        }

        return overdueCount;
    }

    public int getLoanedBooksCount(int userId) {
        int loanedCount = 0;
        String query = "SELECT COUNT(*) FROM RENTALS WHERE USERID = ? AND STATUS = 'active'";

        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                loanedCount = rs.getInt(1); // Get the count of loaned books
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching loaned books: " + e.getMessage());
        }

        return loanedCount;
    }
    
    

}
