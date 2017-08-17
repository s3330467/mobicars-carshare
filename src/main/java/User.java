import java.util.*;

public class User {
    private String id;
    private String f_name;
    private String l_name;
    private String email;
    private String password;
    private String address;
    private String license_no;
    private String phone_no;
    private double lat;
    private double lng;
    public static List<User> userList = new ArrayList<User>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String f_name, String l_name, String email, String password, String address, String license_no, String phone_no) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.license_no = license_no;
        this.phone_no = phone_no;
    }
    
    public User(String id, String f_name, String l_name, String email, String password, String license_no, String phone_no, double lat, double lng) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.password = password;
        this.license_no = license_no;
        this.phone_no = phone_no;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", f_name=" + f_name + ", l_name=" + l_name + ", email=" + email + ", password=" + password + ", address=" + address + ", license_no=" + license_no + ", phone_no=" + phone_no + ", lat=" + lat + ", lng=" + lng + '}';
    }

    public static void updateUserList() {
        userList = DB.fetchUsers();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
