import org.sql2o.*;
import java.util.*;
import org.jasypt.util.password.*;

public class UserService {

    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";


    // returns a list of all users
    public static List<User> getAllUsers() {
        User.updateUserList();
        return User.userList;
    }


    // returns a single user by id
    public static User getUser(String id) {

        int i;
        User.updateUserList();
        for (i = 0; i < User.userList.size(); i++) {
            if (User.userList.get(i).getId().equals(id)) {
                return User.userList.get(i);
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {

        int i;
        User.updateUserList();
        for(i = 0; i <User.userList.size(); i++) {
            if(User.userList.get(i).getEmail().equals(email)) {
                System.out.print("checking email: " + User.userList.get(i).getEmail());
                return User.userList.get(i);
            }
        }
        return null;
    }
    // creates a new user
    public static boolean createUser(String email, String userPassword) {

        //create an encrypted password based off the supplied password string
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);

        if(!DB.insertUser(email, encryptedPassword)) {
            return false;
        }
        User.updateUserList();
        return  true;
    }

    public static Boolean validateUser(String email, String inputPassword) {
        //ensure the userlist is up to date
        User.updateUserList();
        //get the user who matches the supplied email
        User user = getUserByEmail(email);
        //if no user is returned the email is not in the database, return false
        if( user == null) {
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

    // updates an existing user
    public User updateUser(String id, String name, String email) { return null; }
}