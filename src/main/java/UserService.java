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
    public static User createUser(String username, String password) {

        Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/mobicars", "root", "");
        String sql = "insert into user ( username, password ) values ( :username, :password )";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("username", username)
                    .addParameter("password", password)
                    .executeUpdate();
        }
        return null;
    }

    public static User validateUser(String username, String password) {

        //get the user who matches the supplied username
        User user = getUserByUsername(username);

        //if no user is returned the username is not in the database, return no user
        if( user == null) {
            return null;
        }
        //if the password supplied matches the password of the username return the validated user
        else if(user.getPassword().equals(password)) {
            return user;
        }
        //otherwise return no user
        return user;
    }

    // updates an existing user
    public User updateUser(String id, String name, String email) { return null; }
}