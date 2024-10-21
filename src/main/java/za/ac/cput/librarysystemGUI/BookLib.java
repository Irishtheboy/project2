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

public class BookLib extends JFrame implements ActionListener {

    private JButton accountbtn, checkoutbtn, topMenubtn;
    private JLabel loggedInUserLabel, rentalCountLabel;
    private int rentalCount = 0;
    private BookDAO bookDAO;
    private DefaultTableModel model;
    private JTable bookTable;
    private DefaultTableModel rentedBooksModel;
    private JTable rentedBooksTable; 
    private JButton rentButton;
    private JButton returnButton, searchButton; 
    private JTextField searchField;
    private DefaultTableModel borrowHistoryModel; 
    private JTable borrowHistoryTable; 

    public BookLib() {
        bookDAO = new BookDAO();

        setTitle("Audio Book Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 600);

        createMenuBar();

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tableBookPanel = createTableBookPanel();
        tabbedPane.addTab("Books", tableBookPanel);

        JPanel rentedBooksPanel = createRentedBooksPanel(); 
        tabbedPane.addTab("My Rented Books", rentedBooksPanel); 

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

        accountbtn.setBackground(Color.blue);
        checkoutbtn.setBackground(Color.blue);
        topMenubtn.setBackground(Color.blue);
        searchButton.setBackground(Color.yellow);

        bottomPanel.add(accountbtn);
        bottomPanel.add(checkoutbtn);
        bottomPanel.add(topMenubtn);
        JPanel borrowHistoryPanel = createBorrowHistoryPanel(); 
        tabbedPane.addTab("Borrow History", borrowHistoryPanel); 

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

        
        String[] historyColumnNames = {"Book ID", "Title", "Author", "Borrow Date", "Return Date"};
        borrowHistoryModel = new DefaultTableModel(historyColumnNames, 0);

        
        List<Object[]> borrowHistory = bookDAO.getBorrowHistory(UserSession.getLoggedInUserId());
        for (Object[] historyEntry : borrowHistory) {
            borrowHistoryModel.addRow(historyEntry);
        }

        borrowHistoryTable = new JTable(borrowHistoryModel);
        JScrollPane scrollPane = new JScrollPane(borrowHistoryTable);
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        return historyPanel;
    }

    
    private JPanel createTableBookPanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        
        String[] columnNames = {"Book ID", "Title", "Author", "Available"};
        model = new DefaultTableModel(columnNames, 0);

        
        List<Object[]> books = bookDAO.getAllBooks();
        for (Object[] book : books) {
            model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"});
        }

        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        
        rentButton = new JButton("Rent Selected Book");
        rentButton.addActionListener(e -> rentSelectedBook());
        tablePanel.add(rentButton, BorderLayout.SOUTH);
        
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
                model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"}); // Add filtered results to table
            }
        }
    }

    
    private JPanel createRentedBooksPanel() {
        JPanel rentedPanel = new JPanel(new BorderLayout());

        
        String[] rentedColumnNames = {"Book ID", "Title", "Author", "Return Date"};
        rentedBooksModel = new DefaultTableModel(rentedColumnNames, 0);

        
        List<Object[]> rentedBooks = bookDAO.getRentedBooks(UserSession.getLoggedInUserId());
        for (Object[] rentedBook : rentedBooks) {
            rentedBooksModel.addRow(rentedBook);
        }

        rentedBooksTable = new JTable(rentedBooksModel);
        JScrollPane scrollPane = new JScrollPane(rentedBooksTable);
        rentedPanel.add(scrollPane, BorderLayout.CENTER);

        
        returnButton = new JButton("Return Selected Book");
        returnButton.addActionListener(e -> returnSelectedBook());
        rentedPanel.add(returnButton, BorderLayout.SOUTH);

        return rentedPanel;
    }

     
    private void rentSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) bookTable.getValueAt(selectedRow, 0);
            boolean isAvailable = "Yes".equals(bookTable.getValueAt(selectedRow, 3));

            if (isAvailable) {
                
                JPasswordField passwordField = new JPasswordField();
                Object[] message = {
                    "Admin Password:", passwordField
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Admin Approval", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    String enteredPassword = new String(passwordField.getPassword());
                    String adminPassword = "admin123"; 

                    if (enteredPassword.equals(adminPassword)) {
                      
                        int userId = UserSession.getLoggedInUserId(); 
                        boolean success = bookDAO.rentBook(userId, bookId); 
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Book rented successfully!");
                            rentalCount++;
                            rentalCountLabel.setText("Books rented: " + rentalCount);
                            
                            model.setValueAt("No", selectedRow, 3);
                            
                            updateRentedBooksTable();
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
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to return.");
        }
    }

    
    private void updateRentedBooksTable() {
        rentedBooksModel.setRowCount(0); 
        List<Object[]> rentedBooks = bookDAO.getRentedBooks(UserSession.getLoggedInUserId());
        for (Object[] rentedBook : rentedBooks) {
            rentedBooksModel.addRow(rentedBook);
        }
    }

    
    private void refreshAvailableBooksTable() {
        model.setRowCount(0); 
        List<Object[]> books = bookDAO.getAllBooks();
        for (Object[] book : books) {
            model.addRow(new Object[]{book[0], book[1], book[2], (boolean) book[6] ? "Yes" : "No"});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accountbtn) {
            int userId = UserSession.getLoggedInUserId();
            String username = UserSession.getLoggedInUsername();
            new AccountPageGui(userId, username);  
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
        SwingUtilities.invokeLater(BookLib::new);
    }
}
