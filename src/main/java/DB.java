/**
 *
 * @author Alexander
 * Date: Alex, please fill this in
 * Class: DB
 * Description: sql2o framework methods that use JDBC to execute SQL statements
 *              with Java
 */

import org.apache.commons.validator.routines.EmailValidator;
import org.sql2o.*;
import java.util.*;
import org.apache.commons.validator.*;

public class DB {
    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars?useSSL=false";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";

//    fetches all entries from users table
    public static List<User> fetchUsers() {
        String sql = "SELECT * " +
                "FROM users";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
    }
//    fetches all entries from bookings table
    public static List<Booking> fetchBookings() {
        String sql = "SELECT * " +
                "FROM bookings";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Booking.class);
        }
    }
    
//    fetches a booking that matches the supplied user_id and also has not been 
//    concluded i.e a currently active booking, as defined by the
//    unpopulated end date and time fields
    public static List<Booking> fetchCurrentBookingByUser_id(String user_id) {
        String sql = "SELECT * " +
                "FROM bookings " +
                "WHERE user_id = :user_id AND end_date IS NULL AND end_time IS NULL";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                .addParameter("user_id", user_id)
                .executeAndFetch(Booking.class);
        }
    }
//    lists all bookings by user id from bookings table
    public static List<Booking> fetchAllBookingsByUser_id(String user_id) {
        String sql = "SELECT * " +
                "FROM bookings " +
                "WHERE user_id = :user_id";
        
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                .addParameter("user_id", user_id)
                .executeAndFetch(Booking.class);
        }
    }
    
//    author: Rachel, 6.9.17
//    Fetches the last completed booking of a user
    public static List<Booking> fetchLastCompleteBookingOfUser(String user_id) {
        String sql = "SELECT * " +
                "FROM bookings " +
                "WHERE user_id = :user_id AND " +
                "collection_date = NOT NULL AND " +
                "end_date IN (SELECT max(end_date) FROM bookings) " +
                "AND end_time = (select max(end_time) from bookings);";
        
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                .addParameter("user_id", user_id)
                .executeAndFetch(Booking.class);
        }
    }
    
//    new entry is created in users table and populates it with given values.
//    returns false if email and password fields are blank or invalid email given
    public static boolean insertUser(String email, String password, String f_name, String l_name, String address, String license_no, String phone_no, String card_name, String card_no, String expiry_month, String expiry_year, String cvv) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO users ( email, password, f_name, l_name, address, license_no, phone_no, card_name, card_no, expiry_month, expiry_year, cvv ) "
                + "VALUES ( :email, :password, :f_name, :l_name, :address, :license_no, :phone_no, :card_name, :card_no, :expiry_month, :expiry_year, :cvv)";
        
        if(email == null || password == null) {
            System.out.println("null registration fields");
            return false;
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        if( emailValidator.isValid(email) == false) {
            System.out.println("email is invalid");
            return false;
        }
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .addParameter("f_name", f_name)
                    .addParameter("l_name", l_name)
                    .addParameter("address", address)
                    .addParameter("license_no", license_no)
                    .addParameter("phone_no", phone_no)
                    .addParameter("card_name", card_name)
                    .addParameter("card_no", card_no)
                    .addParameter("expiry_month", expiry_month)
                    .addParameter("expiry_year", expiry_year)
                    .addParameter("cvv", cvv)
                    .executeUpdate();
        }
        return true;
    }
    
//    new entry is created in bookings table and is populated with the values
//    start date and time, and start location.
    public static boolean insertBooking(int user_id, int car_id, String start_date, String start_time, double start_lat, double start_lng) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO bookings (user_id, car_id, start_date, start_time, start_lat, start_lng) "
                + "VALUES (:user_id, :car_id, :start_date, :start_time, :start_lat, :start_lng)";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("user_id", user_id)
                    .addParameter("car_id", car_id)
                    .addParameter("start_date", start_date)
                    .addParameter("start_time", start_time)
                    .addParameter("start_lat", start_lat)
                    .addParameter("start_lng", start_lng)
                    .executeUpdate();
        }
        return true;
    }
    
