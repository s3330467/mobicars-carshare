
import org.sql2o.*;
import java.util.*;
import org.jasypt.util.password.*;

/**
 * Date: 7.8.17
 * <p>
 * Contains methods that manipulate User data and calls SQL methods in the DB
 * class to insert or retrieve user data
 *
 * Updated 17.9.17 by Alexander Young<p>
 * removed sql static variables as database methods now have their own class
 * DB.java<p>
 *
 * @author Alexander Young
 */
public class UserService {

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 7.8.17
     * <p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateUserList method<p>
     *
     * @return a list of all users found in the database
     */
    public static List<User> getAllUsers() {
        return User.userList;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 7.8.17
     * <p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateUserList method<p>
     *
     * @param id unique id of the user to retrieve from the database
     * @return a single User object for the user that matches the id given, or
     * null if no match is found
     */
    public static User getUser(String id) {
        int i;
        for (i = 0; i < User.userList.size(); i++) {
            if (User.userList.get(i).getId().equals(id)) {
                return User.userList.get(i);
            }
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 9.8.17
     * <p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateUserList method<p>
     *
     * @param email unique email of the user to retrieve from the database
     * @return a single User object for the user that matches the email given,
     * or null if no match is found
     */
    public static User getUserByEmail(String email) {
        int i;
        for (i = 0; i < User.userList.size(); i++) {
            if (User.userList.get(i).getEmail().equals(email)) {
                return User.userList.get(i);
            }
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 7.8.17
     * <p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateUserList method<p>
     *
     * @param id unique id of the user to retrieve from the database
     * @return a single User object for the user that matches the id given, or
     * null if no match is found
     */
    public static User getUserById(String id) {
        int i;
        for (i = 0; i < User.userList.size(); i++) {
            if (User.userList.get(i).getId().equals(id)) {
                return User.userList.get(i);
            }
        }
        return null;
    }
    
    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 8.8.17
     * <p>
     * encrypts the password provided by the user and then calls the DB method
     * to insert the user into the database<p>
     *
     * Updated 29.08.2017  by 29-08-2017 Vishal Pradhan<p>
     * Added card details to be stored along with users personal details<p>
     * 
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateUserList method, now also adds a user to
     * the userList directly rather than replacing the entire list with an
     * import from the DB<p>
     *
     * @param email email address of the user
     * @param userPassword password of the user, at this point the password is
     * not encrypted
     * @param f_name first name of the user
     * @param l_name last name of the user
     * @param address address of the user
     * @param license_no license number of the user
     * @param phone_no phone number of the user
     * @param card_name card name on the card on file for the user
     * @param card_no card number of the card on file for the user
     * @param expiry_month expiry month of the card on file for the user
     * @param expiry_year expiry year of the card on file for the user
     * @param cvv cvv of the card on file for the user
     * @return true if the user is created, false otherwise
     */
   
    // creates a new user
    public static boolean createUser(String email, String userPassword, 
            String f_name, String l_name, String address, String license_no, String phone_no, String card_name, String card_no, String expiry_month, String expiry_year, String cvv) {
        User user;
        //create an encrypted password based off the supplied password string
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);

        if (DB.insertUser(email, encryptedPassword, f_name, l_name, address, license_no, phone_no, card_name, card_no, expiry_month, expiry_year, cvv)) {
            user = DB.fetchUserByEmail(email).get(0);
             User.userList.add(user);
            return true;
        }
        return false;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 8.8.17
     * <p>
     * login validation<p>
     * checks that the supplied plain text password matches the encrypted
     * password stored in the database<p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateUserList method<p>
     *
     * @param email email address of the user
     * @param inputPassword password of the user, at this point the password is
     * not encrypted
     * @return true if the email and password provided are valid credentials,
     * false otherwise
     */
    public static Boolean validateUser(String email, String inputPassword) {
        //get the user who matches the supplied email
        User user = getUserByEmail(email);
        //if no user is returned the email is not in the database, return false
        if (user == null) {
            System.out.println("user not validated because user doesnt exist");
            return false;
        }
        //compare the supplied password with the users encrypted password, return true if they match
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        if (passwordEncryptor.checkPassword(inputPassword, user.getPassword())) {
            return true;
        } else {
            System.out.println("passwords do not match");
            return false;
        }
    }
}
