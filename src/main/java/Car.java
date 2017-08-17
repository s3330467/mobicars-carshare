import java.util.*;

public class Car {
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

    public Car(String id, String type, String make, String model, String plate_no, double hourly_price, double lat, double lng, boolean available) {
        this.id = id;
        this.type = type;
        this.make = make;
        this.model = model;
        this.plate_no = plate_no;
        this.hourly_price = hourly_price;
        this.lat = lat;
        this.lng = lng;
        this.available = available;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", plate_no='" + plate_no + '\'' +
                ", hourly_price=" + hourly_price +
                ", lat=" + lat +
                ", lng=" + lng +
                ", available=" + available +
                '}';
    }

    public static void updateCarList() {
        carList = DB.fetchCars();
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

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
