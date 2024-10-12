package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.BookDAO;
import ac.za.cput.librarysystem.domain.UserSession;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import za.ac.cput.librarysystemGui.AccountPageGui;
import za.ac.cput.librarysystemGui.CheckoutPage;
import za.ac.cput.librarysystemGui.TopMenu;
import za.ac.cput.librarysystemGui.AccountPageGui;

public class BookLib extends JFrame implements ActionListener {

    private JButton accountbtn, checkoutbtn, topMenubtn;
    private JLabel loggedInUserLabel, rentalCountLabel;
    private int rentalCount = 0;
    private BookDAO bookDAO;
    private DefaultTableModel model;
    private JTable bookTable;
    private DefaultTableModel rentedBooksModel;
    private JTable rentedBooksTable;
    private JTextField searchField;
    private JButton searchButton;
    private String adminPassword = "admin123"; // Admin password for approval

    public BookLib() {
        bookDAO = new BookDAO();

        setTitle("Book Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 600);

        createMenuBar();
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tableBookPanel = createTableBookPanel();
        tabbedPane.addTab("Books", tableBookPanel);

        JPanel rentedBooksPanel = createRentedBooksPanel();
        tabbedPane.addTab("My Rented Books", rentedBooksPanel);

        JPanel borrowHistoryPanel = createBorrowHistoryPanel();
        tabbedPane.addTab("Borrow History", borrowHistoryPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loggedInUserLabel = new JLabel("Logged in as: " + UserSession.getLoggedInUsername());
        loggedInUserLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userPanel.add(loggedInUserLabel);

        JPanel rentalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rentalCountLabel = new JLabel("Books rented: " + rentalCount);
        rentalCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rentalPanel.add(rentalCountLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(userPanel, BorderLayout.WEST);
        topPanel.add(rentalPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

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
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private JPanel createTableBookPanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Book ID", "Title", "Author", "Available"};
        model = new DefaultTableModel(columnNames, 0);

        // Fetch all books from the database and populate the table
        List<Object[]> books = bookDAO.getAllBooks();
        for (Object[] book : books) {
            model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"});
        }

        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Rent Button
        JButton rentButton = new JButton("Rent Selected Book");
        rentButton.addActionListener(e -> rentSelectedBook());
        tablePanel.add(rentButton, BorderLayout.SOUTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search Book:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> searchBooks());

        tablePanel.add(searchPanel, BorderLayout.NORTH);

        return tablePanel;
    }

    private JPanel createRentedBooksPanel() {
        JPanel rentedPanel = new JPanel(new BorderLayout());
        String[] rentedColumnNames = {"Book ID", "Title", "Author", "Return Date"};
        rentedBooksModel = new DefaultTableModel(rentedColumnNames, 0);

        // Fetch rented books from the database and populate the table
        List<Object[]> rentedBooks = bookDAO.getRentedBooks(UserSession.getLoggedInUserId());
        for (Object[] rentedBook : rentedBooks) {
            rentedBooksModel.addRow(rentedBook);
        }

        rentedBooksTable = new JTable(rentedBooksModel);
        JScrollPane scrollPane = new JScrollPane(rentedBooksTable);
        rentedPanel.add(scrollPane, BorderLayout.CENTER);

        // Return Button
        JButton returnButton = new JButton("Return Selected Book");
        returnButton.addActionListener(e -> returnSelectedBook());
        rentedPanel.add(returnButton, BorderLayout.SOUTH);

        return rentedPanel;
    }

    private JPanel createBorrowHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        String[] historyColumnNames = {"Book ID", "Title", "Author", "Borrow Date", "Return Date"};
        DefaultTableModel borrowHistoryModel = new DefaultTableModel(historyColumnNames, 0);
        
        // Fetch borrow history data from the database and populate the table
        List<Object[]> borrowHistory = bookDAO.getBorrowHistory(UserSession.getLoggedInUserId());
        for (Object[] historyEntry : borrowHistory) {
            borrowHistoryModel.addRow(historyEntry);
        }

        JTable borrowHistoryTable = new JTable(borrowHistoryModel);
        JScrollPane scrollPane = new JScrollPane(borrowHistoryTable);
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        return historyPanel;
    }

    private void searchBooks() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            refreshAvailableBooksTable();
            return;
        }

        model.setRowCount(0);
        List<Object[]> books = bookDAO.getAllBooks();
        for (Object[] book : books) {
            String title = book[1].toString().toLowerCase();
            String author = book[2].toString().toLowerCase();
            if (title.contains(query) || author.contains(query)) {
                model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"});
            }
        }
    }

 private void rentSelectedBook() {
    int selectedRow = bookTable.getSelectedRow();
    if (selectedRow >= 0) {
        int bookId = (int) bookTable.getValueAt(selectedRow, 0);
        boolean isAvailable = "Yes".equals(bookTable.getValueAt(selectedRow, 3));

        if (isAvailable) {
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {"Admin Password:", passwordField};

            int option = JOptionPane.showConfirmDialog(null, message, "Admin Approval", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String enteredPassword = new String(passwordField.getPassword());

                if (enteredPassword.equals(adminPassword)) {
                    boolean success = bookDAO.rentBook(UserSession.getLoggedInUserId(), bookId);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Book rented successfully!");
                        rentalCount++;
                        rentalCountLabel.setText("Books rented: " + rentalCount);
                        model.setValueAt("No", selectedRow, 3); // Update availability in book table
                        updateRentedBooksTable(); // Call to update the rented books table
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to rent the book.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect admin password. Rental denied.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "This book is already rented out.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a book to rent.");
    }
}

private void updateRentedBooksTable() {
    rentedBooksModel.setRowCount(0); // Clear existing rows

    // Fetch updated rented books from the database
    List<Object[]> rentedBooks = bookDAO.getRentedBooks(UserSession.getLoggedInUserId());
    for (Object[] rentedBook : rentedBooks) {
        rentedBooksModel.addRow(rentedBook); // Add each rented book to the model
    }
}


    private void returnSelectedBook() {
        int selectedRow = rentedBooksTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) rentedBooksTable.getValueAt(selectedRow, 0);
            boolean success = bookDAO.returnBook(UserSession.getLoggedInUserId(), bookId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
                rentedBooksModel.removeRow(selectedRow);
                rentalCount--;
                rentalCountLabel.setText("Books rented: " + rentalCount);
                refreshAvailableBooksTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to return the book.");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void refreshAvailableBooksTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  
}



//import ac.za.cput.librarysystem.dao.BookDAO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//public class BookLib extends JFrame implements ActionListener {
//
//    private JButton accountbtn, checkoutbtn, topMenubtn;
//    private BookDAO bookDAO; // Assuming you have a BookDAO class to manage book data
//    private List<Book> books; // List to hold the books retrieved from the database
//    private String adminPassword = "admin123"; // Admin password for approval
//
//    public BookLib() {
//        bookDAO = new BookDAO(); // Initialize your BookDAO
////        books = bookDAO.getAllBookss(); // Retrieve all books from the database
//
//        setTitle("Book Library");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(880, 600);
//
//        createMenuBar();
//
//        JLabel title = new JLabel("BOOKS", SwingConstants.CENTER);
//        title.setFont(new Font("Arial", Font.BOLD, 24));
//        add(title, BorderLayout.NORTH);
//
//        JPanel bookPanel = createBookPanel();
//        add(bookPanel, BorderLayout.CENTER);
//
//        JPanel bottomPanel = new JPanel(new FlowLayout());
//        accountbtn = new JButton("Account");
//        checkoutbtn = new JButton("Check out");
//        topMenubtn = new JButton("Top Menu");
//
//        bottomPanel.add(accountbtn);
//        bottomPanel.add(checkoutbtn);
//        bottomPanel.add(topMenubtn);
//
//        accountbtn.addActionListener(this);
//        checkoutbtn.addActionListener(this);
//        topMenubtn.addActionListener(this);
//
//        add(bottomPanel, BorderLayout.SOUTH);
//
//        setVisible(true);
//    }
//
//    private void createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
//
//        JMenu fileMenu = new JMenu("File");
//        JMenuItem exitItem = new JMenuItem("Exit");
//        exitItem.addActionListener(e -> System.exit(0));
//        fileMenu.add(exitItem);
//
//        JMenu helpMenu = new JMenu("Help");
//        JMenuItem aboutItem = new JMenuItem("About");
//        helpMenu.add(aboutItem);
//
//        menuBar.add(fileMenu);
//        menuBar.add(helpMenu);
//
//        setJMenuBar(menuBar);
//    }
//
//    private JPanel createBookPanel() {
//        JPanel bookPanel = new JPanel(new GridLayout(2, 4, 10, 10));
//
//        for (Book book : books) {
//            JPanel bookPanelItem = new JPanel(new BorderLayout());
//            JLabel bookPic = new JLabel(book.getImageIcon());
//            bookPanelItem.add(bookPic, BorderLayout.CENTER);
//
//            JPanel textAndButtonPanel = new JPanel(new BorderLayout());
//
//            JLabel bookName = new JLabel(book.getName(), SwingConstants.CENTER);
//            JLabel bookAuth = new JLabel(book.getAuthor(), SwingConstants.CENTER);
//
//            JButton trolleyBtn = new JButton("Add to cart", new ImageIcon("trolley.png"));
//            trolleyBtn.setActionCommand(book.getName());
//
//            trolleyBtn.addActionListener(e -> {
//                String bookNameClicked = e.getActionCommand();
//                askAdminForPermission(bookNameClicked);
//            });
//
//            JPanel namePanel = new JPanel(new BorderLayout());
//            namePanel.add(bookName, BorderLayout.CENTER);
//
//            JPanel authorPanel = new JPanel(new BorderLayout());
//            authorPanel.add(bookAuth, BorderLayout.CENTER);
//
//            JPanel buttonPanel = new JPanel(new BorderLayout());
//            buttonPanel.add(trolleyBtn, BorderLayout.CENTER);
//
//            textAndButtonPanel.add(namePanel, BorderLayout.NORTH);
//            textAndButtonPanel.add(authorPanel, BorderLayout.CENTER);
//            textAndButtonPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//            bookPanelItem.add(textAndButtonPanel, BorderLayout.SOUTH);
//            bookPanel.add(bookPanelItem);
//        }
//
//        return bookPanel;
//    }
//
//    // Method to ask admin for permission to reserve the book
//    private void askAdminForPermission(String bookNameClicked) {
//        // Dialog to ask for admin password
//        JPasswordField passwordField = new JPasswordField();
//        Object[] message = {
//            "Admin Password:", passwordField
//        };
//
//        int option = JOptionPane.showConfirmDialog(null, message, "Admin Approval", JOptionPane.OK_CANCEL_OPTION);
//
//        if (option == JOptionPane.OK_OPTION) {
//            String enteredPassword = new String(passwordField.getPassword());
//            if (enteredPassword.equals(adminPassword)) {
//                JOptionPane.showMessageDialog(this, "Book '" + bookNameClicked + "' has been reserved successfully.");
//                // Code to reserve the book (e.g., update the database) can be added here
//            } else {
//                JOptionPane.showMessageDialog(this, "Incorrect admin password. Reservation denied.");
//            }
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == accountbtn) {
//            new AccountPageGui();
//            dispose();
//        } else if (e.getSource() == checkoutbtn) {
//            JOptionPane.showMessageDialog(null, "Checked out successfully");
//            new CheckoutPage();
//            dispose();
//        } else if (e.getSource() == topMenubtn) {
//            JOptionPane.showMessageDialog(null, "Back to top menu");
//            dispose();
//        }
//    }
//
//    // Book class remains unchanged; you might have a separate Book class
//    private static class Book {
//        private String name;
//        private String author;
//        private String imagePath;
//
//        public Book(String name, String author, String imagePath) {
//            this.name = name;
//            this.author = author;
//            this.imagePath = imagePath;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getAuthor() {
//            return author;
//        }
//
//        public ImageIcon getImageIcon() {
//            return new ImageIcon(imagePath);
//        }
//    }
//}
