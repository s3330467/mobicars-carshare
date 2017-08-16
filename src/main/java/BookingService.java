import org.sql2o.*;
import java.util.*;
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
}
