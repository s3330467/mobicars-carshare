import org.sql2o.*;
import java.util.*;

public class UserService {

    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars";
    public static String sqlUser = "root";
    public static String sqlPass = "";


    // returns a list of all users
/*
    public static List<User> getAllUsers() {
        return User.getUserList();
    }
*/
    //returns an arraylist of all users in the database
    public static List<User> getAllUsers(){
        String sql = "SELECT *" +
                        "FROM user";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
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

    public static User getUserByEmail(String email) {

        int i;
        List<User> userList = getAllUsers();
        for(i = 0; i <userList.size(); i++) {
            if(userList.get(i).getEmail().equals(email)) {
                System.out.print("checking email: " + userList.get(i).getEmail());
                return userList.get(i);
            }
        }
        return null;


    }
    // creates a new user
    public static User createUser(String email, String password) {

        Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/mobicars", "root", "");
        String sql = "insert into user ( email, password ) values ( :email, :password )";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
        }
        return null;
    }

    public static User validateUser(String email, String password) {

        //get the user who matches the supplied email
        User user = getUserByEmail(email);
        //if no user is returned the email is not in the database, return no user
        if( user == null) {
            return null;
        }
        //if the password supplied matches the password of the email return the validated user
        else if(user.getPassword().equals(password)) {
            return user;
        }
        //otherwise return no user
        return user;
    }

    // updates an existing user
    public User updateUser(String id, String name, String email) { return null; }
}