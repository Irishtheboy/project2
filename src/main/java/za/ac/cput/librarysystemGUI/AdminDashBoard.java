package za.ac.cput.librarysystemGUI;

import javax.swing.*;
import java.awt.*;

public class AdminDashBoard extends JFrame {
    private JTable booksTable;
    private JTable usersTable;
    private JButton btnAddBook, btnDeleteBook, btnAddUser, btnDeleteUser;

    public AdminDashBoard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Panel for Books
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());
        booksPanel.setBorder(BorderFactory.createTitledBorder("Books"));

        // Table for Books
        String[] columnNames = {"Book ID", "Title", "Author", "Genre"}; // Adjust columns as needed
        Object[][] data = {}; // This will hold your book data
        booksTable = new JTable(data, columnNames);
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
        String[] userColumnNames = {"User ID", "Username", "Email", "Role"}; // Adjust columns as needed
        Object[][] userData = {}; // This will hold your user data
        usersTable = new JTable(userData, userColumnNames);
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        // Buttons for Users
        JPanel usersButtonPanel = new JPanel(new FlowLayout());
        btnAddUser = new JButton("Add User");
        btnDeleteUser = new JButton("Delete User");
        usersButtonPanel.add(btnAddUser);
        usersButtonPanel.add(btnDeleteUser);
        usersPanel.add(usersButtonPanel, BorderLayout.SOUTH);

        // Add panels to the main frame
        add(booksPanel, BorderLayout.NORTH);
        add(usersPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashBoard adminDashboard = new AdminDashBoard();
            adminDashboard.setVisible(true);
        });
    }
}


