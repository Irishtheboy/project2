package za.ac.cput.librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AccountPageGui extends JFrame implements ActionListener {

    private JButton topmenuBtn, checkOutBtn, logOutBtn;
    private JPanel pnlSouth, pnlNorth, pnlCenter;
    private JLabel lblAccount, lblOverdue, lblAvailable, lblFine, lblfineAmount, lblLoanedBooks, lblbooksNo;
    private BufferedImage backgroundImage;

    public AccountPageGui() {
        super("Account");

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("C:/Users/Sabura11/Documents/NetBeansProjects/LibrarySystem/src/main/Resources/bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        topmenuBtn = new JButton("Top Menu");
        checkOutBtn = new JButton("Check out");
        logOutBtn = new JButton("Log Out");

        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
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

        setGui();
    }

    public void setGui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        // Set the background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        this.setContentPane(backgroundPanel);

        pnlNorth.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        pnlNorth.add(lblAccount, gbc);
        pnlNorth.setOpaque(false);

        pnlCenter.setLayout(new GridBagLayout());
        pnlCenter.setOpaque(false);
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
        pnlSouth.setOpaque(false);
        pnlSouth.add(topmenuBtn);
        pnlSouth.add(checkOutBtn);
        pnlSouth.add(logOutBtn);

        backgroundPanel.add(pnlNorth, BorderLayout.NORTH);
        backgroundPanel.add(pnlSouth, BorderLayout.SOUTH);
        backgroundPanel.add(pnlCenter, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
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




