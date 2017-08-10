import org.sql2o.*;
import java.util.*;


public class DB {
    public static String sqlDB = "jdbc:mysql://localhost:3306/mobicars";
    public static String sqlUser = "ubuntu";
    public static String sqlPass = "password";

    public static List<User> fetchUsersFromDB() {
        String sql = "SELECT *" +
                "FROM users";

        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
    }

    public static void insertUser(String email, String password) {
        Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
        String sql = "insert into users ( email, password ) values ( :email, :password )";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
        }
    }

        public static List<Car> fetchCarsFromDB () {
            String sql = "SELECT *" +
                    "FROM cars";

            Sql2o sql2o = new Sql2o(sqlDB, sqlUser, sqlPass);
            try (Connection con = sql2o.open()) {
                return con.createQuery(sql).executeAndFetch(Car.class);
            }
        }

    }
}