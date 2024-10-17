package za.ac.cput.librarysystemGui;

import za.ac.cput.librarysystemGUI.AudioBook;
import za.ac.cput.librarysystemGUI.AboutPage;
import za.ac.cput.librarysystemGUI.BookLib;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Sabura11
 */
public class TopMenu extends JFrame implements ActionListener {

    private JPanel pnlCenter;
    private JButton btnBooks, btnAudio;
    private JLabel lblTitle;

    public TopMenu() {
        super("Top Menu");

        // Initialize components
        pnlCenter = new JPanel();
        btnBooks = new JButton("Books");
        btnAudio = new JButton("Audio");
        lblTitle = new JLabel("Welcome to the library system!", SwingConstants.CENTER); // Center lbl text

        setTopMenu();
    }

    public void setTopMenu() {
        // Set up the menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        fileMenu.add(exitItem);

        // Add Feedback option to the file menu
        JMenuItem feedbackItem = new JMenuItem("Feedback");
        feedbackItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FeedbackPage(); // Open the feedback page
            }
        });
        fileMenu.add(feedbackItem); // Add the feedback item to the file menu

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutPage(); // Open the feedback page
            }
        });
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Set up the main frame
        this.setSize(400, 300); // Increased height to accommodate label
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color to white
        pnlCenter.setBackground(Color.WHITE);

        // Customize the label (title) with color and font size
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size and style
        lblTitle.setForeground(Color.BLUE); // Set title color
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align label
        pnlCenter.add(lblTitle); // Add label to the panel

        // Create a new JPanel for buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering buttons
        btnPanel.setBackground(Color.WHITE); // Set background of the button panel to white

        // Customize the buttons
        btnBooks.setBackground(new Color(76, 175, 80)); // Set background color to green
        btnBooks.setForeground(Color.WHITE); // Set text color to white
        btnBooks.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        btnBooks.setFocusPainted(false); // Remove focus border around button

        btnAudio.setBackground(new Color(76, 175, 80)); // Set background color to green
        btnAudio.setForeground(Color.WHITE); // Set text color to white
        btnAudio.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        btnAudio.setFocusPainted(false); // Remove focus border around button

        btnBooks.addActionListener(this); // Add action listeners
        btnAudio.addActionListener(this);

        // Add buttons to the panel using GridBagLayout for centered alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        btnPanel.add(btnBooks, gbc); // Add books button at (0, 0)

        gbc.gridy = 1;
        btnPanel.add(btnAudio, gbc); // Add audio button at (0, 1)

        pnlCenter.add(btnPanel); // Add button panel to main panel

        this.add(pnlCenter, BorderLayout.CENTER); // Add main panel to frame
        this.setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource(); // Get the source of the event

        if (source == btnBooks) {
            JOptionPane.showMessageDialog(this, "Welcome to the books section");
            new BookLib(); // Open the BookLib window
            dispose(); // Close the current window
        } else if (source == btnAudio) {
            JOptionPane.showMessageDialog(this, "Welcome to the audio section");
            new AudioBook(); // Open the AudioBook window
            dispose(); // Close the current window
        } else {
            // Optional: Handle other buttons or show an error message
            JOptionPane.showMessageDialog(this, "Unknown action performed");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TopMenu::new);
    }
}



