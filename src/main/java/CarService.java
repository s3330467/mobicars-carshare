
import org.sql2o.*;
import java.util.*;

public class CarService {

    public static List<Car> getAllCars() {
        Car.updateCarList();
        return Car.carList;
    }
    //test
/*
    *Edited:28-08-2017 by vishal Pradhan
    added image field on create car
    
    */
    public static void createCar(String image, String type, String make, String model, String plate_no, double hourly_price, double lat, double lng) {
        DB.insertCar(image, type, make, model, plate_no, hourly_price, lat, lng);
        Car.updateCarList();
        return;
    }
    
    /*  added 12-09-17 Alexander Young
        deletes a car from the database based off plate_no
    */
    public static void deleteCar(String plate_no) {
        DB.deleteCar(plate_no);
        Car.updateCarList();
        return;
    }

    public static Car getCarByPlate_no(String plate_no) {

        int i;
        Car.updateCarList();
        for (i = 0; i < Car.carList.size(); i++) {
            if (Car.carList.get(i).getPlate_no().equals(plate_no)) {
                System.out.println("checking plate_no: " + Car.carList.get(i).getPlate_no());
                return Car.carList.get(i);
            }
        }
        return null;
    }

    public static Car getCarById(String id) {

        int i;
        Car.updateCarList();
        for (i = 0; i < Car.carList.size(); i++) {
            if (Car.carList.get(i).getId().equals(id)) {
                return Car.carList.get(i);
            }
        }
        return null;
    }

    public static List<Car> carSearch(String type, String make, String model) {
        Car.updateCarList();
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
        } 
        else {
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
}
