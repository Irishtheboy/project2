package za.ac.cput.librarysystemGui;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AudioBook extends JFrame implements ActionListener {

    JFrame frame;
    JButton accountbtn, checkoutbtn, topMenubtn;
    List<Book> books;

    public AudioBook() {
        books = loadBooksFromFile("books.txt");

        frame = new JFrame("Book Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(880, 600);

        JLabel title = new JLabel("AUDIO BOOKS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        JPanel bookPanel = createBookPanel();
        frame.add(bookPanel, BorderLayout.CENTER);

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

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        for (Book book : books) {
            JPanel bookPanelItem = new JPanel(new BorderLayout());
            JLabel bookPic = new JLabel(new ImageIcon(book.getImagePath()));
            bookPanelItem.add(bookPic, BorderLayout.CENTER);

            JPanel textAndButtonPanel = new JPanel();
            textAndButtonPanel.setLayout(new BoxLayout(textAndButtonPanel, BoxLayout.Y_AXIS));
            textAndButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel bookName = new JLabel(book.getName(), SwingConstants.CENTER);
            JLabel bookAuth = new JLabel(book.getAuthor(), SwingConstants.CENTER);
            JButton trolleyBtn = new JButton(new ImageIcon("add.png"));

            bookName.setAlignmentX(Component.CENTER_ALIGNMENT);
            bookAuth.setAlignmentX(Component.CENTER_ALIGNMENT);
            trolleyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            textAndButtonPanel.add(bookName);
            textAndButtonPanel.add(bookAuth);
            textAndButtonPanel.add(trolleyBtn);

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

    private void openNewFrame() {
        JFrame newFrame = new JFrame("New Window");
        newFrame.setSize(400, 300);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("This is a new window", SwingConstants.CENTER);
        newFrame.add(label);

        newFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accountbtn) {
            new AccountPageGui();
            frame.dispose();
        } else if (e.getSource() == checkoutbtn) {
            JOptionPane.showMessageDialog(null, "Checked out successfully");
            new CheckoutPage();
            frame.dispose();
        } else if (e.getSource() == topMenubtn) {
            JOptionPane.showMessageDialog(null, "Back to top menu");
            new TopMenu();
            frame.dispose();
        }
    }

    // Inner class for Book
    private static class Book {

        private String name;
        private String author;
        private String imagePath;

        public Book(String name, String author, String imagePath) {
            this.name = name;
            this.author = author;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public String getAuthor() {
            return author;
        }

        public String getImagePath() {
            return imagePath;
        }
    }
}
    

