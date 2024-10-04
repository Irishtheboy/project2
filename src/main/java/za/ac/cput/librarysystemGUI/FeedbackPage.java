package za.ac.cput.librarysystemGui;

import javax.swing.*;
import java.awt.*;

public class FeedbackPage extends JFrame {

    public FeedbackPage() {
        super("Feedback");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Create a text area for feedback input
        JTextArea feedbackArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        JButton submitBtn = new JButton("Submit");
        
        // Add action listener to the submit button
        submitBtn.addActionListener(e -> {
            String feedback = feedbackArea.getText();
            if (!feedback.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Feedback submitted. Thank you!");
                feedbackArea.setText(""); // Clear the text area
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your feedback.");
            }
        });

        // Add components to the frame
        add(new JLabel("Please provide your feedback:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
