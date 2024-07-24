/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.ac.cput.librarysystem;

import javax.swing.SwingUtilities;

/**
 *
 * @author Sabura11
 */
public class LibrarySystem {

    public static void main(String[] args) {
// Use SwingUtilities to ensure GUI updates are performed on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Instantiate and display the login page
                new LoginPage();
            }
        });

    }
}
