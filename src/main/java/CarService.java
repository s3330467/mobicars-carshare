
import org.sql2o.*;
import java.util.*;

/**
 * Date: 10.8.17
 * <p>
 * Contains methods that manipulate Car data and calls SQL methods in the DB
 * class to insert or retrieve car data
 *
 * @author Alexander Young
 */
public class CarService {

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 10.8.17
     * <p>
     * Updates the carList array from the DB and then returns an arraylist of
     * Car's
     * 
     * Updated 28.08.2017 by Vishal Pradhan<p>
     * Added image field on create car<p>
     * 
     * @return a single Booking object that matches the booking_id parameter
     */
    public static List<Car> getAllCars() {
        //Car.updateCarList();
        return Car.carList;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 10.8.17
     * <p>
     * calls the DB insert car method to insert a new car into the SQL database,
     * then updates the carList<p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed updateCarList() method and instead add a single new car the
     * static Car.carList
     *
     * @param image filename of the image to display for this car
     * @param type the type of the car to be added- e.g., sedan, hatchback,
     * luxury
     * @param make the make of the car to be added - e.g., Ford, Mazda, Toyota
     * @param model the model of the car to be added - e.g., Festiva, Corolla,
     * Camry
     * @param plate_no the registration plate number of the car to be added
     * @param hourly_price the price in AUD that is charged for every hour the
     * car is rented
     * @param lat the latitude of the current position of the car
     * @param lng the longitude of the current position of the car
     */
    public static void createCar(String image, String type, String make, String model, String plate_no, double hourly_price, double lat, double lng) {
        DB.insertCar(image, type, make, model, plate_no, hourly_price, lat, lng);
        Car.carList.add(CarService.getCarByPlate_no(plate_no));
        //Car.updateCarList();
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 12.9.17
     * <p>
     * deletes a car from the database and updates the static carList array<p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateCarList method, the method now updates
     * java objects directly as well as editing the DB<p>
     *
     * @param plate_no the registration plate number of the car to be deleted
     */
    public static void deleteCar(String plate_no) {
        Car.carList.remove(getCarByPlate_no(plate_no));
        DB.deleteCar(plate_no);
        //Car.updateCarList();
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 13.8.17
     * <p>
     * get a single car from the carList array that matches the given
     * plate_no<p>
     *
     * @param plate_no the registration plate number of the car to be fetched,
     * return null if the car cannot be found
     */
    public static Car getCarByPlate_no(String plate_no) {

        int i;
        //Car.updateCarList();
        for (i = 0; i < Car.carList.size(); i++) {
            if (Car.carList.get(i).getPlate_no().equals(plate_no)) {
                System.out.println("checking plate_no: " + Car.carList.get(i).getPlate_no());
                return Car.carList.get(i);
            }
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.8.17
     * <p>
     * get a single car from the carList array that matches the given car id<p>
     *
     * @param id the unique id of the car to be fetched, return null if the car
     * cannot be found
     */
    public static Car getCarById(String id) {

        int i;
        //Car.updateCarList();
        for (i = 0; i < Car.carList.size(); i++) {
            if (Car.carList.get(i).getId().equals(id)) {
                return Car.carList.get(i);
            }
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 8.9.17
     * <p>
     * return an arraylist of cars that match given search criteria, can search
     * by the following combinations<p>
     * type<p>
     * make<p>
     * model<p>
     * type + make<p>
     * type + make + model<p>
     *
     * a field that is not being searched for should not be null, it should be a
     * string that contains the text "empty"
     *
     * @param type the type of car being searched for, if not searching for type
     * the expected parameter is "empty" not null
     * @param make the make of car being searched for, if not searching for make
     * the expected parameter is "empty" not null
     * @param model the model of car being searched for, if not searching for
     * model the expected parameter is "empty" not null
     * @return an arraylist of cars that match to given search parameters, the
     * list will be empty if no matches are found
     */
    public static List<Car> carSearch(String type, String make, String model) {
        //Car.updateCarList();
        List<Car> carList = Car.carList;
        List<Car> searchResults = new ArrayList<Car>();
        if (make.equals("empty") && model.equals("empty") && type.equals("empty")) {
            //user submitted no search criteria, the search will fail
            return searchResults;
        }
        if (make.equals("empty") && model.equals("empty")) {
            //user is searching for type
            System.out.println(type);
            for (int i = 0; i < carList.size(); i++) {
                if (carList.get(i).getType().toLowerCase().equals(type.toLowerCase())) {
                    System.out.println("1 criteria met");
                    searchResults.add(carList.get(i));
                }
            }
        }
        if (type.equals("empty") && model.equals("empty")) {
            //user is searching for make
            System.out.println(make);
            for (int i = 0; i < carList.size(); i++) {
                if (carList.get(i).getMake().toLowerCase().equals(make.toLowerCase())) {
                    System.out.println("1 criteria met");
                    searchResults.add(carList.get(i));
                }
            }
        }
        if (model.equals("empty")) {
            //user is searching for type and make
            System.out.println(type + make);
            for (int i = 0; i < carList.size(); i++) {
                if (carList.get(i).getType().toLowerCase().equals(type.toLowerCase())) {
                    System.out.println("1 criteria met");
                    if (carList.get(i).getMake().toLowerCase().equals(make.toLowerCase())) {
                        System.out.println("2 criteria met");
                        searchResults.add(carList.get(i));
                    }
                }
            }
        } else {
            //user is searching for type, make, model
            System.out.println(type + make + model);
            for (int i = 0; i < carList.size(); i++) {
                if (carList.get(i).getType().toLowerCase().equals(type.toLowerCase())) {
                    System.out.println("1 criteria met");
                    if (carList.get(i).getMake().toLowerCase().equals(make.toLowerCase())) {
                        System.out.println("2 criteria met");
                        if (carList.get(i).getModel().toLowerCase().equals(model.toLowerCase())) {
                            System.out.println("all criteria met");
                            searchResults.add(carList.get(i));
                        }
                    }
                }
            }
        }
        return searchResults;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 19.9.17
     * <p>
     * updates a specified car to a new latitude and longitude<p>
     *
     * @param plate_no the registration plate number of the car to be deleted
     * @param lat the new latitude of the users position
     * @param lng the new longitude of the users position
     */
    public static void updateCarLatLng(String plate_no, double lat, double lng) {
        Car car = getCarByPlate_no(plate_no);
        car.setLat(lat);
        car.setLng(lng);
        DB.updateCarLatLng(plate_no, lat, lng);
        //Car.updateCarList();
    }
}
