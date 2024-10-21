package za.ac.cput.librarysystemGui;

import ac.za.cput.librarysystem.domain.UserSession;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SubmitFrameGui extends JFrame {

    public SubmitFrameGui() {
        initializeUI();

    }

    private void initializeUI() {
        setTitle("Submit Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        getContentPane().setBackground(Color.YELLOW);
        setLayout(new BorderLayout());
        setResizable(true);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(3, 3));


        JLabel label1 = new JLabel("Books reserved.", SwingConstants.CENTER);
        JLabel label2 = new JLabel("Make sure to pick up books at the library.", SwingConstants.CENTER);
        JLabel label3 = new JLabel("<html>Return the books within <font color='red'>30 days</font>.</html>", SwingConstants.CENTER); // Changes the color of "30 days" to red.

        labelPanel.add(label1);
        labelPanel.add(label2);
        labelPanel.add(label3);

        add(labelPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton accountButton = new JButton("Account");
        JButton topMenuButton = new JButton("Top Menu");
        JButton logoutButton = new JButton("Log out");


        buttonPanel.add(accountButton);
        buttonPanel.add(topMenuButton);
        buttonPanel.add(logoutButton);

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = UserSession.getLoggedInUserId();
                String username = UserSession.getLoggedInUsername();
                new AccountPageGui(userId, username);
                dispose();
            }
        });
        topMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SubmitFrameGui.this, "Top Menu button has been clicked!");
                dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SubmitFrameGui.this, "Log Out button has been clicked!");

                dispose();
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
