import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class CarController {
    public CarController(final CarService carService) {

        get("/cars", (request, response) -> {
            Car.updateCarList();
            return Car.carList;
        });
        
        post("/process_get_car_details", (request, response) -> {
            String plate_no = request.queryParams("plate_no");
            return CarService.getCarWindow(CarService.getCarByPlate_no(plate_no));
        });
    }
}
