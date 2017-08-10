import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class CarController {
    public CarController(final CarService carService) {

    get("/cars", (request, response) -> {
        return CarService.getAllCars();
    });
}
