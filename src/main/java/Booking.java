import java.util.*;

public class Booking {
    private int user_id;
    private int car_id;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private double cost;
    private double start_lat;
    private double start_lng;
    private double end_lat;
    private double end_lng;
    public static List<Booking> bookingList = new ArrayList<Booking>();
    
        public Booking(int user_id, int car_id, String start_date, String start_time, double start_lat, double start_lng) {
            this.user_id = user_id;
            this.car_id = car_id;
            this.start_date = start_date;
            this.start_time = start_time;
            this.start_lat = start_lat;
            this.start_lng = start_lng;
        }
    
    public Booking(int user_id, int car_id, String start_date, String end_date, String start_time, String end_time, double cost, double start_lat, double start_lng, double end_lat, double end_lng) {
            this.user_id = user_id;
            this.car_id = car_id;
            this.start_date = start_date;
            this.end_date = end_date;
            this.start_time = start_time;
            this.end_time = end_time;
            this.cost = cost;
            this.start_lat = start_lat;
            this.start_lng = start_lng;
            this.end_lat = end_lat;
            this.end_lng = end_lng;
        }

        @Override
        public String toString() {
            return "Booking{" + "user_id=" + user_id + ", car_id=" + car_id + ", start_date=" + start_date + ", end_date=" + end_date + ", start_time=" + start_time + ", end_time=" + end_time + ", cost=" + cost + ", start_lat=" + start_lat + ", start_lng=" + start_lng + ", end_lat=" + end_lat + ", end_lng=" + end_lng + '}';
        }
        
        public static void updateBookingList() {
        bookingList = DB.fetchBookings();
    }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCar_id() {
            return car_id;
        }

        public void setCar_id(int car_id) {
            this.car_id = car_id;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
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