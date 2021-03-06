
import java.util.*;
import java.lang.Number;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 * Date: 7.8.17
 * <p>
 * Spark route controller for routes that relate to the manipulation or access
 * of user data<p>
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
public class UserController {

    public UserController(final UserService userService) {
        staticFileLocation("/public");

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * Before route<p>
         * before executing the / route check if the user is logged in, and if
         * they are not redirect them to the login page<p>
         * if the user has a booking in progress redirect them to their
         * booking_details<p>
         *
         * Updated 13.8.17 by Alexander Young<p>
         * removed /home route, as / is now used in place of /home<p>
         *
         * Updated 17.8.17 by Alexander Young<p>
         * no longer redirects to /register if the user isn't logged in<p>
         * 
         * Updated 29.9.17 by Rachel Tan<p>
         * When a user reopens the web page while a booking is active, they are
         * redirected to /booking_in_progress instead of the old
         * /booking_details<p>
         */
        before("/", (request, response) -> {
            if (request.session().attribute("session_email") == null) {
                response.redirect("/login");
            }
            if(request.session().attribute("booking_status") == null){
                return;
            }
            if (request.session().attribute("booking_status").equals("uncollected")) {
                response.redirect("/booking_made");
            }
            if (request.session().attribute("booking_status").equals("collected")) {
                response.redirect("/booking_in_progress");
            }
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * GET request<p>
         * displays the root page of the web app, the map page, but the user
         * should be redirected away if they are not logged in when they attempt
         * to access this page<p>
         * If the user has a booking in progress and attempts to navigate to the
         * map it should redirect them to their booking in progress instead<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList and updateCarList
         * methods<p>
         *
         * @return map html page displayed to user
         */
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String currentUserEmail = request.session().attribute("session_email");
            boolean bookingState;
            if (request.session().attribute("booking_status") != null) {
                bookingState = true;
            } else {
                bookingState = false;
            }
            model.put("carList", Car.carList);
            model.put("user", UserService.getUserByEmail(currentUserEmail));
            model.put("bookingState", bookingState);
            model.put("template", "templates/map.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * GET request<p>
         * @return register page is displayed to user
         */
        get("/register", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/register.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 18.8.17
         * <p>
         * GET request<p>
         * @return about page is displayed to user
         */
        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/about.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * GET request<p>
         * @return login page is displayed to user
         */
        get("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/login.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 7.8.17
         * <p>
         * GET request to return list of all users by calling updateUserList
         * method from User.java<p>
         * <p>
         * <b>REMOVE BEFORE RELEASE, VERY USEFUL FOR TESTING BUT EXPOSES
         * DATABASE CONTENTS TO THE OUTSIDE WORLD</b>
         *
         * @return a list of all users
         * <p>
         */
//        get("/users", (request, response) -> {
//            return User.userList;
//        });

        /**
         * Author: <b>Vishal Pradhan</b><p>
         * Date: 31.08.2017
         * <p>
         * GET request<p>
         * @return booking history page is displayed to user
         */
      
        get("/booking_history", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/booking_history.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 13.8.17
         * <p>
         * POST request<p>
         * updates the currently logged in users location to the latitude and
         * longitude query parameters given
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateBookingList and updateCarList methods,
         * method now updates java objects directly instead of just in the DB<p>
         * <p>
         */
        post("/process_update_user_location", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            double lat = Double.parseDouble(request.queryParams("lat"));
            double lng = Double.parseDouble(request.queryParams("lng"));
            DB.updateUserLatLng(user.getEmail(), lat, lng);
            user.setLat(lat);
            user.setLng(lng);
            return "";
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * POST request<p>
         * registers the user by adding them to the database based on the
         * registration details provided by the POST request
         * <p>
         *
         * Updated 11.8.17 by Alexander Young<p>
         * redirects to /login if registration succeeds, or to /register if it
         * fails<p>
         *
         * Updated 13.8.17 by Alexander Young<p>
         * added new fields to keep in sync with changes to the SQL database and
         * User class<p>
         * 
         * Updated 10.10.17 by Alexander Young<p>
         * added check to prevent duplicate user email's being added to the database<p>
         */
        post("/process_register", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String email = request.queryParams("email").toLowerCase();
            String password = request.queryParams("password1");
            String address = request.queryParams("address");
            String f_name = request.queryParams("f_name");
            String l_name = request.queryParams("l_name");
            String license_no = request.queryParams("license_no");
            String phone_no = request.queryParams("phone_no");
            String card_name = request.queryParams("card_name");
            String card_no = request.queryParams("card_no");
            String expiry_month = request.queryParams("expiry_month");
            String expiry_year = request.queryParams("expiry_year");
            String cvv = request.queryParams("cvv");
            if(UserService.getUserByEmail(email) != null){
                return null;
            }
            if (UserService.createUser(email, password, f_name, l_name, address, license_no, phone_no, card_name, card_no, expiry_month, expiry_year, cvv)) {
                response.redirect("/login");
            } else {
                response.redirect("/register");
            }
            return null;
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * POST request<p>
         * logs the user in if the provided query parameters for email and
         * password have matches in the user database
         *
         * Updated 11.8.17 by Alexander Young<p>
         * redirects back to /login if login fails
         * <p>
         */
        post("/process_login", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String email = request.queryParams("email").toLowerCase();
            String password = request.queryParams("password");
            if (UserService.validateUser(email, password)) {
                request.session().attribute("session_email", email);
                User user = UserService.getUserByEmail(email);
                Booking existing_booking = BookingService.getCurrentBookingByUser_id(user.getId());
                if (existing_booking != null) {
                    if(existing_booking.getCollection_date() == null){
                        request.session().attribute("booking_status", "uncollected");
                    }
                    else{
                        request.session().attribute("booking_status", "collected");
                    }
                }
                response.redirect("/");
                return null;
            }
            response.redirect("/login");
            return null;
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 13.8.17
         * <p>
         * GET request<p>
         * logs the user out
         * <p>
         */
        get("/process_logout", (request, response) -> {
            request.session().removeAttribute("session_email");
            request.session().removeAttribute("booking_status");
            response.redirect("/login");
            return null;
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * GET request<p>
         * displays the currently logged in user
         * <p>
         * <b>REMOVE BEFORE RELEASE, VERY USEFUL FOR TESTING BUT EXPOSES
         * DATABASE CONTENTS TO THE OUTSIDE WORLD</b>
         */
        get("/users/currentuser", (request, response) -> {
            String currentUser = request.session().attribute("session_email");
            return currentUser;

        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 8.8.17
         * <p>
         * GET request<p>
         * displays user details based on the email entered in the url
         * <p>
         * <b>REMOVE BEFORE RELEASE, VERY USEFUL FOR TESTING BUT EXPOSES
         * DATABASE CONTENTS TO THE OUTSIDE WORLD</b>
         */
        get("/users/:email", (request, response) -> {
            String email = request.params(":email");
            return UserService.getUserByEmail(email);

        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 9.5.17
         * <p>
         * GET request<p>
         * returns true is a user session exists and false if it does not, use
         * this to determine if a user is logged in or not
         */
        get("/user_login_status", (request, response) -> {
            if (request.session().attribute("session_email") != null) {
                return true;
            } else {
                return false;
            }
        });
    }
}
