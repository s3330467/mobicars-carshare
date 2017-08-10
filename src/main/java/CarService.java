import org.sql2o.*;
import java.util.*;


public class CarService {
    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";

    public static List<Car> getAllCars(){
        String sql = "SELECT *" +
                "FROM cars";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Car.class);
        }
    }

    public static User createCar(String type, double price_per_hour, double lat, double lng) {

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "insert into cars ( type, price_per_hour, lat, lng ) values ( :type, :price_per_hour, :lat, :lng )";

        //if either lat or lng are not supplied the coordinates are not valid and are set to 0.0
        if(lat==null || lng==null) {
            lat = 0.0;
            lng = 0.0;
        }
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("type", type)
                    .addParameter("price_per_hour", price_per_hour)
                    .addParameter("lat", lat)
                    .addParameter("lng", lng)
                    .executeUpdate();
        }
        return null;
    }
}
