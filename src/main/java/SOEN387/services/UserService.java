package SOEN387.services;

import SOEN387.DAOs.UserDAO;
import SOEN387.models.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
    
    
    public boolean createUser(String username) {
            if (userDAO.userExists(username)) {
                return true;
            }
            String password = "not_needed";
            userDAO.createUser(username, password);
            return false;
       
    }


   
}




