
import org.apache.commons.validator.routines.EmailValidator;
import org.sql2o.*;
import java.util.*;
import org.apache.commons.validator.*;

/**
 * Date: 7.8.17
 * <p>
 * sql2o framework methods that use JDBC to execute SQL statements with Java
 *
 * @author Alexander Young
 */
public class DB {

    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars?useSSL=false";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 10.8.17
     * <p>
     * fetches all entries from users table of the database and returns an
     * arraylist of Users
     *
     * @return an arraylist of Users, containing all the users found within the
     * users table of the database
     */
    public static List<User> fetchUsers() {
        String sql = "SELECT * "
                + "FROM users";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 20.9.17
     * <p>
     * fetches a user from the user table that matches a given email
     *
     * @return an arraylist of Users, containing only the user that matches the
     * given email
     */
    public static List<User> fetchUserByEmail(String email) {
        String sql = "SELECT * "
                + "FROM users "
                + "WHERE email = :email";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetch(User.class);
        }
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 10.8.17
     * <p>
     * fetches all entries from bookings table of the database and returns an
     * arraylist of Bookings
     *
     * @return an arraylist of Bookings, containing all the Bookings found
     * within the bookings table of the database
     */
    public static List<Booking> fetchBookings() {
        String sql = "SELECT * "
                + "FROM bookings";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Booking.class);
        }
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.8.17
     * <p>
     * fetches the most recent incomplete booking of a specified user, by
     * checking if end_date and end_time are NULL
     *
     * @param user_id unique id of user to fetch booking for
     * @return an arraylist of Bookings, containing only the users incomplete
     * booking, returns an empty list if the user has none
     */
    public static List<Booking> fetchCurrentBookingByUser_id(String user_id) {
        String sql = "SELECT * "
                + "FROM bookings "
                + "WHERE user_id = :user_id AND end_date IS NULL AND end_time IS NULL";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("user_id", user_id)
                    .executeAndFetch(Booking.class);
        }
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 22.8.17
     * <p>
     * Lists all bookings by user id from bookings table
     *
     * @param user_id unique id of user to fetch booking for
     * @return an arraylist of Bookings, containing all of the specified users
     * bookings, incomplete or complete
     */
    public static List<Booking> fetchAllBookingsByUser_id(String user_id) {
        String sql = "SELECT * "
                + "FROM bookings "
                + "WHERE user_id = :user_id";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("user_id", user_id)
                    .executeAndFetch(Booking.class);
        }
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 6.9.17
     * <p>
     * Fetches the last complete booking of a user.<p>
     *
     * Updated 7.9.17 by Rachel Tan<p>
     * Added working SQL commands.<p>
     *
     * Updated 7.9.17 by Rachel Tan<p>
     * Added command to display only bookings that do not have a null collection
     * date as those are canceled bookings.<p>
     * 
     * Updated 28.08.2017 by Vishal Pradhan<p>
     * Added additional field to the Database for registration including credit card details etc<p>
     * 
     * Updated 19.9.17 by Rachel Tan<p>
     * Edited command to correctly only retrieve one entry.
     *
     * @param user_id unique id of the user to fetch booking for
     * @return an arraylist of Bookings, containing only the users most recent
     * complete booking
     */
    public static List<Booking> fetchLastCompleteBookingOfUser(String user_id) {
        String sql = "SELECT * "
                + "FROM bookings "
                + "WHERE user_id = :user_id AND "
                + "collection_date IS NOT NULL AND "
                + "end_date IN (SELECT max(end_date) FROM bookings) "
                + "ORDER BY end_time desc limit 1;";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("user_id", user_id)
                    .executeAndFetch(Booking.class);
        }
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 10.8.17
     * <p>
     * Insert a new user into the database<p>
     *
     * @param email email address of the user
     * @param password password of the user, by this point the password should
     * already be encrypted as this is the state it will be stored in
     * @param f_name first name of the user
     * @param l_name last name of the user
     * @param address address of the user
     * @param license_no license number of the user
     * @param phone_no phone number of the user
     * @param card_name card name on the card on file for the user
     * @param card_no card number of the card on file for the user
     * @param expiry_month expiry month of the card on file for the user
     * @param expiry_year expiry year of the card on file for the user
     * @param cvv cvv of the card on file for the user
     * @return true if the insert is successful, false otherwise
     */
    public static boolean insertUser(String email, String password, String f_name, String l_name, String address, String license_no, String phone_no, String card_name, String card_no, String expiry_month, String expiry_year, String cvv) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO users ( email, password, f_name, l_name, address, license_no, phone_no, card_name, card_no, expiry_month, expiry_year, cvv ) "
                + "VALUES ( :email, :password, :f_name, :l_name, :address, :license_no, :phone_no, :card_name, :card_no, :expiry_month, :expiry_year, :cvv)";

