import files.payloads;
import io.restassured.RestAssured;
import static org.hamcrest.Matcher.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class basics {

    public static void main (String [] args){

        //    RestAssured.baseURI = “baseURL”;
//    given → all input details ( Parameters , Authorizations ,
//    when → Submit the API
//    then → validate the response

        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // using the post methods --> to add the place
        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payloads.addPlace())
                .when().post("maps/api/place/add/json")
                // Validate the status code , Header (response from the server) , body value
                .then().log().all().assertThat().statusCode(200)
                    .body("scope" , equalTo("APP"))
                    .header("Server", "Apache/2.4.52 (Ubuntu)");

    }


}
