package StepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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
    protected Response response;
    protected JsonPath js;
    protected String placeID;

    @Given("the Maps API base URI is configured with query parameter")
    public void baseURIConfig() throws IOException {
        useRelaxedHTTPSValidation();
        mapsBaseURI = SpecBuilders.requestSpec();
    }

    @Given("the request body contains the following location details:")
    public void requestBodySetup(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        reqAddPlace = given().spec(mapsBaseURI).body(TestData.addPlacePayload(rows.getFirst()));
    }

    @When("I send a POST request to {string}")
    public void sendingHTTPRequest(String resourcePath) {
        response = reqAddPlace.when().post(resourcePath).then().extract().response();
    }

    @Then("the API response status code should be {int}")
    public void validateAPIResponseStatusCode(int statusCode) {
        assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("the response body field {string} should be {string}")
    public void validateResponseBody(String responseKey, String responseValue) {
        js = new JsonPath(response.asString());
        assertEquals(js.get(responseKey), responseValue);
    }

    @Then("I store the {string} value for downstream API tests")
    public void getPlaceID(String place_id) {
        placeID = js.get(place_id);
        System.out.println(placeID);
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
