import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class UserController {
    public UserController(final UserService userService) {

        staticFileLocation("/public");
        String userEmail = "no user";
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            request.session().attribute("session_email", "test@email.com");
            String currentUserEmail = request.session().attribute("session_email");
            User.updateUserList();
            Car.updateCarList();
            model.put("carList", Car.carList);
            model.put("user", UserService.getUserByEmail(currentUserEmail));
            model.put("template", "templates/map.vtl" );
            return new ModelAndView(model, "templates/layout_main.vtl");
        }, new VelocityTemplateEngine());

        get("/register", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/register.vtl");
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

        post("/process_register", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            if(UserService.createUser(email, password)) {
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
            if(UserService.validateUser(email, password)) {
                request.session().attribute("session_email", email);
                response.redirect("/");
                return null;
            }
            System.out.println("user validation failed");
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