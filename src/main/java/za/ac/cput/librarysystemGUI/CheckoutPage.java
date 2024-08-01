package za.ac.cput.librarysystemGui;

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

        accountBtn = new JButton("Account");
        confirmBtn = new JButton("Confirm");
        logOutBtn = new JButton("Log Out");

        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();

        lblCheckOut = new JLabel("Check Out", JLabel.CENTER);
        lblBookTitle = new JLabel("Book Title:");
        lblDueDate = new JLabel("Due Date (YYYY-MM-DD):");
        lblMemberId = new JLabel("Member ID:");

        txtBookTitle = new JTextField(20);
        txtDueDate = new JTextField(20);
        txtMemberId = new JTextField(20);

        accountBtn.addActionListener(this);
        confirmBtn.addActionListener(this);
        logOutBtn.addActionListener(this);

        background = new JLabel(new ImageIcon("check.jpeg"));
        background.setLayout(new BorderLayout());

        setGui();
    }

    public void setGui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        pnlNorth.setLayout(new BorderLayout());
        pnlNorth.add(lblCheckOut, BorderLayout.CENTER);
        pnlNorth.setOpaque(false);

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

        pnlSouth.setLayout(new GridLayout(1, 3));
        pnlSouth.add(accountBtn);
        pnlSouth.add(confirmBtn);
        pnlSouth.add(logOutBtn);
        pnlSouth.setOpaque(false);

        background.add(pnlNorth, BorderLayout.NORTH);
        background.add(pnlCenter, BorderLayout.CENTER);
        background.add(pnlSouth, BorderLayout.SOUTH);

        this.setContentPane(background);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOutBtn) {
            JOptionPane.showMessageDialog(this, "Logged out");
            dispose();
        } else if (e.getSource() == confirmBtn) {
            if (txtBookTitle.getText().isEmpty() || txtDueDate.getText().isEmpty() || txtMemberId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields");
            
            } else {
                JOptionPane.showMessageDialog(this, "Book checked out successfully");
                new SubmitFrameGui().setVisible(true);
            }
        } else if (e.getSource() == accountBtn) {
            JOptionPane.showMessageDialog(this, "Returning to previous menu");
            new AccountPageGui();
            dispose();
        }
    }

}
