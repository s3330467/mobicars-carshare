import org.sql2o.*;
import java.util.*;

public class UserService {

    // returns a list of all users
/*
    public static List<User> getAllUsers() {
        return User.getUserList();
    }
*/

    public static List<User> getAllUsers(){
        String sql = "SELECT *" +
                        "FROM user";

        Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/mobicars", "root", "");
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
    }


    // returns a single user by id
    public static User getUser(String id) {

        int i;
        List<User> userList = getAllUsers();
        for (i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(id)) {
                return userList.get(i);
            }
        }
        return null;
    }

    public static User getUserByUsername(String username) {

        int i;
        List<User> userList = getAllUsers();
        for(i = 0; i <userList.size(); i++) {
            if(userList.get(i).getUsername().equals(username)) {
                return userList.get(i);
            }
        }
        return null;


    }
    // creates a new user
    public User createUser(String name, String email) { return null; }
    // updates an existing user
    public User updateUser(String id, String name, String email) { return null; }
}