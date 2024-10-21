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
    private JButton submitBtn, returnToMenuBtn, btnLogout;

    public FeedbackPage() {
        super("Feedback");
        setSize(400, 400);
        setLayout(new BorderLayout());
        setResizable(false); 

        
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 2));
        userInfoPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        userInfoPanel.add(nameField);
        userInfoPanel.add(new JLabel("Surname:"));
        surnameField = new JTextField(20);
        userInfoPanel.add(surnameField);

       
        JTextArea feedbackArea = new JTextArea(10, 30);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);

        
        submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(76, 175, 80)); 
        submitBtn.setForeground(Color.WHITE); 
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

        
        btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.RED); 
        btnLogout.setForeground(Color.WHITE); 
        btnLogout.addActionListener(e -> {
            dispose(); 
            
        });

        
        returnToMenuBtn = new JButton("Top Menu");
        returnToMenuBtn.addActionListener(e -> {
            new TopMenu(); 
            dispose(); 
        });

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BorderLayout());
        feedbackPanel.add(userInfoPanel, BorderLayout.NORTH);
        feedbackPanel.add(new JLabel("Please provide your feedback:"), BorderLayout.CENTER);
        feedbackPanel.add(scrollPane, BorderLayout.SOUTH);

        
        mainPanel.add(feedbackPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3)); 
        buttonPanel.add(submitBtn);
        buttonPanel.add(returnToMenuBtn);
        buttonPanel.add(btnLogout);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); 

        
        add(mainPanel);
        setLocationRelativeTo(null); 
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void saveFeedback(String name, String surname, String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Feedback.txt", true))) {
           
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
