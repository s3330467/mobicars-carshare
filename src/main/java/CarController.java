import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class CarController {
    public CarController(final CarService carService) {

        get("/cars", (request, response) -> {
            Car.updateCarList();
            return Car.carList;
        },JsonUtil.json());
        
        after("/cars", (req, res) -> {
            res.type("application/json");
	});
                
        post("/process_get_car_details", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String plate_no = request.queryParams("plate_no");
            Car.updateCarList();
            User.updateUserList();
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            model.put("car", car);
            model.put("user", user);
            return new ModelAndView(model, "templates/details.vtl");
        }, new VelocityTemplateEngine());
        
        /*  05-09-17 Edited By Alexander Young 
            added routes that return search results based on the parameters they receieve
        */
        post("/search_for_cars", (request, response) -> {
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            String type = request.queryParams("type");
            String make = request.queryParams("make");
            String model = request.queryParams("model");
            System.out.println(type);
            System.out.println(make);
            System.out.println(model);
            Car.updateCarList();
            List<Car> searchResults = CarService.carSearch(type, make, model);
            return searchResults;
        },JsonUtil.json());
    }
}
