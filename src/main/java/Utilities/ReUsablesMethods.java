package Utilities;

import io.restassured.path.json.JsonPath;

public class ReUsablesMethods {

    public static JsonPath jsonConverter(String response){

        JsonPath js = new JsonPath(response);

        return js;
    }
}
