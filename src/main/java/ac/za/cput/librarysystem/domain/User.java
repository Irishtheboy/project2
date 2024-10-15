package ac.za.cput.librarysystem.domain;


/**
 *
 * @author pholo and Franco And Naqeebah
 */
public class User {
    public String user_Name;
    public String userEmail;
    public String userPhone;
    public String userPassword;
    public int rentedBooksCount; 

    public User(String user_Name, String userEmail, String userPhoneNumber, String userPassword) {
        this.user_Name = user_Name;
        this.userEmail = userEmail;
        this.userPhone = userPhoneNumber;
        this.userPassword = userPassword;
        this.rentedBooksCount = 0; 
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getRentedBooksCount() {
        return rentedBooksCount; // Get the count of rented books
    }

    public void incrementRentedBooksCount() {
        rentedBooksCount++; // Increment the rented books count
    }

    public void decrementRentedBooksCount() {
        rentedBooksCount--; // Decrement the rented books count
    }

    @Override
    public String toString() {
        return "User{" +
                "user_Name='" + user_Name + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", rentedBooksCount=" + rentedBooksCount + // Add rentedBooksCount to the string
                '}';
    }
}