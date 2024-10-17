package za.ac.cput.librarysystemGUI;

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

public class BookLib extends JFrame implements ActionListener {

    private JButton accountbtn, checkoutbtn, topMenubtn;
    private JLabel loggedInUserLabel, rentalCountLabel;
    private int rentalCount = 0;
    private BookDAO bookDAO;
    private DefaultTableModel model;
    private JTable bookTable;
    private DefaultTableModel rentedBooksModel;
    private JTable rentedBooksTable; // New JTable for rented books
    private JButton rentButton;
    private JButton returnButton, searchButton; // Declare returnButton
    private JTextField searchField;
    private DefaultTableModel borrowHistoryModel; // JTable model for borrow history
    private JTable borrowHistoryTable; // JTable for borrow history
    
        private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Color BUTTON_COLOR = Color.BLUE;
    private static final Color BUTTON_HOVER_COLOR = Color.DARK_GRAY;
    private static final Color TABLE_BACKGROUND_COLOR = Color.WHITE;
    private static final Color TABLE_SELECTION_BACKGROUND = Color.LIGHT_GRAY;

    public BookLib() {
                bookDAO = new BookDAO();

        setTitle("Audio Book Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 600);
        setLocationRelativeTo(null); // Center the window on the screen

        createMenuBar();

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tableBookPanel = createTableBookPanel();
        tabbedPane.addTab("Books", tableBookPanel);

        JPanel rentedBooksPanel = createRentedBooksPanel(); // Create new panel for rented books
        tabbedPane.addTab("My Rented Books", rentedBooksPanel); // Add tab for rented books

        add(tabbedPane, BorderLayout.CENTER);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loggedInUserLabel = new JLabel("Logged in as: " + UserSession.getLoggedInUsername());
        loggedInUserLabel.setFont(DEFAULT_FONT);
        userPanel.add(loggedInUserLabel);

        JPanel rentalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rentalCountLabel = new JLabel("Books rented: " + rentalCount);
        rentalCountLabel.setFont(DEFAULT_FONT);
        rentalPanel.add(rentalCountLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(userPanel, BorderLayout.WEST);
        topPanel.add(rentalPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        accountbtn = new JButton("Account");
        checkoutbtn = new JButton("Check out");
        topMenubtn = new JButton("Top Menu");

        // Set button colors and hover effects
        styleButton(accountbtn);
        styleButton(checkoutbtn);
        styleButton(topMenubtn);

        bottomPanel.add(accountbtn);
        bottomPanel.add(checkoutbtn);
        bottomPanel.add(topMenubtn);
        
        JPanel borrowHistoryPanel = createBorrowHistoryPanel(); // Create new panel for borrow history
        tabbedPane.addTab("Borrow History", borrowHistoryPanel); // Add tab for borrow history

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

    private JPanel createBorrowHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(Color.LIGHT_GRAY); // Set background color for the panel

        // Column Names for Borrow History Table
        String[] historyColumnNames = {"Book ID", "Title", "Author", "Borrow Date", "Return Date"};
        borrowHistoryModel = new DefaultTableModel(historyColumnNames, 0);

        // Fetch borrow history data from the database and populate the table
        List<Object[]> borrowHistory = bookDAO.getBorrowHistory(UserSession.getLoggedInUserId());
        for (Object[] historyEntry : borrowHistory) {
            borrowHistoryModel.addRow(historyEntry);
        }

        borrowHistoryTable = new JTable(borrowHistoryModel);
        JScrollPane scrollPane = new JScrollPane(borrowHistoryTable);
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        return historyPanel;
    }

    // Create Table Panel for Books
    private JPanel createTableBookPanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.LIGHT_GRAY); // Set background color for the panel

        // Column Names for the Book Table
        String[] columnNames = {"Book ID", "Title", "Author", "Available"};
        model = new DefaultTableModel(columnNames, 0);

        // Fetch all books from the database and populate the table
        List<Object[]> books = bookDAO.getAllBooks();
        for (Object[] book : books) {
            model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"});
        }

        bookTable = new JTable(model);
        bookTable.setBackground(TABLE_BACKGROUND_COLOR);
        bookTable.setSelectionBackground(TABLE_SELECTION_BACKGROUND);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Rent Button
        rentButton = new JButton("Rent Selected Book");
        rentButton.addActionListener(e -> rentSelectedBook());
        tablePanel.add(rentButton, BorderLayout.SOUTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20); // Text field for search input
        searchButton = new JButton("Search"); // Button to initiate search
        searchPanel.add(new JLabel("Search Book:")); // Label for search
        searchPanel.add(searchField); // Add text field to panel
        searchPanel.add(searchButton); // Add search button to panel

        searchButton.addActionListener(e -> searchBooks()); // Add action listener to search button

        tablePanel.add(searchPanel, BorderLayout.NORTH); // Add search panel to the top of the table panel

        return tablePanel;
    }

    // Search for books based on the title or author
    private void searchBooks() {
        String query = searchField.getText().trim().toLowerCase(); // Get search input and convert to lowercase
        if (query.isEmpty()) {
            refreshAvailableBooksTable(); // Show all books if search field is empty
            return;
        }

        model.setRowCount(0); // Clear existing rows
        List<Object[]> books = bookDAO.getAllBooks(); // Fetch all books from database
        for (Object[] book : books) {
            String title = book[1].toString().toLowerCase(); // Book title
            String author = book[2].toString().toLowerCase(); // Book author
            if (title.contains(query) || author.contains(query)) {
                model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"}); // Add filtered results to table
            }
        }
    }

    // Create Table Panel for Rented Books
    private JPanel createRentedBooksPanel() {
        JPanel rentedPanel = new JPanel(new BorderLayout());
        rentedPanel.setBackground(Color.LIGHT_GRAY); // Set background color for the panel

        // Column Names for Rented Books Table
        String[] rentedColumnNames = {"Book ID", "Title", "Author", "Return Date"};
        rentedBooksModel = new DefaultTableModel(rentedColumnNames, 0);

        // Fetch rented books from the database and populate the table
        List<Object[]> rentedBooks = bookDAO.getRentedBooks(UserSession.getLoggedInUserId());
        for (Object[] rentedBook : rentedBooks) {
            rentedBooksModel.addRow(rentedBook);
        }

        rentedBooksTable = new JTable(rentedBooksModel);
        rentedBooksTable.setBackground(TABLE_BACKGROUND_COLOR);
        rentedBooksTable.setSelectionBackground(TABLE_SELECTION_BACKGROUND);
        JScrollPane scrollPane = new JScrollPane(rentedBooksTable);
        rentedPanel.add(scrollPane, BorderLayout.CENTER);

        // Return Button
        returnButton = new JButton("Return Selected Book");
        returnButton.addActionListener(e -> returnSelectedBook());
        rentedPanel.add(returnButton, BorderLayout.SOUTH);

        return rentedPanel;
    }

    // Rent a selected book
    private void rentSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) bookTable.getValueAt(selectedRow, 0);
            boolean isAvailable = "Yes".equals(bookTable.getValueAt(selectedRow, 3));

