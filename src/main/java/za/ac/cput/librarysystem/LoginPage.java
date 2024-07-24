
package loginmain;

//import java.awt.Color;
//import java.awt.Cursor;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.JPasswordField;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//import javax.swing.SwingUtilities;

//public class LoginPage extends JFrame {
//    
//    private JPanel ePanel, wPanel, sPanel;
//    private JLabel userLabel, passwordLabel, signUpLabel, headingLabel;
//    private JTextField userText;
//    private JPasswordField passwordText;
//    private JButton btnLogin;
//
//    public void LoginPage() {
//        setTitle("User Login"); 
//        wPanel = new JPanel();
//        wPanel.setLayout(new GridLayout(2, 1, 0, 10));
//        
//        ePanel = new JPanel();
//        ePanel.setLayout(new GridLayout(2, 1, 0, 10));
//        
//        sPanel = new JPanel();
//        sPanel.setLayout(new FlowLayout());
//        
//        setLayout(null);
//        
//        headingLabel = new JLabel("Login", JLabel.CENTER);
//        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        headingLabel.setBounds(0, 10, 400, 30);
//        add(headingLabel);
//
//        userLabel = new JLabel("Username");
//        wPanel.add(userLabel);
//        
//        userText = new JTextField(20);
//        ePanel.add(userText);
//        
//        passwordLabel = new JLabel("Password");
//        wPanel.add(passwordLabel);
//        
//        passwordText = new JPasswordField();
//        ePanel.add(passwordText);
//        
//        btnLogin = new JButton("LOGIN");
//        btnLogin.setPreferredSize(new Dimension(200, 27));
//        sPanel.add(btnLogin);
//        btnLogin.addActionListener(new ActionListener() { //functionality for button
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String username = userText.getText();
//                String password = passwordText.getText();
//                if (isValidLogin(username, password)) {
//                    JOptionPane.showMessageDialog(null, "Welcome!");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Please try again.");
//                }
//            }
//        });
//        
//        // Add sign-up label with HTML for clickable "here"
//        signUpLabel = new JLabel("<html>Not a member? <a href='#'>Sign up here</a></html>");
//        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        signUpLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // Handle sign-up action here
//                JOptionPane.showMessageDialog(null, "Sign-up functionality goes here.");
//            }
//        });
//        sPanel.add(signUpLabel);
//        
//        
//        // Set the size and close operation of the JFrame
//        setSize(400, 280);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        // Add the panel to the JFrame
//        add(wPanel);
//        add(ePanel);
//        add(sPanel);
//        
//       wPanel.setBounds(10, 60, 100, 60);
//       ePanel.setBounds(180, 60, 180, 60);
//       sPanel.setBounds(10, 150, 350, 70);
//        
//        // Make the JFrame visible
//        setVisible(true);
//    }
// private boolean isValidLogin(String username, String password) {
//        return username.equals("Teyana") && password.equals("12345");
//    }
//}



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoginPage extends JFrame {

    private JPanel ePanel, wPanel, sPanel;
    private JLabel userLabel, passwordLabel, signUpLabel, headingLabel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton btnLogin;
    private BufferedImage backgroundImage;

    public LoginPage() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("C:/Users/Naqeebah/Documents/NetBeansProjects/Books/src/background_pic.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("User Login");
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        wPanel = new JPanel();
        wPanel.setLayout(new GridLayout(2, 1, 0, 10));
        wPanel.setOpaque(false);

        ePanel = new JPanel();
        ePanel.setLayout(new GridLayout(2, 1, 0, 10));
        ePanel.setOpaque(false);

        sPanel = new JPanel();
        sPanel.setLayout(new FlowLayout());
        sPanel.setOpaque(false);

        headingLabel = new JLabel("Login", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setBounds(0, 10, 400, 30);
        headingLabel.setForeground(Color.BLACK);
        backgroundPanel.add(headingLabel);

        userLabel = new JLabel("Username");
        userLabel.setForeground(Color.BLACK);
        wPanel.add(userLabel);

        userText = new JTextField(20);
        ePanel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);
        wPanel.add(passwordLabel);

        passwordText = new JPasswordField();
        ePanel.add(passwordText);

        btnLogin = new JButton("LOGIN");
        btnLogin.setPreferredSize(new Dimension(200, 27));
        sPanel.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (isValidLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please try again.");
                }
            }
        });

        signUpLabel = new JLabel("<html>Not a member? <a href='#'>Sign up here</a></html>");
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Sign-up functionality goes here.");
            }
        });
        sPanel.add(signUpLabel);

        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(wPanel);
        add(ePanel);
        add(sPanel);

        wPanel.setBounds(10, 60, 100, 60);
        ePanel.setBounds(180, 60, 180, 60);
        sPanel.setBounds(10, 150, 350, 70);

        setVisible(true);
    }

    private boolean isValidLogin(String username, String password) {
        return username.equals("Teyana") && password.equals("12345");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage();
            }
        });
    }

    class BackgroundPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}

