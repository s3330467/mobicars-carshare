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
    }
}
