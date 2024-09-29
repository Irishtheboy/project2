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
import za.ac.cput.librarysystemGui.AccountPageGui;


public class AudioBook extends JFrame implements ActionListener {

    private JButton accountbtn, checkoutbtn, topMenubtn;
    private JLabel loggedInUserLabel, rentalCountLabel;
    private int rentalCount = 0;
    private BookDAO bookDAO;
    private DefaultTableModel model;
    private JTable bookTable;
    private DefaultTableModel rentedBooksModel;
    private JTable rentedBooksTable; // New JTable for rented books
    private JButton rentButton; 
    private JButton returnButton; // Declare returnButton

    public AudioBook() {
        bookDAO = new BookDAO();

        setTitle("Audio Book Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 600);

        createMenuBar();

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tableBookPanel = createTableBookPanel();
        tabbedPane.addTab("Books", tableBookPanel);

        JPanel rentedBooksPanel = createRentedBooksPanel(); // Create new panel for rented books
        tabbedPane.addTab("My Rented Books", rentedBooksPanel); // Add tab for rented books

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

    // Create Table Panel for Books
    private JPanel createTableBookPanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Column Names for the Book Table
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
        rentButton = new JButton("Rent Selected Book");
        rentButton.addActionListener(e -> rentSelectedBook());
        tablePanel.add(rentButton, BorderLayout.SOUTH);

        return tablePanel;
    }

    // Create Table Panel for Rented Books
    private JPanel createRentedBooksPanel() {
        JPanel rentedPanel = new JPanel(new BorderLayout());

        // Column Names for Rented Books Table
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
                boolean success = bookDAO.rentBook(UserSession.getLoggedInUserId(), bookId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Book rented successfully!");
                    rentalCount++;
                    rentalCountLabel.setText("Books rented: " + rentalCount);
                    // Update availability in the table
                    model.setValueAt("No", selectedRow, 3);
                    // Refresh the rented books table
                    updateRentedBooksTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to rent the book.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "This book is already rented out.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to rent.");
        }
    }

    // Return a selected book
    private void returnSelectedBook() {
        int selectedRow = rentedBooksTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) rentedBooksTable.getValueAt(selectedRow, 0);
            boolean success = bookDAO.returnBook(UserSession.getLoggedInUserId(), bookId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
                // Update the rented books table
                rentedBooksModel.removeRow(selectedRow);
                rentalCount--;
                rentalCountLabel.setText("Books rented: " + rentalCount);
                // Refresh the available books table
                refreshAvailableBooksTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to return the book.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to return.");
        }
    }

    // Refresh the rented books table
    private void updateRentedBooksTable() {
        rentedBooksModel.setRowCount(0); // Clear existing rows
        List<Object[]> rentedBooks = bookDAO.getRentedBooks(UserSession.getLoggedInUserId());
        for (Object[] rentedBook : rentedBooks) {
            rentedBooksModel.addRow(rentedBook);
        }
    }

    // Refresh the available books table
    private void refreshAvailableBooksTable() {
        model.setRowCount(0); // Clear existing rows
        List<Object[]> books = bookDAO.getAllBooks();
        for (Object[] book : books) {
            model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"});
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
            new TopMenu();
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AudioBook::new);
    }
}
