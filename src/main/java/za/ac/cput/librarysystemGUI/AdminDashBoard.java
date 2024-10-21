package za.ac.cput.librarysystemGUI;

import ac.za.cput.librarysystem.domain.UserSession;
import ac.za.cput.librarysystem.dao.UserDAO;
import ac.za.cput.librarysystem.dao.BookDAO;
import ac.za.cput.librarysystem.domain.UserSession;

import za.ac.cput.librarysystemGui.LoginPage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import za.ac.cput.librarysystemGui.FeedbackPage;

public class AdminDashBoard extends JFrame {

    private JTable booksTable;
    private JTable usersTable;
    private JButton btnAddBook, btnDeleteBook, btnAddUser, btnDeleteUser, btnLogout;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private JTextArea feedbackTextArea;
     private JTable borrowHistoryTable; 
      private DefaultTableModel borrowHistoryModel; 

    public AdminDashBoard() {
        userDAO = new UserDAO();  
        bookDAO = new BookDAO();  
        createMenuBar();

        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        
        JTabbedPane tabbedPane = new JTabbedPane();

        
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());
        booksPanel.setBorder(BorderFactory.createTitledBorder("Books"));

        
        String[] bookColumnNames = {"Book ID", "Title", "Author", "Genre", "ISBN", "Published Year", "Available", "Image"};
        Object[][] bookData = {};  
        booksTable = new JTable(new DefaultTableModel(bookData, bookColumnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        
    


        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        booksPanel.add(booksScrollPane, BorderLayout.CENTER);

        
        JPanel booksButtonPanel = new JPanel(new FlowLayout());
        btnAddBook = new JButton("Add Book");
        btnDeleteBook = new JButton("Delete Book");
        booksButtonPanel.add(btnAddBook);
        booksButtonPanel.add(btnDeleteBook);
        booksPanel.add(booksButtonPanel, BorderLayout.SOUTH);

        
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());
        usersPanel.setBorder(BorderFactory.createTitledBorder("Users"));
        


        
        String[] userColumnNames = {"User ID", "Username", "Email", "Role"};
        Object[][] userData = {};  
        usersTable = new JTable(new DefaultTableModel(userData, userColumnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        
        JPanel usersButtonPanel = new JPanel(new FlowLayout());
        btnDeleteUser = new JButton("Delete User");
        usersButtonPanel.add(btnDeleteUser);
        usersPanel.add(usersButtonPanel, BorderLayout.SOUTH);

       
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBorder(BorderFactory.createTitledBorder("Feedback"));

        feedbackTextArea = new JTextArea();
        feedbackTextArea.setEditable(false);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackTextArea);
        feedbackPanel.add(feedbackScrollPane, BorderLayout.CENTER);

        
        tabbedPane.addTab("Books", booksPanel);
        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Feedback", feedbackPanel);

        
        add(tabbedPane, BorderLayout.CENTER);
        
        
        btnLogout = new JButton("Logout");
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(btnLogout);
        add(logoutPanel, BorderLayout.NORTH); 
        
        populateBooksTable();
        populateUsersTable();
        populateFeedback();

        
        setTableColumnWidths(booksTable);
        setTableColumnWidths(usersTable);

        
        btnAddBook.addActionListener(e -> showAddBookDialog());
        btnDeleteBook.addActionListener(e -> deleteSelectedBook());
        btnDeleteUser.addActionListener(e -> deleteSelectedUser());
    
        
        btnLogout.addActionListener(e -> {
            dispose();  
            new LoginPage().setVisible(true); 
        });
    }
       private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });
        fileMenu.add(exitItem);
        
        
        JMenuItem feedbackItem = new JMenuItem("Feedback");
        feedbackItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FeedbackPage(); 
            }
        });
        fileMenu.add(feedbackItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutPage(); 
            }
        });
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void setTableColumnWidths(JTable table) {
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(50); 
                    break;
                case 1: // Title/Username
                    column.setPreferredWidth(200); 
                    break;
                case 2: // Author/Email
                    column.setPreferredWidth(150); 
                    break;
                case 3: // Genre/Role
                    column.setPreferredWidth(100); 
                    break;
                case 4: // ISBN
                    column.setPreferredWidth(100); 
                    break;
                case 5: // Published Year
                    column.setPreferredWidth(100); 
                    break;
                case 6: // Available
                    column.setPreferredWidth(70); 
                    break;
                case 7: // Image even though i made this optional to check cause there are issues with the display
                    column.setPreferredWidth(150); 
                    break;
            }
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); 
    }

    private void populateBooksTable() {
    try {
        List<Object[]> books = bookDAO.getAllBooks();
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.setRowCount(0);  
        for (Object[] book : books) {
            model.addRow(book);  
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
    }
}

    private void populateUsersTable() {
        List<Object[]> users = userDAO.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);  

        for (Object[] user : users) {
            model.addRow(user);  
        }
    }

    private void populateFeedback() {
        String filePath = "feedback.txt";  
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

        
        panel.add(new JLabel("ISBN:"));
        JTextField isbnField = new JTextField();
        panel.add(isbnField);

        
        panel.add(new JLabel("Published Year:"));
        JTextField yearField = new JTextField();
        panel.add(yearField);

        
        panel.add(new JLabel("Available:"));
        JCheckBox availableCheckbox = new JCheckBox();
        panel.add(availableCheckbox);

        
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
                imageField.setText(selectedFile.getAbsolutePath()); 
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
            String imagePath = imageField.getText(); 
           
            if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || isbn.isEmpty() || yearPublishedStr.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "All fields must be filled. Except for images");
                return;
            }

            int yearPublished;
            try {
                yearPublished = Integer.parseInt(yearPublishedStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Published Year must be a number.");
                return;
            }

            
            bookDAO.addBook(title, author, genre, isbn, yearPublished, available, imagePath);
            populateBooksTable(); 
        }
    }

    private void deleteSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow != -1) {
            int bookId = (int) booksTable.getValueAt(selectedRow, 0);
            bookDAO.deleteBook(bookId);
            populateBooksTable(); 
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) usersTable.getValueAt(selectedRow, 0);
            userDAO.deleteUser(userId);
            populateUsersTable(); 
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
