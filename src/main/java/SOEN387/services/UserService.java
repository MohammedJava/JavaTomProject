package SOEN387.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import SOEN387.DAOs.UserDAO;
import SOEN387.models.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User findByPasscode(String passcode) {
        return userDAO.findByPasscode(passcode);
    }
    
    
    public HashMap<String, Boolean> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        HashMap<String, Boolean> userMap = new HashMap<>();

        for (User user : users) {
            userMap.put(user.getPasscode(), user.isStaff());
        }

        return userMap;
    }
    
    public List<User> getAllUsersExceptSelf(String passcode) {
        return  userDAO.getAllUsersExceptSelf(passcode);
    }
    
    
    public boolean createUser(String passcode, boolean admin) {
            if (userDAO.userExists(passcode)) {
                return true;
            }
            userDAO.createUser(passcode, admin);
            return false;
       
    }
    
    public boolean setPasscode(User user, String newPasscode) {
    	
        if (userDAO.userExists(user.getPasscode()) || !newPasscode.equals("")) {
            boolean condition = userDAO.changePasscode(user.getPasscode(), newPasscode);
            return condition;
        }
        return false; 
}
    
    
    public boolean GrantStaffPrivilege(User user, String role) {
    	if (role.equals("admin")) {
    		return userDAO.GrantStaffPrivilege(user.getId(), true);
    	}
    	else if (role.equals("customer")) {
    		return userDAO.GrantStaffPrivilege(user.getId(), false);
    	}
    	return false;
    }
    
    public List<User> getAllCustomers(){
    	return userDAO.getAllCustomers();
    	}


   
}




