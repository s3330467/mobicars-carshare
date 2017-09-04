/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class allows you to make a spark route return in Json format by using
 * a Json response transformer, see CarController /cars route for an example
 * @author alex
 */
import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtil {
 
  public static String toJson(Object object) {
    return new Gson().toJson(object);
  }
 
  public static ResponseTransformer json() {
    return JsonUtil::toJson;
  }
}
