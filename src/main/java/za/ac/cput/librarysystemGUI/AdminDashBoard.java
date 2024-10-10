package za.ac.cput.librarysystemGUI;

import ac.za.cput.librarysystem.dao.UserDAO;
import ac.za.cput.librarysystem.dao.BookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AdminDashBoard extends JFrame {

    private JTable booksTable;
    private JTable usersTable;
    private JButton btnAddBook, btnDeleteBook, btnAddUser, btnDeleteUser;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private JTextArea feedbackTextArea;

    public AdminDashBoard() {
        userDAO = new UserDAO();  // Initialize UserDAO
        bookDAO = new BookDAO();  // Initialize BookDAO

        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel for Books
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());
        booksPanel.setBorder(BorderFactory.createTitledBorder("Books"));

        // Table for Books
        String[] bookColumnNames = {"Book ID", "Title", "Author", "Genre", "ISBN", "published_year", "Available"};
        Object[][] bookData = {};  // Will be populated from the database
        booksTable = new JTable(new DefaultTableModel(bookData, bookColumnNames));

        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        booksPanel.add(booksScrollPane, BorderLayout.CENTER);

        // Buttons for Books
        JPanel booksButtonPanel = new JPanel(new FlowLayout());
        btnAddBook = new JButton("Add Book");
        btnDeleteBook = new JButton("Delete Book");
        booksButtonPanel.add(btnAddBook);
        booksButtonPanel.add(btnDeleteBook);
        booksPanel.add(booksButtonPanel, BorderLayout.SOUTH);

        // Panel for Users
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());
        usersPanel.setBorder(BorderFactory.createTitledBorder("Users"));

        // Table for Users
        String[] userColumnNames = {"User ID", "Username", "Email", "Role"};
        Object[][] userData = {};  // Will be populated from the database
        usersTable = new JTable(new DefaultTableModel(userData, userColumnNames));
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        // Buttons for Users
        JPanel usersButtonPanel = new JPanel(new FlowLayout());
//        btnAddUser = new JButton("Add User");
        btnDeleteUser = new JButton("Delete User");
//        usersButtonPanel.add(btnAddUser);
        usersButtonPanel.add(btnDeleteUser);
        usersPanel.add(usersButtonPanel, BorderLayout.SOUTH);

        // Panel for Feedback
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBorder(BorderFactory.createTitledBorder("Feedback"));

        feedbackTextArea = new JTextArea();
        feedbackTextArea.setEditable(false);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackTextArea);
        feedbackPanel.add(feedbackScrollPane, BorderLayout.CENTER);

        // Add panels to the tabbed pane
        tabbedPane.addTab("Books", booksPanel);
        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Feedback", feedbackPanel);  // New Feedback tab

        // Add the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);

        // Fetch data and populate tables
        populateBooksTable();
        populateUsersTable();
        populateFeedback();  // Populate the feedback tab

        // Set table sizes
        setTableColumnWidths(booksTable);
        setTableColumnWidths(usersTable);

        // Add action listeners
        btnAddBook.addActionListener(e -> showAddBookDialog());
        btnDeleteBook.addActionListener(e -> deleteSelectedBook());
//        btnAddUser.addActionListener(e -> showAddUserDialog());
        btnDeleteUser.addActionListener(e -> deleteSelectedUser());
    }

    private void setTableColumnWidths(JTable table) {
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0: // Book/User ID
                    column.setPreferredWidth(50); // Set width for ID column
                    break;
                case 1: // Title/Username
                    column.setPreferredWidth(200); // Set width for Title/Username
                    break;
                case 2: // Author/Email
                    column.setPreferredWidth(150); // Set width for Author/Email
                    break;
                case 3: // Genre/Role
                    column.setPreferredWidth(100); // Set width for Genre/Role
                    break;
            }
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); // Adjust the last column width automatically
    }

    private void populateBooksTable() {
        List<Object[]> books = bookDAO.getAllBooks();
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.setRowCount(0);  // Clear existing data

        for (Object[] book : books) {
            model.addRow(book);  // Add each book to the table
        }
    }

    private void populateUsersTable() {
        List<Object[]> users = userDAO.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);  // Clear existing data

        for (Object[] user : users) {
            model.addRow(user);  // Add each user to the table
        }
    }

    private void populateFeedback() {
        String filePath = "feedback.txt";  // The path to your feedback file
        StringBuilder feedbackContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                feedbackContent.append(line).append("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading feedback file: " + e.getMessage());
        }

        feedbackTextArea.setText(feedbackContent.toString());
    }

    private void showAddBookDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // Title
        panel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        panel.add(titleField);

        // Author
        panel.add(new JLabel("Author:"));
        JTextField authorField = new JTextField();
        panel.add(authorField);

        // Genre
        panel.add(new JLabel("Genre:"));
        JTextField genreField = new JTextField();
        panel.add(genreField);

        // ISBN
        panel.add(new JLabel("ISBN:"));
        JTextField isbnField = new JTextField();
        panel.add(isbnField);

        // Year Published
        panel.add(new JLabel("published_year"));
        JTextField yearField = new JTextField();
        panel.add(yearField);

        // Availability
        panel.add(new JLabel("Available:"));
        JCheckBox availableCheckbox = new JCheckBox();
        panel.add(availableCheckbox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            String isbn = isbnField.getText();
            String yearPublishedStr = yearField.getText();
            boolean available = availableCheckbox.isSelected();

            // Validation for empty fields and numeric year
            if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || isbn.isEmpty() || yearPublishedStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.");
                return;
            }

            int yearPublished;
            try {
                yearPublished = Integer.parseInt(yearPublishedStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Year Published must be a number.");
                return;
            }

            // Add book to the database
            bookDAO.addBook(title, author, genre, isbn, yearPublished, available);
            populateBooksTable();  
        }
    }

    private void deleteSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) booksTable.getValueAt(selectedRow, 0); 
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bookDAO.deleteBook(bookId); 
                populateBooksTable(); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) usersTable.getValueAt(selectedRow, 0); 
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userDAO.deleteUser(userId); 
                populateUsersTable(); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashBoard adminDashboard = new AdminDashBoard();
            adminDashboard.setVisible(true);
        });
    }
}
