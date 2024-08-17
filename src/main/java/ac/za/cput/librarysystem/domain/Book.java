/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.za.cput.librarysystem.domain;


/**
 *
 * @author Sabura11
 */
public class Book {
    private String name;
    private String author;
    private String imagePath;

    public Book(String name, String author, String imagePath) {
        this.name = name;
        this.author = author;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getImagePath() {
        return imagePath;
    }
    
}
