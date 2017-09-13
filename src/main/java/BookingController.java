
/**
 * @author Alexander Young
 * Date: 16-08-17
 * Class: BookingController
 * Description: Performs HTTP GET and POST requests using Spark routes.
 *              Gathers data from forms using HashMap and inserts them into
 *              specified templates.
 *              Uses Velocity Template Engine to reference .vtl files which
 *              are templates containing HTML.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.Gson;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class BookingController {

    public BookingController(final BookingService bookingService) {

        /*     
        @author Alexander Young
        Date: 16-08-17
        GET request to return list of all bookings by calling updateBookingList
        method from Booking.java
        returns in JSON format
        
        updated 10-9-17 Alexander Young
        added data field to the JSON output of this route to make it easier to 
        parse it into DataTable()
         */
        get("/get_booking_history", (request, response) -> {
            Gson gson = new Gson();
            Booking.updateBookingList();
            String jsonObject = gson.toJson(Booking.bookingList);
            return "{\"data\":" + jsonObject + "}";
        });

        after("/get_booking_history", (req, res) -> {
            res.type("application/json");
        });

        /*     
        @author Alexander Young
        Date: 16-08-17
        GET request to return list of all bookings only for the currently logged in user
        return in JSON format
         */
        get("/get_booking_history_for_current_user", (request, response) -> {
            Gson gson = new Gson();
            Booking.updateBookingList();
            User.updateUserList();
            User currentUser = UserService.getUserByEmail(request.session().attribute("session_email"));
            System.out.println(currentUser.getId());
            List<Booking> usersBookings = BookingService.getAllBookingsByUser_id(currentUser.getId());
            Booking.updateBookingList();
            String jsonObject = gson.toJson(usersBookings);
            return "{\"data\":" + jsonObject + "}";
        });

        after("/get_booking_history_for_current_user", (req, res) -> {
            res.type("application/json");
        });

        /*     
        @author Alexander Young
        Date: 16-08-17
        POST request  
        gets user by session. Fetches plate number of the user's selected car
        and inserts both objects into confirm_booking.vtl, which is then
        inserted into layout_main.vtl.
         */
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

        /*     
        @author Alexander Young
        Date: 16-08-17
        POST request  
        actually creates the booking in the SQL DB and returns booking details.vtl to the user
         */
        post("/process_confirm_booking", (request, response) -> {
            String plate_no = request.queryParams("plate_no");
            Car.updateCarList();
            User.updateUserList();
            Booking.updateBookingList();
            System.out.println("confirm booking plate no: " + plate_no);
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            if (BookingService.createBooking(car, user)) {
                Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
                request.session().attribute("session_booking", booking.getId());

                /*
                  30-08-17 edited by Alexander Young
                    added a time check for bookings, bookings will expire 15 minutes
                    (900000 milliseconds) after a booking is created
                
                  05-09-17 edited by Alexander Young
                    added a check to only cancel bookings that haven't been
                    collected when the 15 minutes expire
                 */
                Date current_date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date booking_date = df.parse(booking.getStart_date() + " " + booking.getStart_time());
                Date expireTime = new Date(booking_date.getTime() + 900000);
                System.out.println("\nexpire time: " + expireTime);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        System.out.println("\n\n\n\nattempting to cancel booking");
                        Booking.updateBookingList();
                        Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
                        if (booking.getCollection_date() == null) {
                            if (BookingService.cancelBooking(request.session().attribute("session_booking"))) {
                                System.out.println("and it was cancelled.");
                                request.session().attribute("session_booking", null);
                                response.redirect("/");
                            } else {
                                System.out.println("but something unexpected happened.");
                            }
                        } else {
                            System.out.println("but it was collected so no need.");
                        }

                    }
                }, expireTime);
                response.redirect("/booking_made");
                return null;
            } else {
                return "temporary booking failure page";
            }
        });

        
        /*     
        @author Alexander Young
        Date: 17-08-17
        POST request  
        returns a page to the user providing booking and car information by vtl import
        
        Edited 07-09-17 Rachel Tan
        renamed the route from /booking_details to /booking_made to make its function 
        more immediately apparent
         */
        get("/booking_made", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Car.updateCarList();
            User.updateUserList();
            Booking.updateBookingList();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());

            /*30-8-17 edited by Alexander Young
            added time related checks, puts into velocity templates the number 
            of seconds until the booking should expire. on the booking_made.vtl 
            this value is used to display an accurate javascript countdown timer*/
            Date current_date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date booking_date = df.parse(booking.getStart_date() + " " + booking.getStart_time());
            long timeSinceBooking = (current_date.getTime() - booking_date.getTime()) / 1000;
            System.out.println(timeSinceBooking);

            Car car = CarService.getCarById(booking.getCar_id());
            model.put("timeSinceBooking", timeSinceBooking);
            model.put("car", car);
            model.put("user", user);
            model.put("booking", booking);
            model.put("template", "templates/booking_made.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /* @author: Rachel
        Date: 22.8.17
        Edited: 30.8.17 by Rachel: Changed the template put from booked_car_info.vtl
        to booking_in_progress.vtl
        POST request that gets the user's session, calls the collectCar()
        method from BookingService and redirects to /booking_in_progress
         */
        post("/process_collect_car", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Car.updateCarList();
            User.updateUserList();
            Booking.updateBookingList();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
            Car car = CarService.getCarById(booking.getCar_id());
            if (BookingService.collectCar(request.session().attribute("session_booking"))) {
                model.put("car", car);
                model.put("booking", booking);
                response.redirect("/booking_in_progress");
            }
            return null;
        });

        /* @author: Rachel
        Date: 6.9.17
        Gets booking details including collection_date from user's session and 
        puts them on the template booking_in_progress.vtl.
         */
        get("/booking_in_progress", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Car.updateCarList();
            User.updateUserList();
            Booking.updateBookingList();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
            Car car = CarService.getCarById(booking.getCar_id());

            Date current_date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date collection_date = df.parse(booking.getCollection_date() + " " + booking.getCollection_time());
            long timeSinceCollection = (current_date.getTime() - collection_date.getTime()) / 1000;
            System.out.println(timeSinceCollection);
            model.put("car", car);
            model.put("booking", booking);
            model.put("timeSinceCollection", timeSinceCollection);
            model.put("template", "templates/booking_in_progress.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /*
        Edited 30.8.17 by Rachel: Added redirect to returned_car.vtl instead of
        back to home page.
        Edited 30.8.17 by Rachel: Fixed post not working by rearranging order
        of code, added car put to be able to call car variables in redirected
        page.
        Edited 6.9.17 by Rachel: Removed put template, added redirect to GET method.
        This is to ensure the POST process is completed before displaying booking
        details on redirect.
        
        Edited 13-9-17 by Alexander Young
        removed unused vtl code as this route now only redirects and never serves vtl
         */
        post("/process_return_car", (request, response) -> {
            Booking.updateBookingList();
            if (BookingService.returnCar(request.session().attribute("session_booking"))) {
                request.session().attribute("session_booking", null);
                response.redirect("/booking_summary");
            }
            return null;
        });

        /*      
        @author: Rachel
        Date: 6.9.17
        Gets booking details by user's session and puts them on the template
        booking_summary.vtl.
        Edited: 7.9.17 by Rachel: Changed booking to call getLastCompleteBookingOfUser()
        instead of getCurrentBookingOfUser(), because after POST /process_return_car,
        there is no current booking.
         */
        get("/booking_summary", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Car.updateCarList();
            User.updateUserList();
            Booking.updateBookingList();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getLastCompleteBookingOfUser(user.getId());
            Car car = CarService.getCarById(booking.getCar_id());

            model.put("car", car);
            model.put("booking", booking);
            model.put("template", "templates/booking_summary.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /*     
        @author Alexander Young
        Date: 17-08-17
        POST request  
        calls the method that cancels a booking in progress in BookingService()
         */
        post("/process_cancel_booking", (request, response) -> {
            if (BookingService.cancelBooking(request.session().attribute("session_booking"))) {
                request.session().attribute("session_booking", null);
                response.redirect("/");
                return null;
            }
            return null;
        });
    }

}
