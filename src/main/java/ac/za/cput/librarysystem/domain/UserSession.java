package ac.za.cput.librarysystem.domain;

import ac.za.cput.librarysystem.dao.UserDAO;

public class UserSession {
    private static String loggedInUsername;
    private static int loggedInUserId;

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }


    public static void retrieveAndSetUserId() {
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserIdByUsername(loggedInUsername); 
        if (userId != -1) {
            setLoggedInUserId(userId);  
        } else {
            System.out.println("User ID not found for username: " + loggedInUsername);
        }
    }
}
