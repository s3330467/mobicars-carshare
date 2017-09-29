
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.Gson;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

/**
 * Date: 16.8.17
 * <p>
 * Spark route controller for routes that relate to the manipulation or access
 * of booking data<p>
 *
 * Performs HTTP GET and POST requests using Spark routes.<p>
 * Gathers data from forms using HashMap and inserts them into specified
 * templates.<p>
 * Uses Velocity Template Engine to reference .vtl files which are templates
 * containing HTML.<p>
 * <b>spark routes are not recognised as methods by Javadoc, review the code to
 * see full comments</b>
 *
 * @author Alexander Young
 */
public class BookingController {

    public BookingController(final BookingService bookingService) {

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 16.8.17
         * <p>
         * GET request to return list of all bookings by calling
         * updateBookingList method from Booking.java<p>
         *
         * @return a list of all bookings in JSON format
         *
         * Updated 10.9.17 by Alexander Young<p>
         * added data field to the JSON output of this route to make it easier
         * to parse it into DataTable()
         * <p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList method<p>
         */
        get("/get_booking_history", (request, response) -> {
            Gson gson = new Gson();
            String jsonObject = gson.toJson(Booking.bookingList);
            return "{\"data\":" + jsonObject + "}";
        });

        after("/get_booking_history", (req, res) -> {
            res.type("application/json");
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 16.8.17
         * <p>
         * GET request to return list of all bookings only for the currently
         * logged in user return in JSON format<p>
         *
         * @return a list of the current users bookings in JSON format
         *
         * Updated 10.9.17 by Alexander Young<p>
         * added data field to the JSON output of this route to make it easier
         * to parse it into DataTable()
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList and updateUserList
         * methods<p>
         * <p>
         */
        get("/get_booking_history_for_current_user", (request, response) -> {
            Gson gson = new Gson();
            User currentUser = UserService.getUserByEmail(request.session().attribute("session_email"));
            System.out.println(currentUser.getId());
            List<Booking> usersBookings = BookingService.getAllBookingsByUser_id(currentUser.getId());
            String jsonObject = gson.toJson(usersBookings);
            return "{\"data\":" + jsonObject + "}";
        });

        after("/get_booking_history_for_current_user", (req, res) -> {
            res.type("application/json");
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 16.8.17
         * <p>
         * POST request<p>
         * gets user by session. Fetches plate number of the user's selected car
         * and inserts both objects into confirm_booking.vtl, which is then
         * inserted into layout_main.vtl.<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList and updateCarList
         * methods<p>
         * @return booking confirmation html page displayed to browser
         */
        post("/process_book_car", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String plate_no = request.queryParams("plate_no");
            System.out.println("booking plate no: " + plate_no);
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            model.put("car", car);
            model.put("user", user);
            model.put("template", "templates/confirm_booking.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 16.8.17
         * <p>
         * POST request<p>
         * actually creates the booking in the SQL DB and redirects the user to
         * /booking_made upon success<p>
         *
         * Updated 30.8.17 by Alexander Young<p>
         * added a time check for bookings, bookings will expire 15 minutes
         * (900000 milliseconds) after a booking is created<p>
         *
         * Updated 05.9.17 by Alexander Young<p>
         * added a check to only cancel bookings that haven't been collected
         * when the 15 minutes expire<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList, updateUserList and
         * updateCarList methods<p>
         */
        post("/process_confirm_booking", (request, response) -> {
            String plate_no = request.queryParams("plate_no");
            System.out.println("confirm booking plate no: " + plate_no);
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            String expectedDateTime = request.queryParams("datetime");
            System.out.println("Booking Controller query params date time: " + expectedDateTime);
            if (BookingService.createBooking(car, user, expectedDateTime)) {
                Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
                request.session().attribute("session_booking", booking.getId());
                Date current_date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date booking_date = df.parse(booking.getStart_date() + " " + booking.getStart_time());
                Date expDateTime = df.parse(booking.getExp_date() + " " + booking.getExp_time());
                System.out.println(" Booking Controller Expected date: " + booking.getExp_date() + "Expected time: " + booking.getExp_time());
                Date expireTime = new Date(booking_date.getTime() + 900000);
                System.out.println("\nexpire time: " + expireTime);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        System.out.println("\n\n\n\nattempting to cancel booking");
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
        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 16.8.17
         * <p>
         * GET request<p>
         * @return booking details html page is displayed to the user
         *
         * Updated 07.9.17 by Rachel Tan<p>
         * renamed the route from /booking_details to /booking_made to make its
         * function more immediately apparent<p>
         *
         * Updated 30.8.17 by Alexander Young<p>
         * added time related checks, puts into velocity templates the number of
         * seconds until the booking should expire. in the booking_made.vtl this
         * value is used to display an accurate javascript countdown timer<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList, updateUserList and
         * updateCarList methods<p>
         */
        get("/booking_made", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId());
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

        /**
         * Author: <b>Rachel Tan</b><p>
         * Date: 22.8.17
         * <p>
         * POST request<p>
         * gets the user's session, calls the collectCar() method from
         * BookingService and redirects to /booking_in_progress<p>
         *
         * Updated 30.8.17 by Rachel Tan<p>
         * Changed the template put from booked_car_info.vtl to
         * booking_in_progress.vtl<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList, updateUserList and
         * updateCarList methods<p>
         */
        post("/process_collect_car", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
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

        /**
         * Author: <b>Rachel Tan</b><p>
         * Date: 6.9.17
         * <p>
         * GET request<p>
         * Gets booking details including collection_date from user's session
         * and puts them on the template booking_in_progress.vtl<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList, updateUserList and
         * updateCarList methods<p>
         */
        get("/booking_in_progress", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
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

         /** 
         * Author: <b>Rachel Tan</b><p> 
         * Date: 28.9.17 
         * <p> 
         * Gets the date and time from the DateTimePicker script in /booking_in_progress 
         * and calls extendBooking(), then redirects back to the booking in 
         * progress page.<p> 
         */ 
        post("/process_extend_booking", (request, response) -> { 
            String expectedDateTime = request.queryParams("datetime"); 
            System.out.println("Booking Controller query params date time: " + expectedDateTime); 
            User user = UserService.getUserByEmail(request.session().attribute("session_email")); 
            Booking booking = BookingService.getCurrentBookingByUser_id(user.getId()); 
             
            if (BookingService.extendBooking(booking, expectedDateTime)) { 
                response.redirect("/booking_in_progress"); 
            } 
            return null; 
        }); 
         
        /** 
        
        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 25.8.17
         * <p>
         * POST request<p>
         * calls the service method that handles returning a car and redirects
         * the user to a HTML page that summarises their booking<p>
         *
         * Updated 30.8.17 by Rachel Tan<p>
         * Added redirect to returned_car.vtl instead of back to home page.<p>
         *
         * Updated 30.8.17 by Rachel Tan<p>
         * Fixed post not working by rearranging order of code, added car put to
         * be able to call car variables in redirected page.<p>
         *
         * Updated 6.9.17 by Rachel Tan<p>
         * Removed put template, added redirect to GET method. This is to ensure
         * the POST process is completed before displaying booking details on
         * redirect.<p>
         *
         * Updated 13.9.17 by Alexander Young<p>
         * removed unused vtl code as this route now only redirects and never
         * serves vtl<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList method<p>
         */
        post("/process_return_car", (request, response) -> {
            if (BookingService.returnCar(request.session().attribute("session_booking"))) {
                request.session().attribute("session_booking", null);
                response.redirect("/booking_summary");
            }
            return null;
        });

        /**
         * Author: <b>Rachel Tan</b><p>
         * Date: 6.9.17
         * <p>
         * GET request<p>
         * Gets booking details by user's session and puts them on the template
         * booking_summary.vtl.<p>
         *
         * Updated 7.9.17 by Rachel Tan<p>
         * Changed booking to call getLastCompleteBookingOfUser() instead of
         * getCurrentBookingOfUser(), because after POST /process_return_car,
         * there is no current booking.<p>
         * 
         * Updated 19.9.17 by Rachel Tan<p>
         * setHours method to be able to allow page to display cost.
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList, updateUserList and
         * updateCarList methods<p>
         * 
         * Updated 21.9.17 by Rachel Tan<p>
         * Calls method to insert cost of booking in database<p>
         */
        get("/booking_summary", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getLastCompleteBookingOfUser(user.getId());
            BookingService.insertTotalCostOfBookingById(booking.getId());
            booking.setHours(BookingService.calculateBookingHours(booking));
            Car car = CarService.getCarById(booking.getCar_id());

            model.put("car", car);
            model.put("booking", booking);
            model.put("template", "templates/booking_summary.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 17.8.17
         * <p>
         * POST request<p>
         * calls the method that cancels a booking in progress in
         * BookingService()
         * <p>
         */
        post("/process_cancel_booking", (request, response) -> {
            if (BookingService.cancelBooking(request.session().attribute("session_booking"))) {
                request.session().attribute("session_booking", null);
                response.redirect("/");
                return null;
            }
            return null;
        });
        
        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 29.9.17
         * <p>
         * POST request<p>
         * returns the time elapsed for the users current booking
         * <p>
         */
        post("/process_get_time_elapsed_since_collection", (request, response) -> {
            User currentUser = UserService.getUserByEmail(request.session().attribute("session_email"));
            Booking booking = BookingService.getCurrentBookingByUser_id(currentUser.getId());
            Date current_date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date collection_date = df.parse(booking.getCollection_date() + " " + booking.getCollection_time());
            return (current_date.getTime() - collection_date.getTime()) / 1000;
        });
    }
}
