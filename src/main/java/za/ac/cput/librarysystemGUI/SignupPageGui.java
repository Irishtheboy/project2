package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author oratile
 */
public class SignupPageGui extends JFrame {
    JPanel mainPnl, westPnl, eastPnl, centerPnl, southPnl, backgroundPnl;
    JLabel titleLbl, usernameLbl, emailLbl, phoneLbl, passwordLbl, roleLbl, loginLinkLbl;
    JTextField usernameField, emailField, phoneField;
    JPasswordField passwordField;
    JComboBox<String> roleComboBox;  // Add this for roles
    JButton signUpButton;
    
    public SignupPageGui() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);  
        setResizable(true);
        
        guiSetUp();
        guiLayout();
        guiBackground();
        guiAddListeners();
        
        setContentPane(backgroundPnl);
    }
    
    private void guiSetUp() {    
        mainPnl = new JPanel(new BorderLayout());
        westPnl = new JPanel(new GridLayout(5, 1, 10, 10));  
        eastPnl = new JPanel(new GridLayout(5, 1, 10, 10));  
        centerPnl = new JPanel(new GridLayout(1, 2, 10, 10));
        southPnl = new JPanel(new GridLayout(2, 1, 10, 10));
        
        backgroundPnl = new JPanel();
        
        titleLbl = new JLabel("Sign-up", SwingConstants.CENTER);
        usernameLbl = new JLabel("Username:", SwingConstants.CENTER);
        emailLbl = new JLabel("Email:", SwingConstants.CENTER);
        phoneLbl = new JLabel("Phone:", SwingConstants.CENTER);
        passwordLbl = new JLabel("Password", SwingConstants.CENTER);
        roleLbl = new JLabel("Role:", SwingConstants.CENTER);  
        
        usernameField = new JTextField(15);
        emailField = new JTextField(15);
        phoneField = new JTextField(15);
        passwordField = new JPasswordField(15);
        
        
        String[] roles = {"Member", "Admin"};
        roleComboBox = new JComboBox<>(roles);
        
        signUpButton = new JButton("Sign-Up");
        
        // Initialize login link label
        loginLinkLbl = new JLabel("<html>Already a member? <a href='#'>Login here</a></html>", SwingConstants.CENTER);
        loginLinkLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLinkLbl.setForeground(Color.BLUE); // Optional: Set link color
    }
    
    private void guiLayout() {
        titleLbl.setFont(new Font("Arial", Font.BOLD, 24));
        mainPnl.add(titleLbl, BorderLayout.NORTH);
        
        westPnl.add(usernameLbl);
        westPnl.add(emailLbl);
        westPnl.add(phoneLbl);
        westPnl.add(passwordLbl);
        westPnl.add(roleLbl);  
        
        eastPnl.add(usernameField);
        eastPnl.add(emailField);
        eastPnl.add(phoneField);
        eastPnl.add(passwordField);
        eastPnl.add(roleComboBox);  
        
        centerPnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPnl.add(westPnl);
        centerPnl.add(eastPnl);
        
        mainPnl.add(centerPnl, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(signUpButton);
        southPnl.add(buttonPanel);
        southPnl.add(loginLinkLbl); 
        mainPnl.add(southPnl, BorderLayout.SOUTH);
    }
    
    private void guiBackground() {
        backgroundPnl = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image img = new ImageIcon(getClass().getResource("/za/ac/cput/librarysystem/background.jpg")).getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Failed to load background image: " + e.getMessage());
                }
            }
        };
        backgroundPnl.setLayout(new BorderLayout());
        backgroundPnl.add(mainPnl, BorderLayout.CENTER);
        setContentPane(backgroundPnl);
    }
    
    private void guiAddListeners() {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSignUp();
            }
        });
        
        loginLinkLbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }
    
    private void processSignUp() {
        try {
            String username = usernameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleComboBox.getSelectedItem().toString();  // Capture the selected role
            
            UserDAO userDAO = new UserDAO();
            boolean signUpSuccess = userDAO.signUpUser(username, email, phone, password, role);  // Pass role to DAO
            
            if (signUpSuccess) {
                JOptionPane.showMessageDialog(this, "Sign-up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                new LoginPage().setVisible(true);  
                dispose();  
            } else {
                JOptionPane.showMessageDialog(this, "Sign-up failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);  // Reset role selection
    }
}


