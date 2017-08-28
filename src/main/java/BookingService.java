import org.sql2o.*;
import java.util.*;
import java.text.*;

public class BookingService {
    
    private static final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat time = new SimpleDateFormat("HH:mm:ss");
    
    // returns a single booking by id by looping through the bookingList array
    public static Booking getBooking(String booking_id) {

        int i;
        Booking.updateBookingList();
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(booking_id)) {
                return Booking.bookingList.get(i);
            }
        }
        return null;
    }
    
//    returns a current, unfinished booking of a user. if the user has a booking with no end date
//    this would mean they had started a booking but not finished it.
//    Alex, can you flesh this explanation out? Not understanding the .size and .get parts
    public static Booking getCurrentBookingByUser_id(String user_id) {
        Booking.updateBookingList();
        if(DB.fetchCurrentBookingByUser_id(user_id).size() == 1 ){
            return DB.fetchCurrentBookingByUser_id(user_id).get(0);
        }
        return null;
    }
    
//    when user cancels booking, the ending date and time values are inserted 
//    into the bookings table entry and car availability is set to true.
    public static boolean cancelBooking(String booking_id) {
        Date current_date = new Date();
        Booking.updateBookingList();
        Booking booking = getBooking(booking_id);
        Car car = CarService.getCarById(booking.getCar_id());
        String end_date = date.format(current_date);
        String end_time = time.format(current_date);
        if(DB.cancelBooking(booking_id, end_date, end_time)) {
            DB.updateCarAvailable(car.getPlate_no(), true);
            Car.updateCarList();
            Booking.updateBookingList();
            return true;
        }
        return false;
    }
    
//    when user confirms booking and car availability is true, the car
//    availability is set to false, and the insertBooking
//    method from DB.java is called and an entry is 
//    inserted into the database bookings table with the values of user id,
//    car id, start date and time and starting location
    public static boolean createBooking(Car car, User user) {
        int user_id = Integer.parseInt(user.getId());
        int car_id = Integer.parseInt(car.getId());
        Date current_date = new Date();
        String start_date = date.format(current_date);
        String start_time = time.format(current_date);
        double start_lat = car.getLat();
        double start_lng = car.getLng();
        if(car.isAvailable());
        if(DB.insertBooking(user_id, car_id, start_date, start_time, start_lat, start_lng)) {
            DB.updateCarAvailable(car.getPlate_no(), false);
            Car.updateCarList();
            Booking.updateBookingList();
            return true;
        }
        return false;
    }
    
//    when user collects a car they have booked, the array bookingList is looped
//    to locate their booking and the collectCar method from DB.java
//    is called, which inserts the collection date and time values into the bookings
//    table on the database
    public static boolean collectCar(String booking_id) {
        Date current_date = new Date();
        String end_time = time.format(current_date);
        String collection_date = date.format(current_date);
        String collection_time = time.format(current_date);
        int i;
        Booking.updateBookingList();
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(booking_id)) {
                DB.collectCar(booking_id, collection_date, collection_time);
                Car.updateCarList();
                Booking.updateBookingList();
                return true;
            }
        }
        return false;
    }
    
//    whne user returns their booked car, the array bookingList is looped
//    to locate their booking and the returnCar method from DB.java is called,
//    which inserts the end date and time and end location values in the bookings
//    table on the database. The car availability is set to true.
    public static boolean returnCar(String booking_id) {
        Date current_date = new Date();
        String end_date = date.format(current_date);
        String end_time = time.format(current_date);
        Booking.updateBookingList();
        Booking booking = getBooking(booking_id);
        Car car = CarService.getCarById(booking.getCar_id());
        double end_lat = car.getLat();
        double end_lng = car.getLng();
        int i;
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(booking_id)) {
                DB.returnCar(booking_id, end_date, end_time, end_lat, end_lng);
                DB.updateCarAvailable(car.getPlate_no(), true);
                Car.updateCarList();
                Booking.updateBookingList();
                return true;
            }
        }
        return false;
    }
    
//    displays all bookings made by a user according to their id, by calling
//    the fetchAllBookingsByUser_id method from DB.java
    public static boolean displayAllBookingsByUser_id(String user_id) {
        int i;
        Booking.updateBookingList();
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(user_id)) {
                DB.fetchAllBookingsByUser_id(user_id);
            }
        }
        return false;
    }
}
