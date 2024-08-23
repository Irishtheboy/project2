/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.UserDAO;
import java.awt.BorderLayout;
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
    JLabel titleLbl, usernameLbl, emailLbl, phoneLbl, passwordLbl, loginLinkLbl;
    JTextField usernameField, emailField, phoneField;
    JPasswordField passwordField;
    JButton signUpButton;
    
    public SignupPageGui() {
        
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setResizable(true);
        
        guiSetUp();
        guiLayout();
        guiBackground();
        guiAddListeners();
        
        setContentPane(backgroundPnl);
    }
    private void guiSetUp(){    
        mainPnl = new JPanel(new BorderLayout());
        westPnl = new JPanel(new GridLayout(4, 1, 10, 10));
        eastPnl = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPnl = new JPanel(new GridLayout(1, 2, 10, 10));
        southPnl = new JPanel(new GridLayout(2, 1, 10, 10));
        
        backgroundPnl = new JPanel();
        
        titleLbl = new JLabel("Sign-up", SwingConstants.CENTER);
        usernameLbl = new JLabel("Username:", SwingConstants.CENTER);
        emailLbl = new JLabel("Email:", SwingConstants.CENTER);
        phoneLbl = new JLabel("Phone:", SwingConstants.CENTER);
        passwordLbl = new JLabel("Password", SwingConstants.CENTER);
        loginLinkLbl = new JLabel("<html>Back to <a href=''>login</a></html>", SwingConstants.CENTER);
        
        usernameField = new JTextField(15);
        emailField = new JTextField(15);
        phoneField = new JTextField(15);
        passwordField = new JPasswordField(15);
        
        signUpButton = new JButton("Sign-Up");
        
        
    }
    
    private void guiLayout() {
        titleLbl.setFont(new Font("Arial", Font.BOLD,24));
        mainPnl.add(titleLbl, BorderLayout.NORTH);
        
        westPnl.add(usernameLbl);
        westPnl.add(emailLbl);
        westPnl.add(phoneLbl);
        westPnl.add(passwordLbl);
        
        eastPnl.add(usernameField);
        eastPnl.add(emailField);
        eastPnl.add(phoneField);
        eastPnl.add(passwordField);
        
        centerPnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPnl.add(westPnl);
        centerPnl.add(eastPnl);
        
        mainPnl.add(centerPnl, BorderLayout.CENTER);
        

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(signUpButton);
        southPnl.add(buttonPanel);
        southPnl.add(loginLinkLbl);
        mainPnl.add(southPnl, BorderLayout.SOUTH);
        
        loginLinkLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
                    JOptionPane.showMessageDialog(this, "Failed to load background image: " + e.getMessage ());


                }
            }
        };
        backgroundPnl.setLayout(new BorderLayout());
        backgroundPnl.add(mainPnl, BorderLayout.CENTER);
        setContentPane(backgroundPnl);
    }
    
    private void guiAddListeners(){
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSignUp();
            }
        });
        
        loginLinkLbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Sign-up functionality goes here.");
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }
    
    // Franco, Oratile, Naqeebah and mfana
private void processSignUp() {
    try {
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());
        
        

        UserDAO userDAO = new UserDAO();
        boolean signUpSuccess = userDAO.signUpUser(username, email, phone, password);
        
        if (signUpSuccess) {
            JOptionPane.showMessageDialog(this, "Sign-up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            System.out.println("The Database has captured all the details, you can check in the table");
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
        
    }
    
    
}
