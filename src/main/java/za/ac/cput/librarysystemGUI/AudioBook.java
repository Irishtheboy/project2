//package za.ac.cput.librarysystemGui;
//
//import ac.za.cput.librarysystem.dao.UserDAO;
//import ac.za.cput.librarysystem.domain.Book;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AudioBook extends JFrame implements ActionListener {
//
//    private JButton accountbtn, checkoutbtn, topMenubtn;
//    private JLabel loggedInUserLabel, rentalCountLabel;
//    private List<Book> books;
//    private int rentalCount = 0; // To keep track of the number of books rented
//
//    public AudioBook() {
//        books = loadBooksFromFile("books.txt");
//
//        setTitle("Audio Book Library");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(880, 600);
//
//        createMenuBar();
//
//        JTabbedPane tabbedPane = new JTabbedPane();
//
//        // Create the book panel with images
//        JPanel imageBookPanel = createImageBookPanel();
//        tabbedPane.addTab("Books (Images)", imageBookPanel);
//
//        // Create the book panel with table
//        JPanel tableBookPanel = createTableBookPanel();
//        tabbedPane.addTab("Books (Table)", tableBookPanel);
//
//        add(tabbedPane, BorderLayout.CENTER);
//
//        // User panel for displaying logged-in user and rental count
//        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        loggedInUserLabel = new JLabel("Logged in as: " + UserSession.getLoggedInUsername());
//        loggedInUserLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        userPanel.add(loggedInUserLabel);
//
//        JPanel rentalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        rentalCountLabel = new JLabel("Books rented: " + rentalCount);
//        rentalCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        rentalPanel.add(rentalCountLabel);
//
//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.add(userPanel, BorderLayout.WEST);
//        topPanel.add(rentalPanel, BorderLayout.EAST);
//        add(topPanel, BorderLayout.NORTH);
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
//    private JPanel createImageBookPanel() {
//        JPanel bookPanel = new JPanel(new GridLayout(2, 4, 10, 10));
//
//        for (Book book : books) {
//            JPanel bookPanelItem = new JPanel(new BorderLayout());
//            JLabel bookPic = new JLabel(new ImageIcon(book.getImagePath()));
//            bookPanelItem.add(bookPic, BorderLayout.CENTER);
//
//            JPanel textAndButtonPanel = new JPanel(new BorderLayout());
//
//            JLabel bookName = new JLabel(book.getName(), SwingConstants.CENTER);
//            JLabel bookAuth = new JLabel(book.getAuthor(), SwingConstants.CENTER);
//            JButton rentBtn = new JButton("Rent");
//
//            rentBtn.addActionListener(e -> rentBook(book));
//
//            JPanel namePanel = new JPanel(new BorderLayout());
//            namePanel.add(bookName, BorderLayout.CENTER);
//
//            JPanel authorPanel = new JPanel(new BorderLayout());
//            authorPanel.add(bookAuth, BorderLayout.CENTER);
//
//            JPanel buttonPanel = new JPanel(new BorderLayout());
//            buttonPanel.add(rentBtn, BorderLayout.CENTER);
//
//            textAndButtonPanel.add(namePanel, BorderLayout.NORTH);
//            textAndButtonPanel.add(authorPanel, BorderLayout.CENTER);
//            textAndButtonPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//            bookPanelItem.add(textAndButtonPanel, BorderLayout.SOUTH);
//            bookPanel.add(bookPanelItem);
//        }
//        return bookPanel;
//    }
//
//    private JPanel createTableBookPanel() {
//        JPanel tablePanel = new JPanel(new BorderLayout());
//
//        String[] columnNames = {"Book Name", "Author", "Image Path"};
//        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//
//        for (Book book : books) {
//            model.addRow(new Object[]{book.getName(), book.getAuthor(), book.getImagePath()});
//        }
//
//        JTable bookTable = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(bookTable);
//        tablePanel.add(scrollPane, BorderLayout.CENTER);
//
//        // Add a rent button for the selected book in the table
//        JButton rentButton = new JButton("Rent Selected Book");
//        rentButton.addActionListener(e -> {
//            int selectedRow = bookTable.getSelectedRow();
//            if (selectedRow >= 0) {
//                String bookName = (String) bookTable.getValueAt(selectedRow, 0);
//                Book selectedBook = findBookByName(bookName);
//                rentBook(selectedBook);
//            } else {
//                JOptionPane.showMessageDialog(this, "Please select a book to rent.");
//            }
//        });
//        tablePanel.add(rentButton, BorderLayout.SOUTH);
//
//        return tablePanel;
//    }
//
//    private Book findBookByName(String bookName) {
//        for (Book book : books) {
//            if (book.getName().equals(bookName)) {
//                return book;
//            }
//        }
//        return null; // Book not found
//    }
//
//    private void rentBook(Book book) {
//        String username = UserSession.getLoggedInUsername();
//        UserDAO userDAO = new UserDAO();
//        String user = userDAO.getUserUsername(username); // Get username
//
//        if (user != null) {
//            int bookId = book.getId();
//            if (userDAO.isBookAvailable(bookId)) {
//                boolean success = userDAO.rentBookByUsername(user, bookId); // Update to use username
//                if (success) {
//                    rentalCount++;
//                    rentalCountLabel.setText("Books rented: " + rentalCount);
//                    JOptionPane.showMessageDialog(this, "Book rented successfully!");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Failed to rent book.");
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "This book is already rented out.");
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "User not found.");
//        }
//    }
//
//    private List<Book> loadBooksFromFile(String filename) {
//        List<Book> bookList = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] details = line.split(";");
//                if (details.length == 3) {
//                    bookList.add(new Book(details[0], details[1], details[2]));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bookList;
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
//            new TopMenu();
//            dispose();
//        }
//    }
//
//    public static class UserSession {
//        private static String loggedInUsername;
//
//        public static String getLoggedInUsername() {
//            return loggedInUsername;
//        }
//
//        public static void setLoggedInUsername(String username) {
//            loggedInUsername = username;
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(AudioBook::new);
//    }
//}
package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.dao.BookDAO;
import ac.za.cput.librarysystem.dao.UserDAO;
import ac.za.cput.librarysystem.domain.UserSession;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AudioBook extends JFrame implements ActionListener {

    private JButton accountbtn, checkoutbtn, topMenubtn;
    private JLabel loggedInUserLabel, rentalCountLabel;
    private int rentalCount = 0;
    private BookDAO bookDAO;
    private DefaultTableModel model;
    private JTable bookTable;

    public AudioBook() {
        bookDAO = new BookDAO();

        setTitle("Audio Book Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 600);

        createMenuBar();

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tableBookPanel = createTableBookPanel();
        tabbedPane.addTab("Books", tableBookPanel);

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

        String[] columnNames = {"Book ID", "Title", "Author", "Genre", "ISBN", "Year", "Available"};
        model = new DefaultTableModel(columnNames, 0);

        List<Object[]> books = bookDAO.getAllBooks();
        for (Object[] book : books) {
            model.addRow(book);
        }

        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JButton rentButton = new JButton("Rent Selected Book");
        rentButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                int bookId = (Integer) bookTable.getValueAt(selectedRow, 0);
                rentBook(bookId, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to rent.");
            }
        });
        tablePanel.add(rentButton, BorderLayout.SOUTH);

        return tablePanel;
    }

    private void rentBook(int bookId, int selectedRow) {
        int userId = UserSession.getLoggedInUserId(); // Get the logged-in user's ID

        if (bookDAO.isBookAvailable(bookId)) {
            boolean success = bookDAO.rentBook(userId, bookId); // Pass both user ID and book ID to log the rental
            if (success) {
                rentalCount++;
                rentalCountLabel.setText("Books rented: " + rentalCount);
                model.setValueAt(false, selectedRow, 6); // Update "Available" status in the table to false
                JOptionPane.showMessageDialog(this, "Book rented successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to rent book.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "This book is already rented out.");
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

    public class UserSession {

        private static String loggedInUsername;
        private static int loggedInUserId; // Store the user ID

        public static String getLoggedInUsername() {
            return loggedInUsername;
        }

        public static void setLoggedInUsername(String username) {
            loggedInUsername = username;
        }

        public static int getLoggedInUserId() {
            return loggedInUserId;
        }

        public static void setLoggedInUserId(int userId) {
            loggedInUserId = userId;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AudioBook::new);
    }
}
