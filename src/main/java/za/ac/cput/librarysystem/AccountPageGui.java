package za.ac.cput.librarysystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AccountPageGui extends JFrame implements ActionListener {

    JButton topmenuBtn, checkOutBtn, logOutBtn;
    JPanel pnlSouth, pnlNorth, pnlEast, pnlWest, pnlCenter;
    JLabel lblAccount, lblOverdue, lblAvailable, lblFine, lblfineAmount, lblLoanedBooks, lblbooksNo;

    public AccountPageGui() {
        super("Account");
        checkOutBtn = new JButton("Check out");
        topmenuBtn = new JButton("top Menu");
        logOutBtn = new JButton("Log Out");

        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlEast = new JPanel();
        pnlWest = new JPanel();
        pnlCenter = new JPanel();

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

        this.add(pnlSouth, BorderLayout.SOUTH);
        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlWest, BorderLayout.WEST);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOutBtn) {
            JOptionPane.showMessageDialog(this, "Logged out");
            //this.topMenu();
        } else if (e.getSource() == checkOutBtn) {
            JOptionPane.showMessageDialog(this, "checked out success");
            //this.setSignUp();
        } else if (e.getSource() == topmenuBtn) {
            JOptionPane.showMessageDialog(this, "back to top menu");
        } 

    }
   
}

