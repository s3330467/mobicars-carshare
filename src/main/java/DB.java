import org.apache.commons.validator.routines.EmailValidator;
import org.sql2o.*;
import java.util.*;
import org.apache.commons.validator.*;

public class DB {
    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";

    public static List<User> fetchUsers() {
        String sql = "SELECT *" +
                "FROM users";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
    }
    
    public static List<Booking> fetchBookings() {
        String sql = "SELECT *" +
                "FROM bookings";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Booking.class);
        }
    }

    public static List<Booking> fetchBookingsByUserID() {
        String sql = "SELECT *" +
                "WHERE user_id = :user_id" +
                "FROM bookings";
        
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Booking.class);
        }
    }
    
    public static boolean insertUser(String email, String password, String f_name, String l_name, String address, String license_no, String phone_no) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO users ( email, password, f_name, l_name, address, license_no, phone_no ) "
                + "VALUES ( :email, :password, :f_name, :l_name, :address, :license_no, :phone_no)";
        
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
                    .executeUpdate();
        }
        return true;
    }
    
    public static boolean insertBooking(int user_id, int car_id, String start_date, String start_time, double start_lat, double start_lng) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO bookings (user_id, car_id, start_date, start_time, start_lat, start_lng)"
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
    
    public static boolean updateBooking(int booking_id,String end_date, String end_time, double end_lat, double end_lng) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "UPDATE bookings SET end_date = :end_date, end_time = :end_time, end_lat = :end_lat, end_lng = :end_lng)"
                + "WHERE booking_id = :booking_id";
                
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

    public static List<Car> fetchCars() {
        String sql = "SELECT *" +
                "FROM cars";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Car.class);
        }
    }


    public static boolean insertCar(String type, String make, String model, String plate_no, double hourly_price, double lat, double lng) {
        //if type isn't supplied default to sedan
        if(type.length()<=0 || make.length() <= 0 || model.length() <= 0 || plate_no.length() <= 0) {
            return false;
        }
        //connect to DB and insert car values
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO cars ( type, make, model, hourly_price, lat, lng ) VALUES ( :type, :make, :model, :hourly_price, :lat, :lng )";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
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