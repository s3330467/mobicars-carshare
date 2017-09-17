
import org.sql2o.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

/**
 * Date: 7.8.17
 * <p>
 * Main java class that instantiates the various spark route controllers<p>
 *
 * Updated 24.8.17 by Rachel Tan<p>
 * Runs the program using SSL with the specified keystore location<p>
 * 
 * @author Alexander Young
 */
public class Main {

    public static void main(String[] args) {
        secure("src/main/resources/public/keystore.jks", "mobicars", null, null);

        Car.updateCarList();
        Booking.updateBookingList();
        User.updateUserList();
        new UserController(new UserService());
        new CarController(new CarService());
        new BookingController(new BookingService());

    }
}
