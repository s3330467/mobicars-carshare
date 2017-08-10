import org.sql2o.*;
import java.util.*;

public class CarService {

    public static List<Car> getAllCars() {
        Car.updateCarList();
        return Car.carList;
    }

    public static void createCar(String type, String make, String model, String plate_no , double hourly_price, double lat, double lng) {
        DB.insertCar(type, make, model, plate_no, hourly_price, lat, lng);
        Car.updateCarList();
        return;
    }
}
