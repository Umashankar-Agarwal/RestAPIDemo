import Utilities.payloads;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.equalTo;

public class SpecBuildersImpTest {

    @Test
    public void specBuilder() throws IOException {

        RestAssured.useRelaxedHTTPSValidation();

        // !<----->!!<----->! Apply Request Specification !<----->!!<----->!!<----->!!<----->!

        RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();

        RequestSpecification res = given().spec(requestSpec).body(new String(Files.readAllBytes(Paths.get("C:\\Users\\335418\\IdeaProjects\\JsonFiles.txt"))));

        // !<----->!!<----->! Apply Request Specification !<----->!!<----->!!<----->!!<----->!
        ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        // !<----->!!<----->! Apply Builders & form the exact !<----->!!<----->!!<----->!!<----->!
        String addPlaceResponse = res.when().post("maps/api/place/add/json").then().log().all().spec(responseSpec)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        JsonPath js = new JsonPath(addPlaceResponse);
        String place_ID = js.getString("place_id");

        // Put Place --> use the place ID to update the address

        String newAddress = "Park Hospital , Gurugaram";

        // !<----->!!<----->! Apply Builders !<----->!!<----->!!<----->!!<----->!

        given().log().all().spec(requestSpec)
                .body(payloads.putPlace(place_ID, newAddress))
                .when().put("maps/api/place/update/json")
                .then().log().all().spec(responseSpec).body("msg", equalTo("Address successfully updated"));

        // GET place --> the response with the new updated address

        String getPlaceResponse = given().log().all().spec(requestSpec)
                .queryParam("place_id", place_ID)
                .when().get("maps/api/place/get/json")
                .then().log().all().spec(responseSpec).extract().response().asString();

        JsonPath js2 = new JsonPath(getPlaceResponse);
        String getResponseAddress = js2.getString("address");
        Assert.assertEquals(getResponseAddress, newAddress);
    }
}
