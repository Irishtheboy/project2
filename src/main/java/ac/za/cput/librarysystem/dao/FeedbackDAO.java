package ac.za.cput.librarysystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackDAO {
    private Connection connection; // Assume you have a connection object

    public FeedbackDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean submitFeedback(String userId, String feedback) {
        String sql = "INSERT INTO feedback (user_id, feedback) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, feedback);
            return preparedStatement.executeUpdate() > 0; // Return true if feedback is saved
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
}
