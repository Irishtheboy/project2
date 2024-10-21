package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.UserDAO;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author oratile
 */
public class SignupPageGui extends JFrame {
    private JPanel mainPnl, centerPnl, southPnl, backgroundPnl;
    private JLabel titleLbl, usernameLbl, emailLbl, phoneLbl, passwordLbl, roleLbl, loginLinkLbl;
    private JTextField usernameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;  // Add this for roles
    private JButton signUpButton;

    public SignupPageGui() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(true);
        
        guiSetUp();
        guiLayout();
        guiBackground();
        guiAddListeners();
        
        setContentPane(backgroundPnl);
    }

    private void guiSetUp() {
        mainPnl = new JPanel(new BorderLayout());
        centerPnl = new JPanel(new GridBagLayout());
        southPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

        backgroundPnl = new JPanel();

        titleLbl = new JLabel("Sign-up", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 24));
        titleLbl.setForeground(new Color(50, 150, 250));

        usernameLbl = new JLabel("Username:");
        emailLbl = new JLabel("Email:");
        phoneLbl = new JLabel("Phone:");
        passwordLbl = new JLabel("Password:");
        roleLbl = new JLabel("Role:");

        usernameField = new JTextField(15);
        emailField = new JTextField(15);
        phoneField = new JTextField(15);
        passwordField = new JPasswordField(15);
        
        String[] roles = {"Member", "Admin"};
        roleComboBox = new JComboBox<>(roles);

        signUpButton = new JButton("Sign-Up");
        signUpButton.setBackground(new Color(50, 150, 250));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Initialize login link label
        loginLinkLbl = new JLabel("<html>Already a member? <a href='#'>Login here</a></html>", SwingConstants.CENTER);
        loginLinkLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLinkLbl.setForeground(new Color(50, 150, 250)); // Optional: Set link color
    }

    private void guiLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        mainPnl.add(titleLbl, BorderLayout.NORTH);
        
        // Center panel layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        centerPnl.add(usernameLbl, gbc);
        
        gbc.gridx = 1;
        centerPnl.add(usernameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPnl.add(emailLbl, gbc);
        
        gbc.gridx = 1;
        centerPnl.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPnl.add(phoneLbl, gbc);
        
        gbc.gridx = 1;
        centerPnl.add(phoneField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPnl.add(passwordLbl, gbc);
        
        gbc.gridx = 1;
        centerPnl.add(passwordField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPnl.add(roleLbl, gbc);
        
        gbc.gridx = 1;
        centerPnl.add(roleComboBox, gbc);
        
        mainPnl.add(centerPnl, BorderLayout.CENTER);
        
        
        southPnl.add(signUpButton);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignupPageGui().setVisible(true));
    }
}
