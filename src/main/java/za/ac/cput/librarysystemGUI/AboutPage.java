package za.ac.cput.librarysystemGUI;

import za.ac.cput.librarysystemGui.TopMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutPage extends JFrame implements ActionListener {

    private JButton backButton;
    private TopMenu topMenu; 

    
    public AboutPage(TopMenu topMenu) {
        super("About");
        this.topMenu = topMenu;  

        
        setLayout(new BorderLayout());
        setResizable(false); 

        
        JPanel pnlPurpose = new JPanel();
        pnlPurpose.setLayout(new GridLayout(2, 1));
        JLabel lblPurposeTitle = new JLabel("Purpose of the Library System", JLabel.CENTER);
        lblPurposeTitle.setFont(new Font("Arial", Font.BOLD, 16)); // Bold font for title
        JTextArea txtPurpose = new JTextArea(
            "The library system aims to streamline book management and user account services, allowing " +
            "efficient check-out, return, and payment of overdue fines for a better library experience."
        );
        txtPurpose.setEditable(false);
        txtPurpose.setLineWrap(true);
        txtPurpose.setWrapStyleWord(true);
        txtPurpose.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
       
        pnlPurpose.add(lblPurposeTitle);
        pnlPurpose.add(txtPurpose);

        // FAQs Section
        JPanel pnlFAQs = new JPanel();
        pnlFAQs.setLayout(new BorderLayout());
        JLabel lblFAQsTitle = new JLabel("Frequently Asked Questions (FAQs)", JLabel.CENTER);
        lblFAQsTitle.setFont(new Font("Arial", Font.BOLD, 16)); 
        lblFAQsTitle.setVerticalAlignment(JLabel.TOP);
        lblFAQsTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JTextArea txtFAQs = new JTextArea(
            "Q1: How do I check out books?\n"
            + "A1: You can check out books by navigating to the 'Check Out' section after logging in.\n\n"
            + "Q2: How do I pay overdue fines?\n"
            + "A2: You can pay your fines by going to your account page and selecting 'Pay'.\n\n"
            + "Q3: How do I search for books?\n"
            + "A3: Use the search feature in the main menu to find available books."
        );
        txtFAQs.setEditable(false);
        txtFAQs.setLineWrap(true);
        txtFAQs.setWrapStyleWord(true);
        txtFAQs.setPreferredSize(new Dimension(450, 250)); // Adjust height for more content
        txtFAQs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        pnlFAQs.add(lblFAQsTitle, BorderLayout.NORTH);
        pnlFAQs.add(txtFAQs, BorderLayout.CENTER);

        
        JPanel pnlContact = new JPanel();
        pnlContact.setLayout(new GridLayout(2, 1));
        JLabel lblContactTitle = new JLabel("Emergency Contact Information", JLabel.CENTER);
        lblContactTitle.setFont(new Font("Arial", Font.BOLD, 16)); // Bold font for title
        JTextArea txtContact = new JTextArea(
            "Phone: +1 800 555 1234\n"
            + "Email: support@librarysystem.com"
        );
        txtContact.setEditable(false);
        txtContact.setLineWrap(true);
        txtContact.setWrapStyleWord(true);
        txtContact.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        pnlContact.add(lblContactTitle);
        pnlContact.add(txtContact);

        
        backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(76, 175, 80)); 
        backButton.setForeground(Color.WHITE); 
        backButton.addActionListener(this); 

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(pnlPurpose);
        mainPanel.add(pnlFAQs);
        mainPanel.add(pnlContact);
        
        add(mainPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH); 

        
        setSize(500, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    
    public AboutPage() {
        this(null);  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            if (topMenu != null) {
                topMenu.setVisible(true); 
            }
            dispose(); 
        }
    }
}
