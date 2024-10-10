package za.ac.cput.librarysystemGui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FeedbackPage extends JFrame {

    private JTextField nameField, surnameField;

    public FeedbackPage() {
        super("Feedback");
        setSize(400, 400);
        setLayout(new BorderLayout());

        // Creating text fields for name and surname
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 2));
        userInfoPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        userInfoPanel.add(nameField);
        userInfoPanel.add(new JLabel("Surname:"));
        surnameField = new JTextField(20);
        userInfoPanel.add(surnameField);

        // Feedback text area
        JTextArea feedbackArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);

        // Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String feedback = feedbackArea.getText();

            if (!name.isEmpty() && !surname.isEmpty() && !feedback.isEmpty()) {
                saveFeedback(name, surname, feedback);
                JOptionPane.showMessageDialog(this, "Feedback submitted. Thank you!");
                nameField.setText("");
                surnameField.setText("");
                feedbackArea.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        });

        // Adding components to the layout
        add(userInfoPanel, BorderLayout.NORTH);
        add(new JLabel("Please provide your feedback:"), BorderLayout.CENTER);
        add(scrollPane, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void saveFeedback(String name, String surname, String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Feedback.txt", true))) {
            // Capture the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);

            writer.write("Date: " + formattedDate);
            writer.newLine();
            writer.write("Name: " + name);
            writer.newLine();
            writer.write("Surname: " + surname);
            writer.newLine();
            writer.write("Feedback: " + feedback);
            writer.newLine();
            writer.write("-----------------------------------------------------------------------");
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving your feedback, kindly try again :)");
        }
    }
}
