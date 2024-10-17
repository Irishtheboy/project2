package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import za.ac.cput.librarysystemGUI.AdminDashBoard;
import ac.za.cput.librarysystem.domain.UserSession;

public class LoginPage extends JFrame {

    private JPanel mainPanel;
    private JLabel userLabel, passwordLabel, signUpLabel, headingLabel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton btnLogin;

    public LoginPage() {
        initializeComponents();
        setupLayout();
        addListeners();
        finalizeLayout();
    }

    private void initializeComponents() {
        setTitle("User Login");

        // Main panel with rounded corners and shadow
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255, 230));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 250), 2));
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(true); // Ensure panel is opaque

        headingLabel = new JLabel("Login", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(new Color(50, 150, 250));

        userLabel = new JLabel("Username");
        userLabel.setForeground(Color.BLACK);

        userText = new JTextField(20);
        userText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);

        passwordText = new JPasswordField(20);
        passwordText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(new Color(50, 150, 250));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signUpLabel = new JLabel("<html>Not a member? <a href='#'>Sign up here</a></html>");
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.setForeground(new Color(50, 150, 250));
    }

    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(headingLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(passwordText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(btnLogin, gbc);

        gbc.gridy = 4;
        mainPanel.add(signUpLabel, gbc);
    }

    private void addListeners() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                UserDAO userDAO = new UserDAO();
                String role = userDAO.validateLogin(username, password);

                if (role != null) {
                    JOptionPane.showMessageDialog(null, "Welcome!");

                    // Set the logged-in username
                    UserSession.setLoggedInUsername(username);

                    // Retrieve and set the user ID
                    UserSession.retrieveAndSetUserId();

                    // Check if the user ID was set successfully
                    int userId = UserSession.getLoggedInUserId();
                    System.out.println("User ID retrieved after login: " + userId); // Debugging output

                    if ("admin".equalsIgnoreCase(role)) {
                        System.out.println("Admin logged in");
                        AdminDashBoard adminDashboard = new AdminDashBoard();
                        adminDashboard.setVisible(true);
                    } else {
                        System.out.println("User logged in");
                        new TopMenu().setVisible(true);  // Redirect to regular user page
                    }

                    dispose();  // Close the login window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignupPageGui().setVisible(true);
                dispose();
            }
        });
    }

    private void finalizeLayout() {
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
