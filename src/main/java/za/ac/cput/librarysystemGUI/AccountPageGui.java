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
    private JLabel lblAccount, lblOverdue, lblAvailable, lblFine, lblFineAmount, lblLoanedBooks, lblBooksNo;
    private BufferedImage backgroundImage;
     private UserDAO userDAO;
    private BookDAO bookDAO;    private String username; // Store the username of the logged-in user

    public AccountPageGui() {
        super("Account");
        userDAO = new UserDAO();  // Initialize UserDAO
        bookDAO = new BookDAO();  // Initialize BookDAO
         this.username = username; // Set the username from the constructor
        userDAO = new UserDAO(); // Initialize UserDAO

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
        lblOverdue = new JLabel("Overdue Books:", SwingConstants.RIGHT);
        lblAvailable = new JLabel("3", SwingConstants.LEFT); 
        lblFine = new JLabel("Fine Amount:", SwingConstants.RIGHT);
        lblFineAmount = new JLabel("R50", SwingConstants.LEFT); 
        lblLoanedBooks = new JLabel("Loaned Books:", SwingConstants.RIGHT);
        lblBooksNo = new JLabel("2", SwingConstants.LEFT); 

        topMenuBtn.addActionListener(this);
        checkOutBtn.addActionListener(this);
        logOutBtn.addActionListener(this);
        paymentsBtn.addActionListener(this);
        
       
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

        pnlCenter.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlCenter.add(lblOverdue, gbc);

        gbc.gridx = 1;
        pnlCenter.add(lblAvailable, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlCenter.add(lblFine, gbc);

        gbc.gridx = 1;
        pnlCenter.add(lblFineAmount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlCenter.add(lblLoanedBooks, gbc);

        gbc.gridx = 1;
        pnlCenter.add(lblBooksNo, gbc);

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
        } else if(e.getSource() == paymentsBtn){
            JOptionPane.showConfirmDialog(this, "Are you sure you want to pay off your "+lblFineAmount.getText());
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

