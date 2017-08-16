import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class BookingController {
    public BookingController(final BookingService bookingService) {

        get("/bookings", (request, response) -> {
            Booking.updateBookingList();
            return Booking.bookingList;
        });
        
        post("/process_book_car", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String plate_no = request.queryParams("plate_no");
            Car.updateCarList();
            User.updateUserList();
            System.out.println("booking plate no: " + plate_no);
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            model.put("car", car);
            model.put("user", user);
            model.put("template", "templates/confirm_booking.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());
        
        post("/process_confirm_booking", (request, response) -> {
            String plate_no = request.queryParams("plate_no");
            Car.updateCarList();
            User.updateUserList();
            System.out.println("confirm booking plate no: " + plate_no);
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            if(BookingService.createBooking(car, user)) {
                return "temporary booking success page";
            }
            else
            {
                return "temporary booking failure page";
            }
            });
    }
}
