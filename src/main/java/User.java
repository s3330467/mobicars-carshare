import java.util.*;

public class User {
    private String id;
    private String employee_id;
    private String user_type;
    private String username;
    private String password;
    public static List<User> userList = new ArrayList<User>();

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", user_type='" + user_type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User(String id, String employee_id, String user_type, String username, String password) {
        this.id = id;
        this.employee_id = employee_id;
        this.user_type = user_type;
        this.username = username;
        this.password = password;
    }

    // getter/setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
