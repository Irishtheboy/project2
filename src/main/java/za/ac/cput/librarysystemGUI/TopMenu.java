package za.ac.cput.librarysystemGui;

import za.ac.cput.librarysystemGUI.AudioBook;
import za.ac.cput.librarysystemGUI.AboutPage;
import za.ac.cput.librarysystemGUI.BookLib;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
        this.setSize(400, 150); // Increased height to accommodate label
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use BoxLayout for simpler layout management
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS)); // Vertical layout

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align label
        pnlCenter.add(lblTitle); // Add label to the panel

        // Create a new JPanel for buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center buttons in the panel
        btnBooks.addActionListener(this); // Add action listeners
        btnAudio.addActionListener(this);
        
        btnPanel.add(btnBooks); // Add books button
        btnPanel.add(btnAudio); // Add audio button

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

