/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.librarysystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.*;

/**
 *
 * @author Sabura11
 */
public class AccountPageGui extends JFrame {

    JButton topmenuBtn, checkOutBtn, logOutBtn;
    JPanel pnlSouth, pnlNorth, pnlEast, pnlWest, pnlCenter;
    JLabel lblAccount, lblOverdue, lblAvailable, lblFine, lblfineAmount, lblLoanedBooks, lblbooksNo;
    JLabel blank1;

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
        blank1 = new JLabel("                                         ");

    }

    public void setGui() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
        pnlNorth.setLayout(new GridLayout(1, 1));
        pnlSouth.setLayout(new GridLayout(1, 3));
        pnlCenter.setLayout(new GridLayout(3, 2));
        pnlWest.setLayout(new GridLayout(5, 5));

        pnlNorth.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        pnlNorth.add(lblAccount);
        
        
        pnlCenter.add(lblOverdue);
        pnlCenter.add(lblAvailable);
        pnlCenter.add(lblFine);
        pnlCenter.add(lblfineAmount);
        pnlCenter.add(lblLoanedBooks);
        pnlCenter.add(lblbooksNo);
        pnlSouth.add(topmenuBtn);
        pnlSouth.add(checkOutBtn);
        pnlSouth.add(logOutBtn);
        pnlWest.add(blank1);
        

        this.add(pnlSouth, BorderLayout.SOUTH);
        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlWest, BorderLayout.WEST);

    }

}
