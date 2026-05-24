package StepDefinitions;

import PojoClasses.AddLocation;
import PojoClasses.AddPlace;
import PojoClasses.AddPlaceResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.testng.AssertJUnit.assertEquals;

public class AddPlaceStep {

    protected RequestSpecification mapsBaseURI;
    protected RequestSpecification reqAddPlace;
    protected final String BaseURI = "https://rahulshettyacademy.com/";
    protected AddPlace addPlace;
    protected ResponseSpecification responseSpec;
    protected Response response;
    protected JsonPath js;
    protected String placeID;

    @Given("the Maps API base URI is configured with query parameter {string} is set to {string}")
    public void the_maps_api_base_uri_is_configured_with_query_parameter_is_set_to(String queryKey, String queryValue) {
        useRelaxedHTTPSValidation();
        mapsBaseURI = new RequestSpecBuilder().setBaseUri(BaseURI).
                addQueryParam(queryKey, queryValue).build();
    }

    @Given("the request body contains the following location details:")
    public void the_request_body_contains_the_following_location_details(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = rows.getFirst();

        AddLocation addLocation = new AddLocation();
        addLocation.setLat(Double.parseDouble(row.get("lat")));
        addLocation.setLng(Double.parseDouble(row.get("lng")));

        addPlace = new AddPlace();
        addPlace.setLocation(addLocation);
        addPlace.setAccuracy(Integer.parseInt(row.get("accuracy")));
        addPlace.setAddress(row.get("address"));
        addPlace.setLanguage(row.get("language"));
        addPlace.setName(row.get("name"));
        addPlace.setPhone_number(row.get("phone_number"));
        addPlace.setWebsite(row.get("website"));
        List<String> typesList = Arrays.asList(row.get("types").split(","));
        addPlace.setTypes(typesList);
        reqAddPlace = given().log().all().spec(mapsBaseURI).header("Content-Type", "application/json").body(addPlace);
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String resourcePath) {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqAddPlace.when().post(resourcePath).then().log().all().spec(responseSpec).extract().response();
    }

    @Then("the API response status code should be {int}")
    public void the_api_response_status_code_should_be(int statusCode) {
       assertEquals(response.getStatusCode(),statusCode);
    }

    @Then("the response body field {string} should be {string}")
    public void the_response_body_field_should_be(String responseKey, String responseValue) {
        js = new JsonPath(response.asString());
        assertEquals(js.get(responseKey), responseValue);
    }

    @Then("I store the {string} value for downstream API tests")
    public void i_store_the_value_for_downstream_api_tests(String place_id) {
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
