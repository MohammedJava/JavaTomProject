
import SOEN387.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 
 * import junit to your code
 */

import SOEN387.DAOs.UserDAO;
import SOEN387.models.User;
import org.junit.jupiter.api.Test;

public class SetPasscodeServiceTest {

    private UserDAO userDAO;

    private UserService userService;

    
    
    @Test
    public void testSetPasscode_UserNotExists() {
        this.userService = new UserService();

        // Arrange
    	// old passcode doesnt exist in database
        String oldPasscode = "oldPasscode";
        String newPasscode = "newPasscode";
        
        boolean setpasscode = userService.setPasscode(new User(1, oldPasscode, false), newPasscode);
        assertEquals(false, setpasscode);
        
    }

    @Test
    public void testSetPasscode_Success() {
       
        this.userService = new UserService();

    	// old passcode exist in database
        String oldPasscode = "newPasscode";
        String newPasscode = "newPasscode";
        
        boolean setpasscode = userService.setPasscode(new User(1, oldPasscode, false), newPasscode);
        assertEquals(true, setpasscode);
       
    }

    @Test
    public void testSetPasscode_Failure_EmptyReplacement() {
        // Arrange
    	this.userService = new UserService();

    	// old passcode exist in database
        String oldPasscode = "some";
        String newPasscode = "";
        
        boolean setpasscode = userService.setPasscode(new User(1, oldPasscode, false), newPasscode);
        assertEquals(false, setpasscode);
  
    }
}
