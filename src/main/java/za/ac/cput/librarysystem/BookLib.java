package books;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class BookLib {
    JFrame frame;
    JLabel title, background;
    JPanel bottomPanel, BackgroundPanel;

    public BookLib() {
        frame = new JFrame("Book Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(880, 600);

//        background = new JLabel (new ImageIcon("background_pic.jpg"));
        
        
        
        title = new JLabel ("BOOKS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);
        
        JPanel bookPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        
        JPanel book1 = new JPanel (new BorderLayout());
        JLabel bookPic1 = new JLabel(new ImageIcon("first_book.jpg"));
        book1.add(bookPic1, BorderLayout.CENTER);  
        
       
        
        JPanel textAndButtonPanel = new JPanel();
        textAndButtonPanel.setLayout(new BoxLayout(textAndButtonPanel, BoxLayout.Y_AXIS));
        textAndButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName1 = new JLabel("The Hunger Games", SwingConstants.CENTER);
        JLabel bookAuth1 = new JLabel("Suzanne Collins", SwingConstants.CENTER);
        JButton trolleyBtn1 = new JButton(new ImageIcon("add.png"));

        bookName1.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth1.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn1.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel.add(bookName1);
        textAndButtonPanel.add(bookAuth1);
        textAndButtonPanel.add(trolleyBtn1);

        book1.add(textAndButtonPanel, BorderLayout.SOUTH);
        bookPanel.add(book1);             
//------------------------------------------------------------------------------
//        JPanel book2 = new JPanel (new BorderLayout());
//        JLabel bookPic2 = new JLabel(new ImageIcon("book_two.jpg"));
//        book1.add(bookPic2, BorderLayout.CENTER); 
//        
//        JPanel textPanel2 = new JPanel(new GridLayout(2,1));
//        JLabel bookName2 = new JLabel("Catching Fire", SwingConstants.CENTER);
//        JLabel bookAuth2 = new JLabel("Suzanne Collins", SwingConstants.CENTER);
//        textPanel2.add(bookName2);
//        textPanel2.add(bookAuth2);  
//        
//        JButton trolleyBtn2 = new JButton(new ImageIcon("add.png"));
//        textPanel2.add(trolleyBtn2);
//        
//        book2.add(textPanel2, BorderLayout.SOUTH);
//        bookPanel.add(book2

        JPanel book2 = new JPanel(new BorderLayout());
        JLabel bookPic2 = new JLabel(new ImageIcon("book_two.jpg"));
        book2.add(bookPic2, BorderLayout.CENTER);

        JPanel textAndButtonPanel2 = new JPanel();
        textAndButtonPanel2.setLayout(new BoxLayout(textAndButtonPanel2, BoxLayout.Y_AXIS));
        textAndButtonPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName2 = new JLabel("Catching Fire", SwingConstants.CENTER);
        JLabel bookAuth2 = new JLabel("Suzanne Collins", SwingConstants.CENTER);
        JButton trolleyBtn2 = new JButton(new ImageIcon("add.png"));

        bookName2.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth2.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn2.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel2.add(bookName2);
        textAndButtonPanel2.add(bookAuth2);
        textAndButtonPanel2.add(trolleyBtn2);

        book2.add(textAndButtonPanel2, BorderLayout.SOUTH);
        bookPanel.add(book2);
////------------------------------------------------------------------------------
//        JPanel book3 = new JPanel (new BorderLayout());
//        JLabel bookPic3 = new JLabel(new ImageIcon("book_three.jpg"));
//        book1.add(bookPic3, BorderLayout.CENTER);
//        
//        JPanel textPanel3 = new JPanel(new GridLayout(2,1));
//        JLabel bookName3 = new JLabel("Mocking Jay", SwingConstants.CENTER);
//        JLabel bookAuth3 = new JLabel("Suzanne Collins", SwingConstants.CENTER);
//        textPanel3.add(bookName3);
//        textPanel3.add(bookAuth3); 
//        
//        JButton trolleyBtn3 = new JButton(new ImageIcon("add.png"));
//        textPanel1.add(trolleyBtn3);
//        
//        book3.add(textPanel3, BorderLayout.SOUTH);
//        bookPanel.add(book3);    

        JPanel book3 = new JPanel(new BorderLayout());
        JLabel bookPic3 = new JLabel(new ImageIcon("book_three.jpg"));
        book3.add(bookPic3, BorderLayout.CENTER);

        JPanel textAndButtonPanel3 = new JPanel();
        textAndButtonPanel3.setLayout(new BoxLayout(textAndButtonPanel3, BoxLayout.Y_AXIS));
        textAndButtonPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName3 = new JLabel("Mocking Jay", SwingConstants.CENTER);
        JLabel bookAuth3 = new JLabel("Suzanne Collins", SwingConstants.CENTER);
        JButton trolleyBtn3 = new JButton(new ImageIcon("add.png"));

        bookName3.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth3.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn3.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel3.add(bookName3);
        textAndButtonPanel3.add(bookAuth3);
        textAndButtonPanel3.add(trolleyBtn3);

        book3.add(textAndButtonPanel3, BorderLayout.SOUTH);
        bookPanel.add(book3);
////-----------------------------------------------------------------------------       
//        JPanel book4 = new JPanel (new BorderLayout());
//        JLabel bookPic4 = new JLabel(new ImageIcon("book_four.jpg"));
//        book1.add(bookPic4, BorderLayout.CENTER);
//        
//        JPanel textPanel4 = new JPanel(new GridLayout(2,1));
//        JLabel bookName4= new JLabel("The Ballod of Songbirds & Snakes", SwingConstants.CENTER);
//        JLabel bookAuth4 = new JLabel("Suzanne Collins", SwingConstants.CENTER);
//        textPanel4.add(bookName4);
//        textPanel4.add(bookAuth4);
//        
//        JButton trolleyBtn4 = new JButton(new ImageIcon("add.png"));
//        textPanel1.add(trolleyBtn4);
//       
//        
//        book4.add(textPanel4, BorderLayout.SOUTH);
//        bookPanel.add(book4);

        JPanel book4 = new JPanel(new BorderLayout());
        JLabel bookPic4 = new JLabel(new ImageIcon("book_four.jpg"));
        book2.add(bookPic4, BorderLayout.CENTER);

        JPanel textAndButtonPanel4 = new JPanel();
        textAndButtonPanel4.setLayout(new BoxLayout(textAndButtonPanel4, BoxLayout.Y_AXIS));
        textAndButtonPanel4.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName4 = new JLabel("Songbirds & Snakes", SwingConstants.CENTER);
        JLabel bookAuth4 = new JLabel("Suzanne Collins", SwingConstants.CENTER);
        JButton trolleyBtn4 = new JButton(new ImageIcon("add.png"));

        bookName4.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth4.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn4.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel4.add(bookName4);
        textAndButtonPanel4.add(bookAuth4);
        textAndButtonPanel4.add(trolleyBtn4);

        book4.add(textAndButtonPanel4, BorderLayout.SOUTH);
        bookPanel.add(book4);
////-----------------------------------------------------------------------------        
//        JPanel book5 = new JPanel (new BorderLayout());
//        JLabel bookPic5 = new JLabel(new ImageIcon("book_five.jpg"));
//        book1.add(bookPic5, BorderLayout.CENTER);
//        
//        JPanel textPanel5 = new JPanel(new GridLayout(2,1));
//        JLabel bookName5 = new JLabel("Divergent", SwingConstants.CENTER);
//        JLabel bookAuth5 = new JLabel("Veronica Roth", SwingConstants.CENTER);
//        textPanel5.add(bookName5);
//        textPanel5.add(bookAuth5);
//        
//        JButton trolleyBtn5 = new JButton(new ImageIcon("add.png"));
//        textPanel1.add(trolleyBtn5);
//        
//        book5.add(textPanel5, BorderLayout.SOUTH);
//        bookPanel.add(book5);

        JPanel book5 = new JPanel(new BorderLayout());
        JLabel bookPic5 = new JLabel(new ImageIcon("book_five.jpg"));
        book2.add(bookPic5, BorderLayout.CENTER);

        JPanel textAndButtonPanel5 = new JPanel();
        textAndButtonPanel5.setLayout(new BoxLayout(textAndButtonPanel5, BoxLayout.Y_AXIS));
        textAndButtonPanel5.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName5 = new JLabel("Divergent", SwingConstants.CENTER);
        JLabel bookAuth5 = new JLabel("Veronica Roth", SwingConstants.CENTER);
        JButton trolleyBtn5 = new JButton(new ImageIcon("add.png"));

        bookName5.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth5.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn5.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel5.add(bookName5);
        textAndButtonPanel5.add(bookAuth5);
        textAndButtonPanel5.add(trolleyBtn5);

        book5.add(textAndButtonPanel5, BorderLayout.SOUTH);
        bookPanel.add(book5);
////-----------------------------------------------------------------------------        
//        JPanel book6 = new JPanel (new BorderLayout());
//        JLabel bookPic6 = new JLabel(new ImageIcon("book_six.jpg"));
//        book1.add(bookPic6, BorderLayout.CENTER);
//        
//        JPanel textPanel6 = new JPanel(new GridLayout(2,1));
//        JLabel bookName6 = new JLabel("Insurgent", SwingConstants.CENTER);
//        JLabel bookAuth6 = new JLabel("Veronica Roth", SwingConstants.CENTER);
//        textPanel6.add(bookName6);
//        textPanel6.add(bookAuth6);
//        
//        JButton trolleyBtn6 = new JButton(new ImageIcon("add.png"));
//        textPanel1.add(trolleyBtn6);
//        
//        book6.add(textPanel6, BorderLayout.SOUTH);
//        bookPanel.add(book6);

        JPanel book6 = new JPanel(new BorderLayout());
        JLabel bookPic6 = new JLabel(new ImageIcon("book_six.jpg"));
        book2.add(bookPic6, BorderLayout.CENTER);

        JPanel textAndButtonPanel6 = new JPanel();
        textAndButtonPanel6.setLayout(new BoxLayout(textAndButtonPanel6, BoxLayout.Y_AXIS));
        textAndButtonPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName6 = new JLabel("Insurgent", SwingConstants.CENTER);
        JLabel bookAuth6 = new JLabel("Veronica Roth", SwingConstants.CENTER);
        JButton trolleyBtn6 = new JButton(new ImageIcon("add.png"));

        bookName6.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth6.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn6.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel6.add(bookName6);
        textAndButtonPanel6.add(bookAuth6);
        textAndButtonPanel6.add(trolleyBtn6);

        book6.add(textAndButtonPanel6, BorderLayout.SOUTH);
        bookPanel.add(book6);

////-----------------------------------------------------------------------------          
//        JPanel book7 = new JPanel (new BorderLayout());
//        JLabel bookPic7 = new JLabel(new ImageIcon("book_seven.jpg"));
//        book1.add(bookPic5, BorderLayout.CENTER);
//        
//        JPanel textPanel7 = new JPanel(new GridLayout(2,1));
//        JLabel bookName7 = new JLabel("Allegiant", SwingConstants.CENTER);
//        JLabel bookAuth7 = new JLabel("Veronica Roth", SwingConstants.CENTER);
//        textPanel7.add(bookName7);
//        textPanel7.add(bookAuth7);
//        
//        JButton trolleyBtn7 = new JButton(new ImageIcon("add.png"));
//        textPanel1.add(trolleyBtn7);
//        
//        book7.add(textPanel7, BorderLayout.SOUTH);
//        bookPanel.add(book7);

        JPanel book7 = new JPanel(new BorderLayout());
        JLabel bookPic7 = new JLabel(new ImageIcon("book_seven.jpg"));
        book2.add(bookPic7, BorderLayout.CENTER);

        JPanel textAndButtonPanel7 = new JPanel();
        textAndButtonPanel7.setLayout(new BoxLayout(textAndButtonPanel7, BoxLayout.Y_AXIS));
        textAndButtonPanel7.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName7 = new JLabel("Allegiant", SwingConstants.CENTER);
        JLabel bookAuth7 = new JLabel("Veronica Roth", SwingConstants.CENTER);
        JButton trolleyBtn7 = new JButton(new ImageIcon("add.png"));

        bookName7.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth7.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn7.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel7.add(bookName7);
        textAndButtonPanel7.add(bookAuth7);
        textAndButtonPanel7.add(trolleyBtn7);

        book7.add(textAndButtonPanel7, BorderLayout.SOUTH);
        bookPanel.add(book7);

////-----------------------------------------------------------------------------    
//        JPanel book8 = new JPanel (new BorderLayout());
//        JLabel bookPic8 = new JLabel(new ImageIcon("book_eight.jpg"));
//        book1.add(bookPic8, BorderLayout.CENTER);
//        
//        JPanel textPanel8 = new JPanel(new GridLayout(2,1));
//        JLabel bookName8= new JLabel("Four", SwingConstants.CENTER);
//        JLabel bookAuth8 = new JLabel("Veronica Roth", SwingConstants.CENTER);
//        textPanel8.add(bookName8);
//        textPanel8.add(bookAuth8);
//        
//        JButton trolleyBtn8 = new JButton(new ImageIcon("add.png"));
//        textPanel1.add(trolleyBtn8);
//        
//        book8.add(textPanel8, BorderLayout.SOUTH);
//        bookPanel.add(book8);
        

        JPanel book8 = new JPanel(new BorderLayout());
        JLabel bookPic8 = new JLabel(new ImageIcon("book_eight.jpeg"));
        book2.add(bookPic8, BorderLayout.CENTER);

        JPanel textAndButtonPanel8 = new JPanel();
        textAndButtonPanel8.setLayout(new BoxLayout(textAndButtonPanel8, BoxLayout.Y_AXIS));
        textAndButtonPanel8.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookName8 = new JLabel("Four", SwingConstants.CENTER);
        JLabel bookAuth8 = new JLabel("Veronica Roth", SwingConstants.CENTER);
        JButton trolleyBtn8 = new JButton(new ImageIcon("add.png"));

        bookName8.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookAuth8.setAlignmentX(Component.CENTER_ALIGNMENT);
        trolleyBtn8.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAndButtonPanel8.add(bookName8);
        textAndButtonPanel8.add(bookAuth8);
        textAndButtonPanel8.add(trolleyBtn8);

        book8.add(textAndButtonPanel8, BorderLayout.SOUTH);
        bookPanel.add(book8);

        
        
        
        frame.add(bookPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton accountbtn = new JButton("Account");
        JButton checkoutbtn = new JButton("Check out");
        JButton topMenubtn = new JButton("Top Menu");


        bottomPanel.add(accountbtn);
        bottomPanel.add(checkoutbtn);
        bottomPanel.add(topMenubtn);
        

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

   
}