//    specified entry from bookings table is updated with the end date and time
//    values
    public static boolean cancelBooking(String booking_id, String end_date, String end_time) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "UPDATE bookings "
                + "SET end_date = :end_date, end_time = :end_time "
                + "WHERE id = :booking_id";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("booking_id", booking_id)
                    .addParameter("end_date", end_date)
                    .addParameter("end_time", end_time)
                    .executeUpdate();
        }
        return true;
    }
    
//    UNUNSED
    public static boolean updateBooking(String booking_id, String start_date, String start_time, String end_date, String end_time, double end_lat, double end_lng) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "UPDATE bookings "
                + "SET start_date = :start_date, start_time = :start_time, end_date = :end_date, end_time = :end_time, end_lat = :end_lat, end_lng = :end_lng "
                + "WHERE id = :booking_id";
                
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("booking_id", booking_id)
                    .addParameter("end_date", end_date)
                    .addParameter("end_time", end_time)
                    .addParameter("end_lat", end_lat)
                    .addParameter("end_lng", end_lng)
                    .executeUpdate();
        }
        return true;
    }
    
//    specified entry from bookings table is updated with the collection date 
//    and time values
    public static boolean collectCar(String booking_id, String collection_date, String collection_time){
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "UPDATE bookings "
                + "SET collection_date = :collection_date, collection_time = :collection_time "
                + "WHERE id = :booking_id";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("booking_id", booking_id)
                    .addParameter("collection_date", collection_date)
                    .addParameter("collection_time", collection_time)
                    .executeUpdate();
        }
        return true;
    }
    
//    specified entry from bookings table is updated with the end date and time
//    and end location values
    public static boolean returnCar(String booking_id, String end_date, String end_time, double end_lat, double end_lng){
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "UPDATE bookings "
                + "SET end_date = :end_date, end_time = :end_time, end_lat = :end_lat, end_lng = :end_lng "
                + "WHERE id = :booking_id";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("booking_id", booking_id)
                    .addParameter("end_date", end_date)
                    .addParameter("end_time", end_time)
                    .addParameter("end_lat", end_lat)
                    .addParameter("end_lng", end_lng)
                    .executeUpdate();
        }
        return true;
    }

//    fetches all entries from cars table
    public static List<Car> fetchCars() {
        String sql = "SELECT * " +
                "FROM cars";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Car.class);
        }
    }

//  creates new entry into cars table with all fields populated
    public static boolean insertCar(String image, String type, String make, String model, String plate_no, double hourly_price, double lat, double lng) {
        //if type isn't supplied default to sedan
        if(type.length()<=0 || make.length() <= 0 || model.length() <= 0 || plate_no.length() <= 0) {
            return false;
        }
        //connect to DB and insert car values
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO cars ( image, type, make, model, hourly_price, lat, lng ) VALUES ( :type, :make, :model, :hourly_price, :lat, :lng )";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("image", image)
                    .addParameter("type", type)
                    .addParameter("make", make)
                    .addParameter("model", model)
                    .addParameter("plate_no", plate_no)
                    .addParameter("hourly_price", hourly_price)
                    .addParameter("lat", lat)
                    .addParameter("lng", lng)
                    .executeUpdate();
        }
        return true;
    }
    
//    specified entry by email from users table is updated with location
    public static boolean updateUserLatLng(String email, double lat, double lng) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String updateSql = "UPDATE users SET lat = :lat, lng = :lng WHERE email = :email";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                .addParameter("email", email)
                .addParameter("lat", lat)
                .addParameter("lng", lng)
                .executeUpdate();
        }
        return true;
    }
    
//    specified entry by plate number from cars table is updated with availability
    public static boolean updateCarAvailable(String plate_no, boolean available) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String updateSql = "UPDATE cars SET available = :available WHERE plate_no = :plate_no";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                .addParameter("plate_no", plate_no)
                .addParameter("available", available)
                .executeUpdate();
        }
        return true;
    }
}