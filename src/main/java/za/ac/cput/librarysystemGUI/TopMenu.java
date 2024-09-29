package za.ac.cput.librarysystemGui;

import za.ac.cput.librarysystemGUI.AudioBook;
import java.awt.BorderLayout;
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

    public TopMenu() {
        super("Top Menu");

        pnlCenter = new JPanel();
        btnBooks = new JButton("Books");
        btnAudio = new JButton("Audio");

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

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Set up the main frame
        this.setVisible(true);
        this.setSize(400, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pnlCenter.setLayout(new GridLayout(1, 2));
        pnlCenter.add(btnBooks);
        pnlCenter.add(btnAudio);

        btnBooks.addActionListener(this);
        btnAudio.addActionListener(this);

        this.add(pnlCenter, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBooks) {
            JOptionPane.showMessageDialog(null, "Welcome to the books section");
            new BookLib();
            dispose();
        } else if (e.getSource() == btnAudio) {
            JOptionPane.showMessageDialog(null, "Welcome to the audio section");
            new AudioBook();
            dispose();
        }
    }
}

