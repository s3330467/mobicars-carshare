import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class UserController {
    public UserController(final UserService userService) {

        staticFileLocation("/public");

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
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
            return User.getUserList();
        });

        post("/process_register", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            response.redirect("/login");
            return UserService.createUser(email, password);
        });

        post("/process_login", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            if(UserService.validateUser(email, password)== null) {
                response.redirect("/login");
                return null;
            }
            request.session().attribute("session_email", email);
            response.redirect("/");
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