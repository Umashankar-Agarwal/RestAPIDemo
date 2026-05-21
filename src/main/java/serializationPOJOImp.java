import Pojo.AddLocation;
import Pojo.AddPlace;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.equalTo;

public class serializationPOJOImp {

    @Test
    public void serializationAddPlace() {

        useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        //!<--->!<--->!<--->! Applying the Serialization to generate the Body Payload using the POJO Classes !<--->!<--->!<--->!<--->
        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(40);
        addPlace.setAddress("Gurugram , Sector 49 ");
        addPlace.setLanguage("English");
        addPlace.setName("Uma Shankar - Serialization Implementation");
        addPlace.setPhone_number("+91 8982712812");
        addPlace.setWebsite("https://www.google.com/search");
        // !<----->!!<----->! Applying to the nested jsons !<----->!!<----->!
        AddLocation addLocation = new AddLocation();
        addLocation.setLat(-38.383494);
        addLocation.setLng(33.427362);
        addPlace.setLocation(addLocation);

        List<String> myList = Arrays.asList("Residential", "Commercial", "migration");
        addPlace.setTypes(myList);


        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(addPlace)
                .when().post("maps/api/place/add/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("status", equalTo("OK"))
                .body("scope", equalTo("APP"));
    }

}
