import org.sql2o.*;
import java.util.*;

public class CarService {

    public static List<Car> getAllCars() {
        Car.updateCarList();
        return Car.carList;
    }
    //test

    public static void createCar(String type, String make, String model, String plate_no , double hourly_price, double lat, double lng) {
        DB.insertCar(type, make, model, plate_no, hourly_price, lat, lng);
        Car.updateCarList();
        return;
    }
    
    public static Car getCarByPlate_no(String plate_no) {

        int i;
        Car.updateCarList();
        for(i = 0; i <Car.carList.size(); i++) {
            if(Car.carList.get(i).getPlate_no().equals(plate_no)) {
                System.out.print("checking plate_no: " + Car.carList.get(i).getPlate_no());
                return Car.carList.get(i);
            }
        }
        return null;
    }
    
    public static Car getCarById(String id) {

        int i;
        Car.updateCarList();
        for(i = 0; i <Car.carList.size(); i++) {
            if(Car.carList.get(i).getId().equals(id)) {
                return Car.carList.get(i);
            }
        }
        return null;
    }
}
