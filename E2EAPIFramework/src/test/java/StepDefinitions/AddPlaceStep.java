package StepDefinitions;

import PojoClasses.AddPlace;
import enums.APIResources;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import utils.ReUsableMethods;
import utils.SpecBuilders;
import utils.TestData;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.testng.AssertJUnit.assertEquals;

public class AddPlaceStep {

    RequestSpecification mapsBaseURI;
    protected static Response response;
    static String extractedId;

    @Given("the Maps API base URI is configured with query parameter")
    public void baseURIConfig() throws IOException {
        useRelaxedHTTPSValidation();
        //!<----->!!<----->!!<----->!!<-----> Building the Base URI with the required common request !<----->!!<----->!!<----->!!<----->!
        mapsBaseURI = given().spec(SpecBuilders.requestSpec());
    }

    @Given("the request body contains the following location details:")
    public void requestBodySetup(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        applyPayload(rows.getFirst());
    }

    public void applyPayload(Map<String, String> dataMap) {
        mapsBaseURI.body(TestData.addPlacePayload(dataMap));
    }

    @When("I send a {string} request to {string}")
    public void sendingHTTPRequest(String method, String resourcePath) {
        //!<----->!!<----->!!<----->!!<-----> Calling the value of resource by enums
        // !<----->!!<----->!!<----->!!<-----> Only responsible for building the request
        APIResources apiResources = APIResources.valueOf(resourcePath);

        switch (method.toUpperCase()) {
            case "POST":
                response = mapsBaseURI.when().post(apiResources.getResource());
                break;

            case "GET":
                response = mapsBaseURI.when().get(apiResources.getResource());
                break;

            case "PUT":
                response = mapsBaseURI.when().put(apiResources.getResource());
                break;

            case "DELETE":
                response = mapsBaseURI.when().delete(apiResources.getResource());
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
        assertEquals(ReUsableMethods.getJsonpath(response, responseKey), responseValue);
    }

    @Then("I store the {string} value for downstream API tests")
    public String getPlaceID(String place_id) {
        //    !<----->!!<-----> Getting the placeID from ReusableMethod & return the value of the key !<----->!!<----->!!<----->!!<----->!!<----->!
        extractedId = ReUsableMethods.getJsonpath(response, place_id);
        return extractedId;
    }

    @Given("the request body contains the stored place id")
    public void theRequestBodyContainsTheStoredPlaceId() {
        String placeID = extractedId;
        {
            if (placeID == null) {
                throw new IllegalStateException("Place ID was not stored by the previous step!");
            }
            mapsBaseURI.queryParam("place_id", placeID);
        }
    }

    @And("the response body contains the following location details:")
    public void theResponseBodyContainsTheFollowingLocationDetails(DataTable dataTable) {

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        Map<String, String> row = rows.getFirst();
        AddPlace actualResponse = response.as(AddPlace.class);
        //!<----->!!<----->!!<----->!!<----->! Applying assertion to each items while Deserializing the response !<----->!!<----->!!<----->!!<----->!
        Assert.assertEquals(actualResponse.getAccuracy(), Integer.parseInt(row.get("accuracy")));
        Assert.assertEquals(actualResponse.getAddress(), row.get("address"));
        Assert.assertEquals(actualResponse.getName(), row.get("name"));
        Assert.assertEquals(actualResponse.getPhone_number(), row.get("phone_number"));
        Assert.assertEquals(actualResponse.getWebsite(), row.get("website"));
        Assert.assertEquals(actualResponse.getLanguage(), row.get("language"));
        List<String> expectedTypeList = Arrays.asList(row.get("types").split(","));
        Assert.assertEquals(actualResponse.getTypes(), expectedTypeList);
        Assert.assertEquals(actualResponse.getLocation().getLng(), Double.parseDouble(row.get("lng")));
        Assert.assertEquals(actualResponse.getLocation().getLat(), Double.parseDouble(row.get("lat")));
    }

    @Given("delete place payload")
    public void deletePlacePayload() {
        mapsBaseURI.body(TestData.deletePlacePayload(extractedId));
    }
}