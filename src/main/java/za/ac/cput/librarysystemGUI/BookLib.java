package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.BookDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookLib extends JFrame implements ActionListener {

    private JButton accountbtn, checkoutbtn, topMenubtn;
    private BookDAO bookDAO; // Assuming you have a BookDAO class to manage book data
    private List<Book> books; // List to hold the books retrieved from the database
    private String adminPassword = "admin123"; // Admin password for approval

    public BookLib() {
        bookDAO = new BookDAO(); // Initialize your BookDAO
//        books = bookDAO.getAllBookss(); // Retrieve all books from the database

        setTitle("Book Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 600);

        createMenuBar();

        JLabel title = new JLabel("BOOKS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel bookPanel = createBookPanel();
        add(bookPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        accountbtn = new JButton("Account");
        checkoutbtn = new JButton("Check out");
        topMenubtn = new JButton("Top Menu");

        bottomPanel.add(accountbtn);
        bottomPanel.add(checkoutbtn);
        bottomPanel.add(topMenubtn);

        accountbtn.addActionListener(this);
        checkoutbtn.addActionListener(this);
        topMenubtn.addActionListener(this);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        for (Book book : books) {
            JPanel bookPanelItem = new JPanel(new BorderLayout());
            JLabel bookPic = new JLabel(book.getImageIcon());
            bookPanelItem.add(bookPic, BorderLayout.CENTER);

            JPanel textAndButtonPanel = new JPanel(new BorderLayout());

            JLabel bookName = new JLabel(book.getName(), SwingConstants.CENTER);
            JLabel bookAuth = new JLabel(book.getAuthor(), SwingConstants.CENTER);

            JButton trolleyBtn = new JButton("Add to cart", new ImageIcon("trolley.png"));
            trolleyBtn.setActionCommand(book.getName());

            trolleyBtn.addActionListener(e -> {
                String bookNameClicked = e.getActionCommand();
                askAdminForPermission(bookNameClicked);
            });

            JPanel namePanel = new JPanel(new BorderLayout());
            namePanel.add(bookName, BorderLayout.CENTER);

            JPanel authorPanel = new JPanel(new BorderLayout());
            authorPanel.add(bookAuth, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(trolleyBtn, BorderLayout.CENTER);

            textAndButtonPanel.add(namePanel, BorderLayout.NORTH);
            textAndButtonPanel.add(authorPanel, BorderLayout.CENTER);
            textAndButtonPanel.add(buttonPanel, BorderLayout.SOUTH);

            bookPanelItem.add(textAndButtonPanel, BorderLayout.SOUTH);
            bookPanel.add(bookPanelItem);
        }

        return bookPanel;
    }

    // Method to ask admin for permission to reserve the book
    private void askAdminForPermission(String bookNameClicked) {
        // Dialog to ask for admin password
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Admin Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Admin Approval", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String enteredPassword = new String(passwordField.getPassword());
            if (enteredPassword.equals(adminPassword)) {
                JOptionPane.showMessageDialog(this, "Book '" + bookNameClicked + "' has been reserved successfully.");
                // Code to reserve the book (e.g., update the database) can be added here
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect admin password. Reservation denied.");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accountbtn) {
            new AccountPageGui();
            dispose();
        } else if (e.getSource() == checkoutbtn) {
            JOptionPane.showMessageDialog(null, "Checked out successfully");
            new CheckoutPage();
            dispose();
        } else if (e.getSource() == topMenubtn) {
            JOptionPane.showMessageDialog(null, "Back to top menu");
            dispose();
        }
    }

    // Book class remains unchanged; you might have a separate Book class
    private static class Book {
        private String name;
        private String author;
        private String imagePath;

        public Book(String name, String author, String imagePath) {
            this.name = name;
            this.author = author;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public String getAuthor() {
            return author;
        }

        public ImageIcon getImageIcon() {
            return new ImageIcon(imagePath);
        }
    }
}
