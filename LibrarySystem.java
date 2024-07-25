/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.ac.cput.librarysystem;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author pholo
 */
public class LibrarySystem {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
        SignupPageGui signUp = new SignupPageGui();
        
        signUp.setVisible(true);
        
        });
        
        
    }
}
