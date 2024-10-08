package za.ac.cput.librarysystemGui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FeedbackPage extends JFrame {

    public FeedbackPage() {
        super("Feedback");
        setSize(400, 300);
        setLayout(new BorderLayout());


        JTextArea feedbackArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        JButton submitBtn = new JButton("Submit");
        
        
        submitBtn.addActionListener(e -> {
            String feedback = feedbackArea.getText();
            if (!feedback.isEmpty()) {
                saveFeedback(feedback);
                JOptionPane.showMessageDialog(this, "Feedback submitted. Thank you!");
                feedbackArea.setText(""); 
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your feedback.");
            }
        });

        
        add(new JLabel("Please provide your feedback:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void saveFeedback(String feedback){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Feedback.txt", true))){
            writer.write(feedback);
            writer.newLine();
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error saving your feedback, kindly try again :)");
        }
    }
    
}
