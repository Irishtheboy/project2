package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.UserDAO;
import ac.za.cput.librarysystem.dao.BookDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AccountPageGui extends JFrame implements ActionListener {

    private JButton topMenuBtn, checkOutBtn, logOutBtn, paymentsBtn;
    private JPanel pnlSouth, pnlNorth, pnlCenter;
    private JLabel lblAccount;
    private BufferedImage backgroundImage;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private String username; // Store the username of the logged-in user
    private int userId;
    private JTable accountTable; // Table to display account information
    private JScrollPane scrollPane;

    public AccountPageGui() {
        super("Account");
        this.userId = userId;
        userDAO = new UserDAO();  // Initialize UserDAO
        bookDAO = new BookDAO();  // Initialize BookDAO
        this.username = username; // Set the username from the constructor

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(""));
        } catch (IOException e) {
            e.printStackTrace();
        }

        topMenuBtn = new JButton("Top Menu");
        checkOutBtn = new JButton("Check Out");
        logOutBtn = new JButton("Log Out");
        paymentsBtn = new JButton("Pay");

        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();

        lblAccount = new JLabel("Account", SwingConstants.CENTER);

        topMenuBtn.addActionListener(this);
        checkOutBtn.addActionListener(this);
        logOutBtn.addActionListener(this);
        paymentsBtn.addActionListener(this);

        // Set up the JTable with account information
        setUpAccountTable();

        setGui();
    }

    private void setUpAccountTable() {
        // Use methods from the DAO classes to fetch the actual data from the database
        int overdueBooks = bookDAO.getOverdueBooksCount(userId);  // Fetch number of overdue books for this user
        double fineAmount = userDAO.getFineAmount(userId);        // Fetch the total fine amount for this user
        int loanedBooks = bookDAO.getLoanedBooksCount(userId);    // Fetch the number of loaned books for this user

        // Populate the table with dynamic data from the database
        String[] columnNames = {"Details", "Value"};
        Object[][] data = {
            {"Overdue Books:", overdueBooks},
            {"Fine Amount:", "R" + fineAmount},
            {"Loaned Books:", loanedBooks}
        };

        accountTable = new JTable(data, columnNames);
        scrollPane = new JScrollPane(accountTable);
    }

    private void setGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Create and set up the menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Set up the background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblAccount);
        pnlNorth.setOpaque(false);

        pnlCenter.setLayout(new BorderLayout());
        pnlCenter.add(scrollPane, BorderLayout.CENTER); // Add the JTable inside the JScrollPane to the center panel

        pnlSouth.setLayout(new GridLayout(1, 4));
        pnlSouth.add(topMenuBtn);
        pnlSouth.add(checkOutBtn);
        pnlSouth.add(logOutBtn);
        pnlSouth.add(paymentsBtn);
        pnlSouth.setOpaque(false);

        backgroundPanel.add(pnlNorth, BorderLayout.NORTH);
        backgroundPanel.add(pnlCenter, BorderLayout.CENTER);
        backgroundPanel.add(pnlSouth, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOutBtn) {
            JOptionPane.showMessageDialog(this, "Logged out");
            dispose();
        } else if (e.getSource() == checkOutBtn) {
            JOptionPane.showMessageDialog(this, "Checked out successfully");
            new CheckoutPage();
            dispose();
        } else if (e.getSource() == topMenuBtn) {
            JOptionPane.showMessageDialog(this, "Returning to top menu");
            new TopMenu();
            dispose();
        } else if (e.getSource() == paymentsBtn) {
            // Get the total fine amount for the user
            double fineAmount = userDAO.getFineAmount(userId); // Now userId is available
            if (fineAmount > 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "You have a fine of R" + fineAmount + ". Do you want to pay it now?",
                        "Confirm Payment",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Proceed to pay the fine
                    boolean paymentSuccess = userDAO.payFine(userId); // Call the payFine method
                    if (paymentSuccess) {
                        JOptionPane.showMessageDialog(this, "Fine paid successfully.");
                        // Update the fine amount in the table
                        accountTable.setValueAt("R0", 1, 1);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to process payment. Please try again.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "You have no outstanding fines.");
            }
        }
    }

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
