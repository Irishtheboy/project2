package za.ac.cput.librarysystemGUI;

import ac.za.cput.librarysystem.domain.UserSession;
import ac.za.cput.librarysystem.dao.UserDAO;
import ac.za.cput.librarysystem.dao.BookDAO;
import ac.za.cput.librarysystem.domain.UserSession;

import javax.swing.*;

import java.awt.*;


import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.io.BufferedReader;
import java.io.File;
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
     private JTable borrowHistoryTable; // JTable for borrow history
      private DefaultTableModel borrowHistoryModel; // JTable model for borrow history

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
        String[] bookColumnNames = {"Book ID", "Title", "Author", "Genre", "ISBN", "Published Year", "Available", "Image"};
        Object[][] bookData = {};  // Will be populated from the database
        booksTable = new JTable(new DefaultTableModel(bookData, bookColumnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent cell editing
            }
        };

        // Custom renderer for displaying images
    


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
        
        JPanel borrowHistoryPanel = createBorrowHistoryPanel(); // Create new panel for borrow history
        tabbedPane.addTab("Borrow History", borrowHistoryPanel); // Add tab for borrow history
        add(tabbedPane, BorderLayout.CENTER);

        // Table for Users
        String[] userColumnNames = {"User ID", "Username", "Email", "Role"};
        Object[][] userData = {};  // Will be populated from the database
        usersTable = new JTable(new DefaultTableModel(userData, userColumnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent cell editing
            }
        };
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        // Buttons for Users
        JPanel usersButtonPanel = new JPanel(new FlowLayout());
        btnDeleteUser = new JButton("Delete User");
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
        tabbedPane.addTab("Feedback", feedbackPanel);

        // Add the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);

        // Fetch data and populate tables
        populateBooksTable();
        populateUsersTable();
        populateFeedback();

        // Set table sizes
        setTableColumnWidths(booksTable);
        setTableColumnWidths(usersTable);

        // Add action listeners
        btnAddBook.addActionListener(e -> showAddBookDialog());
        btnDeleteBook.addActionListener(e -> deleteSelectedBook());
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
                case 4: // ISBN
                    column.setPreferredWidth(100); // Set width for ISBN
                    break;
                case 5: // Published Year
                    column.setPreferredWidth(100); // Set width for Published Year
                    break;
                case 6: // Available
                    column.setPreferredWidth(70); // Set width for Available
                    break;
                case 7: // Image
                    column.setPreferredWidth(150); // Set width for Image
                    break;
            }
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); // Adjust the last column width automatically
    }

    private void populateBooksTable() {
    try {
        List<Object[]> books = bookDAO.getAllBooks();
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.setRowCount(0);  // Clear existing data
        for (Object[] book : books) {
            model.addRow(book);  // Add each book to the table
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
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
     private JPanel createBorrowHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());

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
        panel.add(new JLabel("Published Year:"));
        JTextField yearField = new JTextField();
        panel.add(yearField);

        // Availability
        panel.add(new JLabel("Available:"));
        JCheckBox availableCheckbox = new JCheckBox();
        panel.add(availableCheckbox);

        // Image selection
        panel.add(new JLabel("Book Image:"));
        JTextField imageField = new JTextField();
        panel.add(imageField);
        JButton browseButton = new JButton("Browse");
        panel.add(browseButton);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imageField.setText(selectedFile.getAbsolutePath()); // Set the path in the text field
            }
        });

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            String isbn = isbnField.getText();
            String yearPublishedStr = yearField.getText();
            boolean available = availableCheckbox.isSelected();
            String imagePath = imageField.getText(); // Get the image path

            // Validation checks
            if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || isbn.isEmpty() || yearPublishedStr.isEmpty() || imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            int yearPublished;
            try {
                yearPublished = Integer.parseInt(yearPublishedStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Published Year must be a number.");
                return;
            }

            // Add the book using DAO
            bookDAO.addBook(title, author, genre, isbn, yearPublished, available, imagePath);
            populateBooksTable(); // Refresh the books table
        }
    }

    private void deleteSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow != -1) {
            int bookId = (int) booksTable.getValueAt(selectedRow, 0);
            bookDAO.deleteBook(bookId);
            populateBooksTable(); // Refresh the books table
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) usersTable.getValueAt(selectedRow, 0);
            userDAO.deleteUser(userId);
            populateUsersTable(); // Refresh the users table
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashBoard dashboard = new AdminDashBoard();
            dashboard.setVisible(true);
        });
    }

}
