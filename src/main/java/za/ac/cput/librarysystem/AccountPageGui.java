package za.ac.cput.librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AccountPageGui extends JFrame implements ActionListener {

    JButton topmenuBtn, checkOutBtn, logOutBtn;
    JPanel pnlSouth, pnlNorth, pnlEast, pnlWest, pnlCenter, backgroundPanel;
    JLabel lblAccount, lblOverdue, lblAvailable, lblFine, lblfineAmount, lblLoanedBooks, lblbooksNo;
    JLabel background;

    public AccountPageGui() {
        super("Account");

        checkOutBtn = new JButton("Check out");
        topmenuBtn = new JButton("Top Menu");
        logOutBtn = new JButton("Log Out");

        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlEast = new JPanel();
        pnlWest = new JPanel();
        pnlCenter = new JPanel();
        backgroundPanel = new JPanel(null);

        lblAccount = new JLabel("Account");
        lblOverdue = new JLabel("Overdue");
        lblAvailable = new JLabel("A");
        lblFine = new JLabel("Fine");
        lblfineAmount = new JLabel("R0");
        lblLoanedBooks = new JLabel("Loaned Books");
        lblbooksNo = new JLabel("5");

        topmenuBtn.addActionListener(this);
        checkOutBtn.addActionListener(this);
        logOutBtn.addActionListener(this);

        background = new JLabel(new ImageIcon("bg.jpeg"));
        background.setBounds(0, 0, 500, 500);
        backgroundPanel.add(background);

        setGui();
    }

    public void setGui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);

        pnlNorth.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        pnlNorth.add(lblAccount, gbc);

        pnlCenter.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 70, 40, 70);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlCenter.add(lblOverdue, gbc);

        gbc.gridx = 1;
        pnlCenter.add(lblAvailable, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlCenter.add(lblFine, gbc);

        gbc.gridx = 1;
        pnlCenter.add(lblfineAmount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlCenter.add(lblLoanedBooks, gbc);

        gbc.gridx = 1;
        pnlCenter.add(lblbooksNo, gbc);

        pnlSouth.setLayout(new GridLayout(1, 3));
        pnlSouth.add(topmenuBtn);
        pnlSouth.add(checkOutBtn);
        pnlSouth.add(logOutBtn);

        this.setLayout(new BorderLayout());
        this.add(pnlSouth, BorderLayout.SOUTH);
        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(backgroundPanel, BorderLayout.CENTER);
        this.add(pnlCenter, BorderLayout.CENTER);
          // Add the background panel here

        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOutBtn) {
            JOptionPane.showMessageDialog(this, "Logged out");
        } else if (e.getSource() == checkOutBtn) {
            JOptionPane.showMessageDialog(this, "Checked out successfully");
        } else if (e.getSource() == topmenuBtn) {
            JOptionPane.showMessageDialog(this, "Back to top menu");
        }
    }
}



