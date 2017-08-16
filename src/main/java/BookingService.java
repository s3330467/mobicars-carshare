import org.sql2o.*;
import java.util.*;
<<<<<<< HEAD

public class BookingService {
    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";
    
    // returns a single booking by id
    public static Booking getBooking(String booking_id) {

        int i;
        Booking.updateBookingList();
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getBooking_id().equals(booking_id)) {
                return Booking.bookingList.get(i);
            }
        }
        return null;
    }
    
    /*public static Booking getBookingByUserId(String user_id) {

        int i;
        Booking.updateUserList();
        for(i = 0; i <Booking.bookingist.size(); i++) {
            if(Booking.bookingList.get(i).getEmail().equals(email)) {
                System.out.print("checking email: " + User.userList.get(i).getEmail());
                return User.userList.get(i);
            }
        }
        return null;
    }*/
=======
import java.text.*;

public class BookingService {
    private static final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat time = new SimpleDateFormat("HH:mm:ss");
    
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
            car.setAvailable(false);
            return true;
        }
            return false;
    }
>>>>>>> sprint2-alex
}
