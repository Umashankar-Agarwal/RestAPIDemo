import io.restassured.path.json.JsonPath;

public class JsonCoverter {
    public static JsonPath jsonConverter(String response){

        return new JsonPath(response);
    }
}
