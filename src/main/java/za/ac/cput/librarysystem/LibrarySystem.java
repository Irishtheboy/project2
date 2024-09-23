/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.ac.cput.librarysystem;


import javax.swing.SwingUtilities;
import za.ac.cput.librarysystemGui.LoginPage;



/**
 *
 * @author Sabura11
 */
public class LibrarySystem {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                new LoginPage();
            }
        });
    }
}
