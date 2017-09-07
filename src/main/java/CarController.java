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
        
        after("/search_for_cars", (req, res) -> {
            res.type("application/json");
	});
        /*  07-09-17 Edited By Alexander Young 
            added routes that return a json list of makes models or types
        */
        get("/get_all_types", (request, response) -> {
            Car.updateCarList();
            Set<String> results = new HashSet<String>();
            for(int i = 0; i < Car.carList.size(); i++) {
                results.add(Car.carList.get(i).getType());
            }
            System.out.print(results);
            return results;
        },JsonUtil.json());
        
        after("/get_all_types", (req, res) -> {
            res.type("application/json");
	});
        
        get("/get_all_makes", (request, response) -> {
            Car.updateCarList();
            Set<String> results = new HashSet<String>();
            for(int i = 0; i < Car.carList.size(); i++) {
                results.add(Car.carList.get(i).getMake());
            }
            System.out.print(results);
            return results;
        },JsonUtil.json());
        
        after("/get_all_makes", (req, res) -> {
            res.type("application/json");
	});
        
        get("/get_models_by_make", (request, response) -> {
            Car.updateCarList();
            String make = request.queryParams("make");
            Set<String> results = new HashSet<String>();
            for(int i = 0; i < Car.carList.size(); i++) {
                if(make.equals(Car.carList.get(i).getMake())) {
                    results.add(Car.carList.get(i).getModel());
                }
            }
            System.out.print(results);
            return results;
        },JsonUtil.json());
        
        after("/get_models_by_make", (req, res) -> {
            res.type("application/json");
	});
    }
}
