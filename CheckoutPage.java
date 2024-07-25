
package za.ac.cput.librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Mfana
 */
public class CheckoutPage extends JFrame implements ActionListener {
    
    JButton accountBtn, confirmBtn, logOutBtn;
    JPanel pnlSouth, pnlNorth, pnlCenter;
    JLabel lblCheckOut, lblBookTitle, lblDueDate, lblMemberId;
    JTextField txtBookTitle, txtDueDate, txtMemberId;
    JLabel background;

    public CheckoutPage() {
        super("Library System - Check Out");

        // Initialize buttons
        accountBtn = new JButton("Account");
        confirmBtn = new JButton("Confirm");
        logOutBtn = new JButton("Log Out");

        // Initialize panels
        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();

        // Initialize labels
        lblCheckOut = new JLabel("Check Out", JLabel.CENTER);
        lblBookTitle = new JLabel("Book Title:");
        lblDueDate = new JLabel("Due Date (YYYY-MM-DD):");
        lblMemberId = new JLabel("Member ID:");

        // Initialize text fields
        txtBookTitle = new JTextField(20);
        txtDueDate = new JTextField(20);
        txtMemberId = new JTextField(20);

        // Add action listeners to buttons
        accountBtn.addActionListener(this);
        confirmBtn.addActionListener(this);
        logOutBtn.addActionListener(this);

        // Background label
        background = new JLabel(new ImageIcon("check.jpeg"));
        background.setLayout(new BorderLayout());

        // Set up GUI components
        setGui();
    }

    public void setGui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        // North panel setup
        pnlNorth.setLayout(new BorderLayout());
        pnlNorth.add(lblCheckOut, BorderLayout.CENTER);
        pnlNorth.setOpaque(false);

        // Center panel setup
        pnlCenter.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlCenter.add(lblBookTitle, gbc);

        gbc.gridx = 1;
        pnlCenter.add(txtBookTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlCenter.add(lblDueDate, gbc);

        gbc.gridx = 1;
        pnlCenter.add(txtDueDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlCenter.add(lblMemberId, gbc);

        gbc.gridx = 1;
        pnlCenter.add(txtMemberId, gbc);

        pnlCenter.setOpaque(false);

        // South panel setup
        pnlSouth.setLayout(new GridLayout(1, 3));
        pnlSouth.add(accountBtn);
        pnlSouth.add(confirmBtn);
        pnlSouth.add(logOutBtn);
        pnlSouth.setOpaque(false);

        // Add components to background
        background.add(pnlNorth, BorderLayout.NORTH);
        background.add(pnlCenter, BorderLayout.CENTER);
        background.add(pnlSouth, BorderLayout.SOUTH);

        // Add background to the frame
        this.setContentPane(background);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOutBtn) {
            JOptionPane.showMessageDialog(this, "Logged out");
        } else if (e.getSource() == confirmBtn) {
            // Here, you can add code to handle the actual book checkout process
            if (txtBookTitle.getText().isEmpty() || txtDueDate.getText().isEmpty() || txtMemberId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields");
            } else {
                JOptionPane.showMessageDialog(this, "Book checked out successfully");
            }
        } else if (e.getSource() == accountBtn) {
            // Return to the account page or previous menu
            JOptionPane.showMessageDialog(this, "Returning to previous menu");
        }
    }

}