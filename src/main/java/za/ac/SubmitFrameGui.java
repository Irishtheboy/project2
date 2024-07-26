package submitframegui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SubmitFrameGui {

    public static void main(String[] args) {
// Creating the frame and naming it Submit Frame.
        JFrame frame = new JFrame("Submit Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.getContentPane().setBackground(Color.YELLOW);

        frame.setLayout(new BorderLayout());
        frame.setResizable(true);
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(3, 3));
        labelPanel.setBackground(Color.ORANGE); // Changing the background color of the label panels to Orange.


        String labelText = "<html>Return books within <font color='red'>colored</font> 30 days.</html>";
        JLabel label = new JLabel(labelText);
        JLabel label1 = new JLabel("Books reserved.", SwingConstants.CENTER);
        JLabel label2 = new JLabel("Make sure to pick up books at the library.", SwingConstants.CENTER);
        JLabel label3 = new JLabel("<html>Return the books within <font color='red'>30 days</font>.</html>", SwingConstants.CENTER);// it changes the color of the words 30 days to a different one than the entire text.

        // adding the panels to the main label panel 
        labelPanel.add(label1);
        labelPanel.add(label2);
        labelPanel.add(label3);

        frame.add(labelPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton accountButton = new JButton("Account");
        JButton topMenuButton = new JButton("Top Menu");
        JButton logoutButton = new JButton("Log out");

        accountButton.setBackground(Color.cyan);
        accountButton.setForeground(Color.BLACK);

        topMenuButton.setBackground(Color.YELLOW);
        topMenuButton.setForeground(Color.BLACK);

        logoutButton.setBackground(Color.CYAN);
        logoutButton.setForeground(Color.BLACK);

        buttonPanel.add(accountButton);
        buttonPanel.add(topMenuButton);
        buttonPanel.add(logoutButton);

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Account button has been clicked!");
            }
        });
        topMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Top Menu button has been clicked!");
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Log Out button has been clicked!");
            }
        });
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

      
    }

}