        if (email == null || password == null) {
            System.out.println("null registration fields");
            return false;
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(email) == false) {
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

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 14.8.17
     * <p>
     * New entry is created in bookings table and is populated with the values
     * start date and time, and start location.<p>
     *
     * @param user_id unique id of the user who booked the car
     * @param car_id unique id of the car being booked
     * @param start_date start date of the booking
     * @param start_time start time of the booking
     * @param start_lat starting latitude of the car being booked
     * @param start_lng starting longitude of the car being booked
     * @return true if the booking is inserted successfully, false otherwise
     */
    public static boolean insertBooking(String user_id, String car_id, String start_date, String start_time, double start_lat, double start_lng) {
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

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 22.8.17
     * <p>
     * specified entry from bookings table is updated with the end date and time
     * values<p>
     *
     * @param booking_id unique id of the booking being canceled
     * @param end_date the date of the cancellation
     * @param end_time the time of the cancellation
     * @return true if the booking is canceled, otherwise false
     */
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

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 22.8.17
     * <p>
     * updates the collection date and time fields when a car has been
     * collected<p>
     *
     * Updated 22.8.17 by Rachel Tan<p>
     * Populates booking entry with collection time and date instead of
     * overwriting start time and date<p>
     *
     * @param booking_id unique id of the booking being canceled
     * @param collection_date the date of the collection
     * @param collection_time the time of the collection
     * @return true if the booking is collected, otherwise false
     */
    public static boolean collectCar(String booking_id, String collection_date, String collection_time) {
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

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 22.8.17
     * <p>
     * specified entry from bookings table is updated with the end date and time
     * and end location values<p>
     *
     * @param booking_id unique id of the booking being canceled
     * @param end_date the date of the collection
     * @param end_time the time of the collection
     * @param end_lat the end latitude of the car
     * @param end_lng the end longitude of the car
     * @return true if the booking is ended, otherwise false
     */
    public static boolean returnCar(String booking_id, String end_date, String end_time, double end_lat, double end_lng) {
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

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 11.8.17
     * <p>
     * fetches all entries from cars table<p>
     *
     * @return an arraylist of Car, containing all the cars in the cars table of
     * the database
     */
    public static List<Car> fetchCars() {
        String sql = "SELECT * "
                + "FROM cars";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Car.class);
        }
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 11.8.17
     * <p>
     * Insert a new car into the database<p>
     *
     * @param type the type of the car - e.g., sedan, hatchback, luxury
     * @param make the make of the car - e.g., Ford, Mazda, Toyota
     * @param model the model of the car - e.g., Festiva, Corolla, Camry
     * @param plate_no the registration plate number of the car
     * @param hourly_price the price in AUD that is charged for every hour the
     * car is rented
     * @param lat the latitude of the starting position of the car
     * @param lng the longitude of the starting position of the car
     * @return true if the car is inserted, otherwise false
     */
    public static boolean insertCar(String image, String type, String make, String model, String plate_no, double hourly_price, double lat, double lng) {
        //if type isn't supplied default to sedan
        if (type.length() <= 0 || make.length() <= 0 || model.length() <= 0 || plate_no.length() <= 0) {
            return false;
        }
        //connect to DB and insert car values
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "INSERT INTO cars (image, type, make, model, plate_no, hourly_price, lat, lng) "
                + "VALUES (:image, :type, :make, :model, :plate_no, :hourly_price, :lat, :lng)";
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

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 12.9.17
     * <p>
     * SQL handling of removing a car from the database based on plate_no<p>
     *
     * @param plate_no the registration plate number of the car to be deleted
     * @return true if the car is deleted, otherwise false
     */
    public static boolean deleteCar(String plate_no) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "DELETE FROM cars "
                + "WHERE plate_no = :plate_no";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("plate_no", plate_no)
                    .executeUpdate();
        }
        return true;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 12.8.17
     * <p>
     * specify user by email from users table and update location<p>
     *
     * @param email the email of the user to update
     * @param lat the new latitude of the users position
     * @param lng the new longitude of the users position
     * @return true if the position is updated, otherwise false
     */
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

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 16.8.17
     * <p>
     * update the availability of a specified car<p>
     *
     * @param plate_no the plate number of the car being updated
     * @param available value to update the availability state to, true is the
     * car can be booked, false if it cannot
     * @return true if the car is updated, otherwise false
     */
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
       
    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 19.9.17
     * <p>
     * Populates the cost field of the last completed booking of user.<p>
     * 
     * @param id ID of the booking
     * @param cost Total cost of the booking
     * @return True if the booking is updated, otherwise false
     * 
     * Updated 21.9.17 by Rachel Tan<p>
     * SQL query selects booking ID instead of last complete booking of user<p>
     */
    public static boolean updateTotalCostOfBooking(String booking_id, double cost) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "UPDATE bookings "
                + "SET cost = :cost "
                + "WHERE id = :booking_id";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("booking_id", booking_id)
                    .addParameter("cost", cost)
                    .executeUpdate();
                    }
        return true;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 19.9.17
     * <p>
     * update a specified car to a new latitude and longitude<p>
     *
     * @param plate_no the plate number of the car to update position for
     * @param lat the new latitude of the users position
     * @param lng the new longitude of the users position
     * @return true if the position is updated, otherwise false
     */
    public static boolean updateCarLatLng(String plate_no, double lat, double lng) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String updateSql = "UPDATE cars SET lat = :lat, lng = :lng WHERE plate_no = :plate_no";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                    .addParameter("plate_no", plate_no)
                    .addParameter("lat", lat)
                    .addParameter("lng", lng)
                    .executeUpdate();
        }
        return true;
    }
}
