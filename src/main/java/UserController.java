import java.util.*;
import java.lang.Number;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class UserController {
    public UserController(final UserService userService) {
        staticFileLocation("/public");
        before("/",(request, response) -> {
            //if the user attempts to access the map while session email is 
            //null the user is not logged in and is redirected to login
            if (request.session().attribute("session_email") == null) {
                response.redirect("/login");
            }
            if (request.session().attribute("session_booking") != null) {
                response.redirect("/booking_details");
            }
        });
        
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String currentUserEmail = request.session().attribute("session_email");
            User.updateUserList();
            Car.updateCarList();
            boolean bookingState;
            if(request.session().attribute("session_booking") !=null) {
                bookingState = true;
            }
            else {
                bookingState = false;
            }
            System.out.println(bookingState);
            model.put("carList", Car.carList);           
            model.put("user", UserService.getUserByEmail(currentUserEmail));
            model.put("bookingState", bookingState);
            model.put("template", "templates/map.vtl" );
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        get("/register", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/register.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/about.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        get("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/login.vtl");
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        get("/users", (request, response) -> {
            User.updateUserList();
            return User.userList;
        });
        
        post("/process_update_user_location", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            double lat = Double.parseDouble(request.queryParams("lat"));
            double lng = Double.parseDouble(request.queryParams("lng"));
            DB.updateUserLatLng(request.session().attribute("session_email"), lat, lng);
            return "";
        });
        
        
        post("/process_register", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            String address = request.queryParams("address");
            String f_name = request.queryParams("f_name");
            String l_name = request.queryParams("l_name");
            String license_no = request.queryParams("license_no");
            String phone_no = request.queryParams("phone_no");
            if(UserService.createUser(email, password, f_name, l_name, address, license_no, phone_no)) {
                response.redirect("/login");
            }
            else {
                response.redirect("/register");
            }
            return null;
        });
        
        post("/process_login", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            System.out.println(password);
            System.out.println(email);
            if(UserService.validateUser(email, password)) {
                request.session().attribute("session_email", email);
                User user = UserService.getUserByEmail(email);
                Booking existing_booking = BookingService.getCurrentBookingByUser_id(user.getId());
                if(existing_booking != null) {
                    request.session().attribute("session_booking", existing_booking.getId());
                }
                response.redirect("/");
                return null;
            }
            System.out.println("user validation failed");
            response.redirect("/login");
            return null;
        });
        
        get("/process_logout", (request, response) -> {
            request.session().removeAttribute("session_email");
            request.session().removeAttribute("session_booking");
            response.redirect("/login");
            return null;
        });

        get("/users/currentuser", (request, response) -> {
            String currentUser = request.session().attribute("session_email");
            return currentUser;

        });

        /*get("/users/:id", (request, response) -> {
            String id = request.params(":id");
            return UserService.getUser(id);

        });*/

        get("/users/:email", (request, response) -> {
            String email = request.params(":email");
            return UserService.getUserByEmail(email);

        });


        // more routes


    }
}