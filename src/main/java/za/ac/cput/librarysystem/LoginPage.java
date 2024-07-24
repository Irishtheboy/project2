package za.ac.cput.librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class LoginPage extends JFrame {

    private JPanel ePanel, wPanel, sPanel;
    private JLabel userLabel, passwordLabel, signUpLabel, headingLabel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton btnLogin;
    private BufferedImage backgroundImage;
    private Map<String, String> credentials;

    public LoginPage() {
        initializeComponents();
        setupLayout();
        addListeners();
        finalizeLayout();
    }

    private void initializeComponents() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("C:/Users/Sabura11/Documents/NetBeansProjects/LibrarySystem/src/main/Resources/bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load user credentials from file
        credentials = loadCredentialsFromFile("users.txt");

        setTitle("User Login");

        wPanel = new JPanel();
        ePanel = new JPanel();
        sPanel = new JPanel();

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
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        wPanel.setLayout(new GridLayout(2, 1, 10, 10));
        wPanel.setOpaque(false);
        ePanel.setLayout(new GridLayout(2, 1, 10, 10));
        ePanel.setOpaque(false);
        sPanel.setLayout(new FlowLayout());
        sPanel.setOpaque(false);

        headingLabel.setBounds(0, 10, 400, 30);
        backgroundPanel.add(headingLabel);

        userLabel.setBounds(10, 60, 100, 25);
        userText.setBounds(120, 60, 180, 25);
        backgroundPanel.add(userLabel);
        backgroundPanel.add(userText);

        passwordLabel.setBounds(10, 100, 100, 25);
        passwordText.setBounds(120, 100, 180, 25);
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordText);

        sPanel.setBounds(10, 150, 350, 70);
        sPanel.add(btnLogin);
        sPanel.add(signUpLabel);
        backgroundPanel.add(sPanel);
    }

    private void addListeners() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (isValidLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome!");
                    new BookLib();
                } else {
                    JOptionPane.showMessageDialog(null, "Please try again.");
                }
            }
        });

        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Sign-up functionality goes here.");
            }
        });
    }

    private void finalizeLayout() {
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean isValidLogin(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }

    private Map<String, String> loadCredentialsFromFile(String filename) {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 2) {
                    credentials.put(details[0], details[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    // Inner class for background panel
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}





