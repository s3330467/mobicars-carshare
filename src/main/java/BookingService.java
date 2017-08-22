import org.sql2o.*;
import java.util.*;
import java.text.*;

public class BookingService {
    
    private static final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat time = new SimpleDateFormat("HH:mm:ss");
    
    // returns a single booking by id
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
    
    //this function returns a booking if the user has a booking with no end date
    //this would mean they had started a booking but not finished it.
    public static Booking getCurrentBookingByUser_id(String user_id) {
        Booking.updateBookingList();
        if(DB.fetchCurrentBookingByUser_id(user_id).size() == 1 ){
            return DB.fetchCurrentBookingByUser_id(user_id).get(0);
        }
        return null;
    }
    
    public static boolean cancelBooking(String booking_id) {
        Date current_date = new Date();
        Booking.updateBookingList();
        Booking booking = getBooking(booking_id);
        Car car = CarService.getCarById(booking.getCar_id());
        String end_date = date.format(current_date);
        String end_time = time.format(current_date);
        double end_lat = car.getLat();
        double end_lng = car.getLng();
        if(DB.updateBooking(booking_id, end_date, end_time, end_lat, end_lng)) {
            DB.updateCarAvailable(car.getPlate_no(), true);
            Car.updateCarList();
            Booking.updateBookingList();
            return true;
        }
        return false;
    }
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
    
    public static boolean collectCarInBooking(String booking_id) {
        Date current_date = new Date();
        String start_date = date.format(current_date);
        String start_time = time.format(current_date);
        int i;
        Booking.updateBookingList();
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(booking_id)) {
                DB.updateStartBooking(booking_id, start_date, start_time);
                Car.updateCarList();
                Booking.updateBookingList();
                return true;
            }
        }
        return false;
    }
    
    public static boolean returnCarInBooking(String booking_id) {
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
                DB.endBooking(booking_id, end_date, end_time, end_lat, end_lng);
                DB.updateCarAvailable(car.getPlate_no(), true);
                Car.updateCarList();
                Booking.updateBookingList();
                return true;
            }
        }
        return false;
    }
    
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
