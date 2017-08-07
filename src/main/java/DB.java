import org.sql2o.*;

public class DB {

    public static Sql2o sql2o;
    static {
        Sql2o sql2o = new Sql2o("jdbc:mysql://ec2-13-210-66-178.ap-southeast-2.compute.amazonaws.com", null, null);
    }
}