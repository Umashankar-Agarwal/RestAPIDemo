package StepDefinitions;

import enums.APIResources;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ReUsableMethods;
import utils.SpecBuilders;
import utils.TestData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.testng.AssertJUnit.assertEquals;

public class AddPlaceStep {

    protected RequestSpecification mapsBaseURI;
    protected RequestSpecification reqAddPlace;
    protected static Response response;

    @Given("the Maps API base URI is configured with query parameter")
    public void baseURIConfig() throws IOException {
        useRelaxedHTTPSValidation();
//        !<----->!!<----->!!<----->!!<-----> Building the Base URI with the required common request !<----->!!<----->!!<----->!!<----->!
        mapsBaseURI = SpecBuilders.requestSpec();
    }

    @Given("the request body contains the following location details:")
    public void requestBodySetup(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        reqAddPlace = given().spec(mapsBaseURI).body(TestData.addPlacePayload(rows.getFirst()));
    }

    @When("I send a {string} request to {string}")
    public void sendingHTTPRequest(String method , String resourcePath) {
        //!<----->!!<----->!!<----->!!<-----> Calling the value of resource by enums
        // !<----->!!<----->!!<----->!!<-----> Only responsible for building the request
        APIResources apiResources = APIResources.valueOf(resourcePath);

        switch (method.toUpperCase()) {
            case "POST":
                response = reqAddPlace.when().post(apiResources.getResource());
                break;

            case "PUT":
                response = reqAddPlace.when().put(apiResources.getResource());
                break;

            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    @Then("the API response status code should be {int}")
    public void validateAPIResponseStatusCode(int statusCode) {
        response = response.then().extract().response();
        assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("the response body field {string} should be {string}")
    public void validateResponseBody(String responseKey, String responseValue) {
        assertEquals(ReUsableMethods.getJsonpath(response,responseKey), responseValue);
    }

    @Then("I store the {string} value for downstream API tests")
    public static String getPlaceID(String place_id) {
//        !<----->!!<-----> Getting the placeID from ReusableMethod & return the value of the key !<----->!!<----->!!<----->!!<----->!!<----->!
        return ReUsableMethods.getJsonpath(response,place_id);
    }

    @Given("the request body contains the {string} ")
    public void requestBodyGetPlace(String place_id){

    }
//
//    @And("the request body contains valid location details")
//    public void theRequestBodyContainsValidLocationDetails() {
//        throw new io.cucumber.java.PendingException();
//
//    }
//
//    @Given("the request body is missing the {string} field")
//    public void theRequestBodyIsMissingTheField(String arg0) {
//        throw new io.cucumber.java.PendingException();
//
//    }
//
//    @And("the response body field {string} should indicate a validation failure")
//    public void theResponseBodyFieldShouldIndicateAValidationFailure(String arg0) {
//        throw new io.cucumber.java.PendingException();
//    }
}
