package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReUsableMethods {

    public static String getJsonpath(Response response, String key) {

        JsonPath js = new JsonPath(response.asString());
        return js.get(key).toString();
    }
}
