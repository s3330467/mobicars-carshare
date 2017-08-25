import org.sql2o.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {
    public static void main(String[] args) {
        secure("src/main/resources/public/keystore.jks", "mobicars", null, null);
        
        /*User tempUser1 = new User("01","John Smith", "JS@somewhere.com");
        User tempUser2 = new User("02","Bob Smith", "BS@somewhere.com");
        User tempUser3 = new User("03","Greg Smith", "GS@somewhere.com");*/
        Car.updateCarList();
        Booking.updateBookingList();
        User.updateUserList();
        new UserController(new UserService());
        new CarController(new CarService());
        new BookingController(new BookingService());
        
    }
}