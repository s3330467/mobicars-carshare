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
                Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
                request.session().attribute("session_booking", booking.getId());
                response.redirect("/booking_details");
                return null;
            }
            else
            {
                return "temporary booking failure page";
            }
            });
        
        post("/process_cancel_booking", (request, response) -> {
            String plate_no = request.queryParams("plate_no");
            Car.updateCarList();
            User.updateUserList();
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
            if(BookingService.cancelBooking(booking.getId())) {
                request.session().attribute("session_booking", null);
                response.redirect("/");
                return null;
            }
            else
            {
                return "temporary booking cancel failure page";
            }
            });
        
        get("/booking_details", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Car.updateCarList();
            User.updateUserList();
            Booking.updateBookingList();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
            Car car = CarService.getCarById(booking.getCar_id());
            model.put("car", car);
            model.put("user", user);
            model.put("booking", booking);
            model.put("template", "templates/booking_details.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());
    }
}