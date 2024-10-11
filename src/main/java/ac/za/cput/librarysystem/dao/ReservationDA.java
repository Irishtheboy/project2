package ac.za.cput.librarysystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDA {
    private Connection connection;

    public ReservationDA(Connection connection) {
        this.connection = connection;
    }

    // Create a new reservation
    public boolean createReservation(String userId, int bookId) {
        String sql = "INSERT INTO RESERVATIONS (USERID, BOOKID, RESERVATION_DATE, NOTIFIED) VALUES (?, ?, CURRENT_DATE, false)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate() > 0; // Return true if reservation is created
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    // Retrieve all reservations
    public List<String> getAllReservations() {
        List<String> reservationsList = new ArrayList<>();
        String sql = "SELECT * FROM RESERVATIONS"; // Adjust to select the needed fields
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                String reservationDetails = String.format("Reservation ID: %d, User ID: %s, Book ID: %d, Date: %s",
                        resultSet.getInt("RESERVATIONID"),
                        resultSet.getString("USERID"),
                        resultSet.getInt("BOOKID"),
                        resultSet.getDate("RESERVATION_DATE"));
                reservationsList.add(reservationDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationsList; // Return the list of reservations
    }

    // Update notification status by reservation ID
    public void updateNotificationStatus(int reservationId) {
        String sql = "UPDATE RESERVATIONS SET NOTIFIED = true WHERE RESERVATIONID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a reservation by ID
    public boolean deleteReservation(int reservationId) {
        String sql = "DELETE FROM RESERVATIONS WHERE RESERVATIONID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            return pstmt.executeUpdate() > 0; // Return true if reservation is deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
}
