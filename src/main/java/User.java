/*
*Edited : Vishal Pradhan
Date: 29-08-2017
* Added additional fields on to the users during registration and their 
*credit card name, numbers etc
*includes get and set of the relevant field
*/

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
    private String card_name;
    private String card_no;
    private String expiry_month;
    private String expiry_year;
    private String cvv;
    private double lat;
    private double lng;
    public static List<User> userList = new ArrayList<User>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String f_name, String l_name, String email, String password, String address, String license_no, String phone_no, String card_name,
            String card_no, String expiry_month, String expiry_year, String cvv) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.license_no = license_no;
        this.phone_no = phone_no;
        this.card_name = card_name;
        this.card_no = card_no;
        this.expiry_month = expiry_month;
        this.expiry_year = expiry_year;
        this.cvv = cvv;
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
        return "User{" + "id=" + id + ", f_name=" + f_name + ", l_name=" + l_name + ", email=" + email + ", password=" + password + ", address=" + address + ", license_no=" + license_no + ", phone_no=" + phone_no + 
                ",card_name="+ card_name + ",card_no=" + card_no + ",expiry_month=" + expiry_month + ",expiry_year=" + expiry_year + ",cvv=" + cvv + ", lat=" + lat + ", lng=" + lng + '}';
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
    
    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }
    
    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }
    
    public String getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(String expiry_month) {
        this.expiry_month = expiry_month;
    }
    
     public String getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(String expiry_year) {
        this.expiry_year = expiry_year;
    }
    
     public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
}
