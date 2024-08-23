package za.ac.cput.librarysystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame {
    private JPanel pnlCenter, pnlSouth;
    private JButton returnBtn, loginBtn, exitBtn;
    private JTextField ustext;
    private JPasswordField pstext;
    private JLabel username, password;

    public AdminLogin() {
        super("Admin Login");
        
        // Initialize components
        username = new JLabel("Username:");
        password = new JLabel("Password:");
        ustext = new JTextField(15);
        pstext = new JPasswordField(15);
        returnBtn = new JButton("Return");
        loginBtn = new JButton("Login");
        exitBtn = new JButton("Exit");

        // Set up panel for input fields and labels
        pnlCenter = new JPanel();
        pnlCenter.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, horizontal and vertical gap of 10
        pnlCenter.add(username);
        pnlCenter.add(ustext);
        pnlCenter.add(password);
        pnlCenter.add(pstext);

        // Set up panel for buttons
        pnlSouth = new JPanel();
        pnlSouth.setLayout(new FlowLayout());
        pnlSouth.add(returnBtn);
        pnlSouth.add(loginBtn);
        pnlSouth.add(exitBtn);

        // Add panels to frame
        this.setLayout(new BorderLayout());
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Button Action Listeners
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement login logic here
                String user = ustext.getText();
                String pass = new String(pstext.getPassword());
                if (validateLogin(user, pass)) {
                    JOptionPane.showMessageDialog(null, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Proceed to admin functionalities
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                dispose(); // Closes the current window
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });

        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 200);
        this.setLocationRelativeTo(null); 
        this.setVisible(true);
    }

    private boolean validateLogin(String username, String password) {
        
        return "admin".equals(username) && "password".equals(password);
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}

