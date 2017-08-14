import org.apache.commons.validator.routines.EmailValidator;
import org.sql2o.*;
import java.util.*;
import org.apache.commons.validator.*;

public class DB {
    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";

    public static List<User> fetchUsersFromDB() {
        String sql = "SELECT *" +
                "FROM users";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
    }

    public static List<Booking> fetchBookingsFromDB() {
        String sql = "SELECT *" +
                "FROM users";
        
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Booking.class);
        }
    }
    
    public static boolean insertUser(String email, String password, String f_name, String l_name, String address, String license_no, String phone_no) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "insert into users ( email, password, f_name, l_name, address, license_no, phone_no ) "
                + "values ( :email, :password, :f_name, :l_name, :address, :license_no, :phone_no)";
        
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
        String sql = "insert into bookings (user_id, car_id, start_date, start_time, start_lat, start_lng)"
                + "values (:user_id, :car_id, :start_date, :start_time, :start_lat, :start_lng)";
        
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

    public static List<Car> fetchCarsFromDB() {
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
        String sql = "insert into cars ( type, make, model, hourly_price, lat, lng ) values ( :type, :make, :model, :hourly_price, :lat, :lng )";
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
        String updateSql = "update users set lat = :lat, lng = :lng where email = :email";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                .addParameter("email", email)
                .addParameter("lat", lat)
                .addParameter("lng", lng)
                .executeUpdate();
        }
        return true;
    }
}