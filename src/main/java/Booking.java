
import java.util.*;

/**
 * Date: 14.8.17
 * <p>
 * POJO Booking object declaring the bookings MySQL table fields as variables
 * <p>
 * Edited 22.8.17 by Rachel Tan<p>
 * Added the variables: collection_date, collection_time and end_date<p>
 * 
 * Edited 26.9.17 by Rachel Tan<p>
 *Added the variables: exp_date and exp_time<p>
 * 
 * @author Rachel Tan
 */
public class Booking {

    private String id;
    private String user_id;
    private String car_id;
    private String start_date;
    private String start_time;
    private String exp_date;
    private String exp_time;
    private String collection_date;
    private String collection_time;
    private String end_date;
    private String end_time;
    private double hours;
    private double cost;
    private double start_lat;
    private double start_lng;
    private double end_lat;
    private double end_lng;
    public static List<Booking> bookingList = new ArrayList<Booking>();

    /**
     * Constructor for a new booking
     * <p>
     * Author: <b>Rachel Tan</b>
     *
     * @param id the unique booking identifier number, auto incrementing integer
     * @param user_id the unique id of the user making the booking
     * @param car_id the unique id of the car being booking
     * @param start_date the date of the booking in the format yyyy-MM-dd
     * @param start_time the time of the booking in the format HH:mm:ss
     * @param exp_date the expected return date selected by user
     * @param exp_time the expected return time selected by user
     */
    public Booking(String id, String user_id, String car_id, String start_date, String start_time, String exp_date, String exp_time) {
        this.id = id;
        this.user_id = user_id;
        this.car_id = car_id;
        this.start_date = start_date;
        this.start_time = start_time;
        this.exp_date = exp_date;
        this.exp_time = exp_time;
    }

    @Override
    public String toString() {
        return "Booking{" + "booking_id=" + id + ", user_id=" + user_id + ", car_id=" + car_id + ", start_date=" + start_date + ", end_date=" + end_date + ", start_time=" + start_time + ", end_time=" + end_time + ", cost=" + cost + ", start_lat=" + start_lat + ", start_lng=" + start_lng + ", end_lat=" + end_lat + ", end_lng=" + end_lng + '}';
    }

    /**
     * used to synchronise the state of the static arraylist of bookings with
     * the state of the database.
     * <p>
     * Author: <b>Rachel Tan</b>
     */
    public static void updateBookingList() {
        bookingList = DB.fetchBookings();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    
    
    public String getCollection_date() {
        return collection_date;
    }

    public void setCollection_date(String collection_date) {
        this.collection_date = collection_date;
    }

    public String getCollection_time() {
        return collection_time;
    }

    public void setCollection_time(String collection_time) {
        this.collection_time = collection_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
       
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getStart_lat() {
        return start_lat;
    }

    public void setStart_lat(double start_lat) {
        this.start_lat = start_lat;
    }

    public double getStart_lng() {
        return start_lng;
    }

    public void setStart_lng(double start_lng) {
        this.start_lng = start_lng;
    }

    public double getEnd_lat() {
        return end_lat;
    }

    public void setEnd_lat(double end_lat) {
        this.end_lat = end_lat;
    }

    public double getEnd_lng() {
        return end_lng;
    }

    public void setEnd_lng(double end_lng) {
        this.end_lng = end_lng;
    }

}
