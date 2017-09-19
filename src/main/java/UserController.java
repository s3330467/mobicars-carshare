
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
         */
        before("/", (request, response) -> {
            if (request.session().attribute("session_email") == null) {
                response.redirect("/login");
            }
            if (request.session().attribute("session_booking") != null) {
                response.redirect("/booking_details");
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
         * @return map html page displayed to user
         */
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String currentUserEmail = request.session().attribute("session_email");
            User.updateUserList();
            //Car.updateCarList();
            boolean bookingState;
            if (request.session().attribute("session_booking") != null) {
                bookingState = true;
            } else {
                bookingState = false;
            }
            System.out.println("users booking state: " + bookingState);
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
        get("/users", (request, response) -> {
            User.updateUserList();
            return User.userList;
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 16.8.17
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
         * <p>
         */
        post("/process_update_user_location", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            double lat = Double.parseDouble(request.queryParams("lat"));
            double lng = Double.parseDouble(request.queryParams("lng"));
            DB.updateUserLatLng(request.session().attribute("session_email"), lat, lng);
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
            System.out.println("logging in with password: " + password);
            System.out.println("logging in with email: " + email);
            if (UserService.validateUser(email, password)) {
                request.session().attribute("session_email", email);
                User user = UserService.getUserByEmail(email);
                Booking existing_booking = BookingService.getCurrentBookingByUser_id(user.getId());
                if (existing_booking != null) {
                    request.session().attribute("session_booking", existing_booking.getId());
                }
                response.redirect("/");
                return null;
            }
            System.out.println("user login validation failed");
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
            request.session().removeAttribute("session_booking");
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
