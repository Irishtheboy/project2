package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.domain.Book;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AudioBook extends JFrame implements ActionListener {

    private JButton accountbtn, checkoutbtn, topMenubtn;
    private List<Book> books;

    public AudioBook() {
        books = loadBooksFromFile("books.txt");

        setTitle("Book Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 600);

        createMenuBar();
        
        JLabel title = new JLabel("AUDIO BOOKS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel bookPanel = createBookPanel();
        add(bookPanel, BorderLayout.CENTER);

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

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }

    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        for (Book book : books) {
            JPanel bookPanelItem = new JPanel(new BorderLayout());
            JLabel bookPic = new JLabel(new ImageIcon(book.getImagePath()));

            bookPanelItem.add(bookPic, BorderLayout.CENTER);

            JPanel textAndButtonPanel = new JPanel(new BorderLayout());

            JLabel bookName = new JLabel(book.getName(), SwingConstants.CENTER);
            JLabel bookAuth = new JLabel(book.getAuthor(), SwingConstants.CENTER);
            JButton trolleyBtn = new JButton(new ImageIcon("add.png"));

            JPanel namePanel = new JPanel(new BorderLayout());
            namePanel.add(bookName, BorderLayout.CENTER);

            JPanel authorPanel = new JPanel(new BorderLayout());
            authorPanel.add(bookAuth, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(trolleyBtn, BorderLayout.CENTER);

            textAndButtonPanel.add(namePanel, BorderLayout.NORTH);
            textAndButtonPanel.add(authorPanel, BorderLayout.CENTER);
            textAndButtonPanel.add(buttonPanel, BorderLayout.SOUTH);

            bookPanelItem.add(textAndButtonPanel, BorderLayout.SOUTH);
            bookPanel.add(bookPanelItem);
        }
        return bookPanel;
    }

    private List<Book> loadBooksFromFile(String filename) {
        List<Book> bookList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(";");
                if (details.length == 3) {
                    bookList.add(new Book(details[0], details[1], details[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
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
}

