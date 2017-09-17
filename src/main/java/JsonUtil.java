
import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Date: 4.9.17
 * <p>
 * This class allows you to make a spark route return in Json format by using a
 * Json response transformer, see CarController.java /cars route for an example
 *
 * @author Alexander Young
 */
public class JsonUtil {

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 4.9.17
     * <p>
     * converts a java object to a JSON string<p>
     *
     * @param object the object to convert to JSON
     * @return a stringified version of the object in JSON format
     */
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 4.9.17
     * <p>
     * spark java response transformer override that allows a route to return
     * JSON<p>
     *
     * @return the spark response in JSON form
     */
    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
