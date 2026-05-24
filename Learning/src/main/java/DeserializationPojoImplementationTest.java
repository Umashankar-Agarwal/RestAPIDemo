import Pojo.Api;
import Pojo.GetCourseDetail;
import Pojo.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReUsablesMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class DeserializationPojoImplementationTest {

    @Test
    public void Deserialization() {

        String[] expectedWebAutomationCoursesList = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        useRelaxedHTTPSValidation();
        baseURI = "https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";

        // !<--->!<--->!<--->!<--->!<--->!<---> Authorization !<--->!<--->!<--->!<--->!<--->!<--->!<--->!<--->
        String authorizationResponse = given().log().all().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post()
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(authorizationResponse);
        String accessToken = js.get("access_token");
        System.out.println(accessToken);

        // !<--->!<--->!<--->!<--->!<--->!<--->Getting the actual course details response !<--->!<--->!<--->!<--->!<--->!<--->!<--->!<--->
        GetCourseDetail cs =
                given().queryParam("access_token", accessToken)
                        .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourseDetail.class);

        // !<--->!<--->!<--->!<---> General Questions!<--->!<--->!<--->!<--->
        System.out.println("Instructor : " + cs.getInstructor());
        System.out.println("LinkedIN : " + cs.getLinkedIn());

        // !<--->!<--->!<--->!<--->!<--->!<---> 2. print the course price of SoapUI WebServices Testing !<--->!<--->!<--->!<--->!<--->
        List<Api> apiCoursesList = cs.getCourses().getApi();

        for (Api a : apiCoursesList) {
            if (a.getCourseTitle().equalsIgnoreCase("soapUI WebServices Testing")) {
                System.out.println("Course Title : " + a.getCourseTitle() + " Course Price : " + a.getPrice());
            }
        }

        // !<--->!<--->!<--->!<--->!<--->!<---> 2. print all the course title of WebAutomation Courses !<--->!<--->!<--->!<--->!<--->

        List<WebAutomation> wcList = cs.getCourses().getWebAutomation();
        ArrayList<String> actualCourseTitle = new ArrayList<>();
        System.out.println("Below are the available courses for WebAutomation");
        for (WebAutomation wa : wcList) {
            System.out.println(wa.getCourseTitle());

            // !<--->!<--->!<--->!<--->!<--->!<---> Apply Assertion & Validate the Expected & actual  !<--->!<--->!<--->!<--->!<--->

            actualCourseTitle.add(wa.getCourseTitle());
        }
        List<String> expectedList = Arrays.asList(expectedWebAutomationCoursesList);

        Assert.assertEquals(expectedList, actualCourseTitle, "List Does not matches");


    }
}



