import Utilities.payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class basics {

    private static final Logger log = LoggerFactory.getLogger(basics.class);

    public static void main(String[] args) throws IOException {

//    RestAssured.baseURI = “baseURL”;
//    given → all input details ( Parameters , Authorizations ,
//    when → Submit the API
//    then → validate the response
//
//         Add place --> Update palce with the new address --> get place validate with the new address

        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Post Place --> using the post methods --> to add the place
        String addPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
//                .body(payloads.addPlace())

                // Another method to pass the json from file
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\335418\\IdeaProjects\\JsonFiles.txt"))))
                .when().post("maps/api/place/add/json")
                // Validate the status code , Header (response from the server) , body value
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        JsonPath js = new JsonPath(addPlaceResponse);
        String place_ID = js.getString("place_id"); // path should be start from parent

        // Put Place --> use the place ID to update th eaddress

        String newAddress = "Park Hospital , Gurugaram";
        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payloads.putPlace(place_ID, newAddress))
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        // GET place --> the response with the new updated address

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", place_ID)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js2 = new JsonPath(getPlaceResponse);
        String getResponseAddress = js2.getString("address");
        Assert.assertEquals(getResponseAddress, newAddress);
    }
}