            if (isAvailable) {
                // Prompt for admin password before renting
                JPasswordField passwordField = new JPasswordField();
                int result = JOptionPane.showConfirmDialog(this, passwordField, "Enter Admin Password", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String password = new String(passwordField.getPassword());
                    if (isValidAdminPassword(password)) {
                        bookDAO.rentBook(bookId, UserSession.getLoggedInUserId());
                        rentalCount++;
                        rentalCountLabel.setText("Books rented: " + rentalCount);
                        refreshAvailableBooksTable();
                        refreshRentedBooksTable(); // Refresh rented books table
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "This book is not available for rent.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to rent.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Return a selected book
    private void returnSelectedBook() {
        int selectedRow = rentedBooksTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) rentedBooksTable.getValueAt(selectedRow, 0);
            bookDAO.returnBook(bookId, UserSession.getLoggedInUserId());
            rentalCount--;
            rentalCountLabel.setText("Books rented: " + rentalCount);
            refreshRentedBooksTable(); // Refresh rented books table
            refreshAvailableBooksTable(); // Refresh available books table
        } else {
            JOptionPane.showMessageDialog(this, "Please select a rented book to return.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Validate admin password
    private boolean isValidAdminPassword(String password) {
        // Implement your password validation logic here
        return "admin123".equals(password);
    }

    // Refresh available books table
    private void refreshAvailableBooksTable() {
        model.setRowCount(0); // Clear existing rows
        List<Object[]> books = bookDAO.getAllBooks(); // Fetch all books from database
        for (Object[] book : books) {
            model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"}); // Add refreshed books to table
        }
    }

    // Refresh rented books table
    private void refreshRentedBooksTable() {
        rentedBooksModel.setRowCount(0); // Clear existing rows
        List<Object[]> rentedBooks = bookDAO.getRentedBooks(UserSession.getLoggedInUserId()); // Fetch rented books from database
        for (Object[] rentedBook : rentedBooks) {
            rentedBooksModel.addRow(rentedBook); // Add refreshed rented books to table
        }
    }

    // Style buttons
    private void styleButton(JButton button) {
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false); // Remove default border

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }

    // Action listener for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accountbtn) {
            int userId = UserSession.getLoggedInUserId();
            String username = UserSession.getLoggedInUsername();
            new AccountPageGui(userId, username);  // Pass the user ID and username
            dispose();
        } else if (e.getSource() == checkoutbtn) {
            JOptionPane.showMessageDialog(null, "Checked out successfully");
            new CheckoutPage();
            dispose();
        } else if (e.getSource() == topMenubtn) {
            new TopMenu();
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookLib::new); // Launch the application
    }
}
