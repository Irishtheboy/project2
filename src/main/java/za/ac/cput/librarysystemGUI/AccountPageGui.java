package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.UserDAO;
import ac.za.cput.librarysystem.dao.BookDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AccountPageGui extends JFrame implements ActionListener {

    private JButton topMenuBtn, feedbackBtn, logOutBtn, paymentsBtn;
    private JPanel pnlSouth, pnlNorth, pnlCenter;
    private JLabel lblAccount, lblFees;
    private BufferedImage backgroundImage;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private String username;
    private int userId;

    public AccountPageGui(int userId, String username) {
        super("Account");
        this.userId = userId;
        this.username = username;
        userDAO = new UserDAO();  // Initialize UserDAO
        bookDAO = new BookDAO();  // Initialize BookDAO

        // Set up fees label and retrieve current outstanding fees
        double fineAmount = userDAO.getFineAmount(userId);
        lblFees = new JLabel("Outstanding Fees: R" + fineAmount, SwingConstants.CENTER);

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(""));
        } catch (IOException e) {
            e.printStackTrace();
        }

        topMenuBtn = new JButton("Top Menu");
        feedbackBtn = new JButton("Feedback");
        logOutBtn = new JButton("Log Out");
        paymentsBtn = new JButton("Pay");

        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();

        lblAccount = new JLabel("Account Overview", SwingConstants.CENTER);

        feedbackBtn.addActionListener(this);
        topMenuBtn.addActionListener(this);
        logOutBtn.addActionListener(this);
        paymentsBtn.addActionListener(this);

        // Set up the main GUI
        setGui();
    }

    private void setGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Create and set up the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));  // Exit the application
        JMenuItem feedbackItem = new JMenuItem("Feedback");
        feedbackItem.addActionListener(this);  // Add ActionListener for feedback
        fileMenu.add(feedbackItem);
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

        pnlNorth.setLayout(new GridLayout(2, 1));  // Set the layout to hold 2 components
        pnlNorth.add(lblAccount);
        pnlNorth.add(lblFees);  // Add the fees label to the north panel
        pnlNorth.setOpaque(false);

        // Create a panel for the rented books section
        pnlCenter.setLayout(new GridLayout(0, 1));  // Adjust layout as needed
        List<Object[]> rentedBooks = bookDAO.getRentedBooks(userId);
        if (rentedBooks.isEmpty()) {
            JLabel noBooksLabel = new JLabel("No books currently rented.", SwingConstants.CENTER);
            pnlCenter.add(noBooksLabel);
        } else {
            for (Object[] book : rentedBooks) {
                JPanel bookPanel = new JPanel(new GridLayout(1, 2));
                bookPanel.add(new JLabel("Title: " + book[1]));
                bookPanel.add(new JLabel("Return Date: " + book[3]));
                pnlCenter.add(bookPanel);
            }
        }

        pnlSouth.setLayout(new GridLayout(1, 4));  // Adjust column count if needed
        pnlSouth.add(topMenuBtn);
        pnlSouth.add(feedbackBtn);
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
        } else if (e.getSource() == feedbackBtn) {
            JOptionPane.showMessageDialog(this, "Feedback page");
            new FeedbackPage();
            dispose();
        } else if (e.getSource() == topMenuBtn) {
            JOptionPane.showMessageDialog(this, "Returning to top menu");
            new TopMenu();
            dispose();
        } else if (e.getSource() == paymentsBtn) {
            double fineAmount = userDAO.getFineAmount(userId);
            if (fineAmount > 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "You have a fine of R" + fineAmount + ". Do you want to pay it now?",
                        "Confirm Payment", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean paymentSuccess = userDAO.payFine(userId);
                    if (paymentSuccess) {
                        JOptionPane.showMessageDialog(this, "Fine paid successfully.");
                        lblFees.setText("Outstanding Fees: R0");  // Update the fees label to show no outstanding fees
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
