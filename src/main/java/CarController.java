
import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

/**
 * Date: 10.8.17
 * <p>
 * Spark route controller for routes that relate to the manipulation or access
 * of Car data<p>
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
public class CarController {

    public CarController(final CarService carService) {

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 10.8.17
         * <p>
         * GET request to return list of all bookings by calling updateCarList
         * method from Car.java<p>
         *
         * @return a list of all cars in JSON format
         *
         * Updated 10.9.17 by Alexander Young<p>
         * converted the return to JSON
         * <p>
         */
        get("/cars", (request, response) -> {
            //Car.updateCarList();
            return Car.carList;
        }, JsonUtil.json());

        after("/cars", (req, res) -> {
            res.type("application/json");
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 10.8.17
         * <p>
         * POST request<p>
         *
         * Updated 20.9.17 by Alexander Young<p>
         * removed reference to the updateUserList and updateCarList methods
         *
         * @return HTML for a car details popup when the user clicks on a car on
         * the map
         */
        post("/process_get_car_details", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String plate_no = request.queryParams("plate_no");
            // Car.updateCarList();
            //User.updateUserList();
            Car car = CarService.getCarByPlate_no(plate_no);
            User user = UserService.getUserByEmail(request.session().attribute("session_email"));
            model.put("car", car);
            model.put("user", user);
            return new ModelAndView(model, "templates/details.vtl");
        }, new VelocityTemplateEngine());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 5.9.17
         * <p>
         * GET request<p>
         * calls the carSearch method using the query parameters<p>
         *
         * @return JSON array of cars that match the search criteria
         */
        get("/search_for_cars", (request, response) -> {
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            String type = request.queryParams("type");
            String make = request.queryParams("make");
            String model = request.queryParams("model");
            System.out.println(type);
            System.out.println(make);
            System.out.println(model);
            //Car.updateCarList();
            List<Car> searchResults = CarService.carSearch(type, make, model);
            return searchResults;
        }, JsonUtil.json());

        after("/search_for_cars", (req, res) -> {
            res.type("application/json");
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 7.9.17
         * <p>
         * GET request<p>
         * loops through the cars in the static carList to find all the unique
         * car types<p>
         *
         * @return JSON list of all the unique car types found in carList
         */
        get("/get_all_types", (request, response) -> {
            //Car.updateCarList();
            Set<String> results = new HashSet<String>();
            for (int i = 0; i < Car.carList.size(); i++) {
                results.add(Car.carList.get(i).getType());
            }
            System.out.print(results);
            return results;
        }, JsonUtil.json());

        after("/get_all_types", (req, res) -> {
            res.type("application/json");
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 7.9.17
         * <p>
         * GET request<p>
         * loops through the cars in the static carList to find all the unique
         * car makes<p>
         *
         * @return JSON list of all the unique car makes found in carList
         */
        get("/get_all_makes", (request, response) -> {
            //Car.updateCarList();
            Set<String> results = new HashSet<String>();
            for (int i = 0; i < Car.carList.size(); i++) {
                results.add(Car.carList.get(i).getMake());
            }
            System.out.print(results);
            return results;
        }, JsonUtil.json());

        after("/get_all_makes", (req, res) -> {
            res.type("application/json");
        });

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 7.9.17
         * <p>
         * GET request<p>
         * loops through the cars in the static carList to find all the unique
         * car makes for a specific type of car<p>
         *
         * @return JSON list of all the unique car makes found in carList that
         * match the specified type
         */
        get("/get_make_by_type", (request, response) -> {
            //Car.updateCarList();
            String type = request.queryParams("type");
            Set<String> results = new HashSet<String>();
            for (int i = 0; i < Car.carList.size(); i++) {
                if (type.equals(Car.carList.get(i).getType())) {
                    results.add(Car.carList.get(i).getMake());
                }
            }
            System.out.print(results);
            return results;
        }, JsonUtil.json());

        /**
         * Author: <b>Alexander Young</b><p>
         * Date: 7.9.17
         * <p>
         * GET request<p>
         * loops through the cars in the static carList to find all the unique
         * car models for a specific make of car<p>
         *
         * @return JSON list of all the unique car models found in carList that
         * match the specified make
         */
        get("/get_models_by_make", (request, response) -> {
            //Car.updateCarList();
            String make = request.queryParams("make");
            Set<String> results = new HashSet<String>();
            for (int i = 0; i < Car.carList.size(); i++) {
                if (make.equals(Car.carList.get(i).getMake())) {
                    results.add(Car.carList.get(i).getModel());
                }
            }
            System.out.print(results);
            return results;
        }, JsonUtil.json());

        after("/get_models_by_make", (req, res) -> {
            res.type("application/json");
        });
    }
}
