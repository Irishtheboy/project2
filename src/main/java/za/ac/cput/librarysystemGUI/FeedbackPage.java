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
    private JButton submitBtn, returnToMenuBtn, btnLogout; // Button to return to the top menu

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
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);

        // Submit button
        submitBtn = new JButton("Submit");
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
        
         // Add Logout Button to the Top
        btnLogout = new JButton("Logout");
       // JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        //add(logoutPanel, BorderLayout.NORTH);  // Adding the logout button panel to the north of the frame
        
        // Logout Button Action Listener
        btnLogout.addActionListener(e -> {
            dispose();  // Close the current Admin Dashboard window
            //new LoginPage().setVisible(true);  // Open the login page
        });

        // Return to TopMenu button
        returnToMenuBtn = new JButton("Top Menu");
        returnToMenuBtn.addActionListener(e -> {
            new TopMenu(); // Assuming TopMenu is another JFrame
            dispose(); // Close this page
        });

        // Main panel to hold all sections
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create a panel to hold user info and feedback section
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BorderLayout());
        feedbackPanel.add(userInfoPanel, BorderLayout.NORTH);
        feedbackPanel.add(new JLabel("Please provide your feedback:"), BorderLayout.CENTER);
        feedbackPanel.add(scrollPane, BorderLayout.SOUTH);

        // Adding components to the main panel
        mainPanel.add(feedbackPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2)); // Two buttons side by side
        buttonPanel.add(submitBtn);
        buttonPanel.add(returnToMenuBtn);
        buttonPanel.add(btnLogout);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel at the bottom

        // Adding main panel to the frame
        add(mainPanel);

        setLocationRelativeTo(null); // Center the frame
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
