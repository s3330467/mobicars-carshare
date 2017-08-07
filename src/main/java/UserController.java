import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import static spark.Spark.*;

public class UserController {
    public UserController(final UserService userService) {

        staticFileLocation("/public");

        get("/users", (request, response) -> {
            return UserService.getAllUsers();
        });

        /*get("/users/:id", (request, response) -> {
            String id = request.params(":id");
            return UserService.getUser(id);

        });*/

        get("/users/:username", (request, response) -> {
            String username = request.params(":username");
            return UserService.getUserByUsername(username);

        });
        get("/favourite_photos", (request, response) ->
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<title>Hello Friend!</title>" +
                        "<link rel='stylesheet'  href='https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>" +
                        "</head>" +
                        "<body>" +
                        "<h1>Favorite Photos</h1>" +
                        "<ul>" +
                        "<li><img src='/images/star_citizen_vanguard_bulldog-HD.jpg' alt='A photo of a space ships.'/></li>" +
                        "</ul>" +
                        "</body>" +
                        "</html>"
        );


        // more routes


    }
}