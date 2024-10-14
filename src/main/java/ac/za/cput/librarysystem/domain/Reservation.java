/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.za.cput.librarysystem.domain;

import java.util.Date;



/**
 *
 * @author Franco
 */
public class Reservation {
        private int reservationId;
    private int userId;
    private int bookId;
    private Date reservationDate;
    private boolean notified; // New attribute
    private Date expiresOn;   // New attribute

    public Reservation() {
    }

    // Constructors, Getters, and Setters

    public Reservation(int reservationId, int userId, int bookId, Date reservationDate, boolean notified, Date expiresOn) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
        this.notified = notified;
        this.expiresOn = expiresOn;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }
    
    
    
}