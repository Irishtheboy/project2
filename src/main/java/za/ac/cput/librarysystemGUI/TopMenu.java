package za.ac.cput.librarysystemGui;

import za.ac.cput.librarysystemGUI.AudioBook;
import za.ac.cput.librarysystemGUI.AboutPage;
import za.ac.cput.librarysystemGUI.BookLib;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TopMenu extends JFrame implements ActionListener {

    private JPanel pnlCenter;
    private JButton btnBooks, btnAudio;
    private JLabel lblTitle;

    public TopMenu() {
        super("Top Menu");

        pnlCenter = new JPanel();
        btnBooks = new JButton("Books");
        btnAudio = new JButton("Audio");
        lblTitle = new JLabel("Welcome to the library system!", SwingConstants.CENTER);
        
        btnBooks.setBackground(Color.green);
        btnAudio.setBackground(Color.green);
        this.setBackground(Color.yellow);
        setTopMenu();
    }

    public void setTopMenu() {
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

        this.setSize(400, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlCenter.add(lblTitle);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnBooks.addActionListener(this);
        btnAudio.addActionListener(this);
        
        btnPanel.add(btnBooks);
        btnPanel.add(btnAudio);

        pnlCenter.add(btnPanel);

        this.add(pnlCenter, BorderLayout.CENTER);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnBooks) {
            JOptionPane.showMessageDialog(this, "Welcome to the books section");
            new BookLib();
            dispose();
        } else if (source == btnAudio) {
            JOptionPane.showMessageDialog(this, "Welcome to the audio section");
            new AudioBook();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Unknown action performed");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TopMenu::new);
    }
}
