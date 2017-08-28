/**
 *
 * @author Alexander
 * Date: 
 * Class: BookingController
 * Description: Performs HTTP GET and POST requests using Spark routes.
 *              Gathers data from forms using HashMap and inserts them into 
 *              specified templates.
 *              Uses Velocity Template Engine to reference .vtl files which
 *              are templates containing HTML.
 */

import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class BookingController {
    public BookingController(final BookingService bookingService) {

//      GET request to return list of all bookings by calling updateBookingList
//      method from Booking.java
        get("/bookings", (request, response) -> {
            Booking.updateBookingList();
            return Booking.bookingList;
        });
        
//      POST request 
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
        
        post("/process_collect_car", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Car.updateCarList();
            User.updateUserList();
            Booking.updateBookingList();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
            Car car = CarService.getCarById(booking.getCar_id());
            if(BookingService.collectCar(request.session().attribute("session_booking"))) {
                model.put("car", car);
                model.put("user", user);
                model.put("booking", booking);
                model.put("template", "templates/booked_car_info.vtl");
                return new ModelAndView(model, "templates/layout_main.vtl");
            }
            return null;
        }, new VelocityTemplateEngine());
        
        post("/process_return_car", (request, response) -> {
            if(BookingService.returnCar(request.session().attribute("session_booking"))) {
            request.session().attribute("session_booking", null);
            response.redirect("/");
            return null;
            }
            return null;
        });
        
        post("/process_cancel_booking", (request, response) -> {
            if(BookingService.cancelBooking(request.session().attribute("session_booking"))) {
            request.session().attribute("session_booking", null);
            response.redirect("/");
            return null;
            }
            return null;
        });
        
        /* Can't figure this out:
        post("/process_collect_car", (request, response) -> {
            //Map<String, Object> model = new HashMap<String, Object>();
            String plate_no = request.queryParams("plate_no");
            Car.updateCarList();
            User.updateUserList();
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.collectCarInBooking(booking.getId());
            return new ModelAndView(model, "templates/car_collected.vtl");
            */
    }
        
}
