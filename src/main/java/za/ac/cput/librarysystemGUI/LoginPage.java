package za.ac.cput.librarysystemGui;
import ac.za.cput.librarysystem.dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import za.ac.cput.librarysystemGui.AudioBook.UserSession;

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

        mainPanel = new JPanel();
        headingLabel = new JLabel("Login", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(Color.BLACK);

        userLabel = new JLabel("Username");
        userLabel.setForeground(Color.BLACK);

        userText = new JTextField(20);

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);

        passwordText = new JPasswordField(20);

        btnLogin = new JButton("LOGIN");

        signUpLabel = new JLabel("<html>Not a member? <a href='#'>Sign up here</a></html>");
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.setForeground(Color.BLACK);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        mainPanel.setLayout(null);
        add(mainPanel, BorderLayout.CENTER);

        headingLabel.setBounds(0, 10, 400, 30);
        mainPanel.add(headingLabel);

        userLabel.setBounds(10, 60, 100, 25);
        userText.setBounds(120, 60, 180, 25);
        mainPanel.add(userLabel);
        mainPanel.add(userText);

        passwordLabel.setBounds(10, 100, 100, 25);
        passwordText.setBounds(120, 100, 180, 25);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordText);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBounds(10, 150, 350, 70);
        buttonPanel.add(btnLogin);
        mainPanel.add(buttonPanel);

        JPanel signUpPanel = new JPanel(new FlowLayout());
        signUpPanel.setBounds(10, 220, 350, 30);
        signUpPanel.add(signUpLabel);
        mainPanel.add(signUpPanel);
    }

    // Teyanah & Franco
    private void addListeners() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                UserDAO userDAO = new UserDAO();
                if (userDAO.validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome!");
                    System.out.println("The User has validated and compared to the database table....");
                    new TopMenu().setVisible(true);
                    UserSession.setLoggedInUsername(username);
                    dispose();
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
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}