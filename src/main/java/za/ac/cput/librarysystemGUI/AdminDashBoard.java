package za.ac.cput.librarysystemGUI;

import ac.za.cput.librarysystem.dao.UserDAO;
import ac.za.cput.librarysystem.dao.BookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class AdminDashBoard extends JFrame {

    private JTable booksTable;
    private JTable usersTable;
    private JButton btnAddBook, btnDeleteBook, btnAddUser, btnDeleteUser;
    private UserDAO userDAO;
    private BookDAO bookDAO;

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
        String[] bookColumnNames = {"Book ID", "Title", "Author", "Genre"};
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
        btnAddUser = new JButton("Add User");
        btnDeleteUser = new JButton("Delete User");
        usersButtonPanel.add(btnAddUser);
        usersButtonPanel.add(btnDeleteUser);
        usersPanel.add(usersButtonPanel, BorderLayout.SOUTH);

        // Add panels to the tabbed pane
        tabbedPane.addTab("Books", booksPanel);
        tabbedPane.addTab("Users", usersPanel);

        // Add the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);

        // Fetch data and populate tables
        populateBooksTable();
        populateUsersTable();

        // Set table sizes
        setTableColumnWidths(booksTable);
        setTableColumnWidths(usersTable);

        // Add action listeners
        btnAddBook.addActionListener(e -> showAddBookDialog());
        btnDeleteBook.addActionListener(e -> deleteSelectedBook());
        btnAddUser.addActionListener(e -> showAddUserDialog());
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

    private void showAddBookDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        panel.add(titleField);
        
        panel.add(new JLabel("Author:"));
        JTextField authorField = new JTextField();
        panel.add(authorField);
        
        panel.add(new JLabel("Genre:"));
        JTextField genreField = new JTextField();
        panel.add(genreField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            bookDAO.addBook(title, author, genre);
            populateBooksTable();  // Refresh the book table
        }
    }

    private void deleteSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) booksTable.getValueAt(selectedRow, 0); // Assuming book ID is in the first column
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bookDAO.deleteBook(bookId); // Call the method to delete the book
                populateBooksTable(); // Refresh the book table
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
        }
    }

    private void showAddUserDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);
        
        panel.add(new JLabel("Role:"));
        JTextField roleField = new JTextField();
        panel.add(roleField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String role = roleField.getText();
            userDAO.addUser(username, email, role); // Add the user to the database
            populateUsersTable();  // Refresh the user table
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) usersTable.getValueAt(selectedRow, 0); // Assuming user ID is in the first column
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userDAO.deleteUser(userId); // Call the method to delete the user
                populateUsersTable(); // Refresh the user table
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
