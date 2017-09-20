
import java.util.*;

/**
 * Date: 10.8.17
 * <p>
 * POJO Car object declaring the cars MySQL table fields as variables.<p>
 *
 * Updated 10.9.17 by Rachel Tan<p>
 * Added the variables: collection_date, collection_time and end_date<p>
 * 
 * Updated by Vishal Pradhan<p>
 * Added image fields along with get and set methods<p>
 *
 * Updated 10.9.17 by Alexander Young<p>
 * Added an instance of CarSimulator to each car, this class can be called to simulate car movement<p>
 * 
 * @author Rachel Tan
 * 
 */
public class Car {

    private String image;
    private String id;
    private String type;
    private String make;
    private String model;
    private String plate_no;
    private double hourly_price;
    private double lat;
    private double lng;
    private boolean available;
    public static List<Car> carList = new ArrayList<Car>();
    public transient CarSimulator carSim;
    
    /**
     * Constructor for a new Car
     * <p>
     * Author: <b>Rachel Tan</b>
     *
     * @param id the unique car identifier, an auto incrementing integer
     * @param type the type of the car - e.g., sedan, hatchback, luxury
     * @param make the make of the car - e.g., Ford, Mazda, Toyota
     * @param model the model of the car - e.g., Festiva, Corolla, Camry
     * @param plate_no the registration plate number of the car
     * @param hourly_price the price in AUD that is charged for every hour the
     * car is rented
     * @param lat the latitude of the current position of the car
     * @param lng the longitude of the current position of the car
     * @param available boolean is true if the car is not currently booked,
     * false if the car is booked or otherwise unavailable for booking
     */
    public Car(String image, String id, String type, String make, String model, String plate_no, double hourly_price, double lat, double lng, boolean available) {
        this.image = image;
        this.id = id;
        this.type = type;
        this.make = make;
        this.model = model;
        this.plate_no = plate_no;
        this.hourly_price = hourly_price;
        this.lat = lat;
        this.lng = lng;
        this.available = available;
        carSim = new CarSimulator(this);
    }

    //toString method
    @Override
    public String toString() {
        return "Car{"
                + "image='" + image + '\''
                + "id='" + id + '\''
                + ", type='" + type + '\''
                + ", make='" + make + '\''
                + ", model='" + model + '\''
                + ", plate_no='" + plate_no + '\''
                + ", hourly_price=" + hourly_price
                + ", lat=" + lat
                + ", lng=" + lng
                + ", available=" + available
                + '}';
    }

    /**
     * used to synchronise the state of the static arraylist of Cars with the
     * state of the database.
     * <p>
     * Author: <b>Rachel Tan</b>
     */
    public static void updateCarList() {
        carList = DB.fetchCars();
        Car car;
        for(int i = 0; i < carList.size(); i++) {
            car = carList.get(i);
            //car.carSim = new CarSimulator(car);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate_no() {
        return plate_no;
    }

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }

    public double getHourly_price() {
        return hourly_price;
    }

    public void setHourly_price(double hourly_price) {
        this.hourly_price = hourly_price;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
