import org.sql2o.*;
import java.util.*;

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
}
