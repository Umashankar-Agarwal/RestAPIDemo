import Pojo.api;
import Pojo.getCourseDetail;
import Pojo.webAutomation;
import Utilities.ReUsablesMethods;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public static void main(String[] args) {

    String [] expectedWebAutomationCoursesList = {"Selenium Webdriver Java" , "Cypress", "Protractor"};
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
    getCourseDetail cs =
            given().queryParam("access_token", accessToken)
                    .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(getCourseDetail.class);

    // !<--->!<--->!<--->!<---> General Questions!<--->!<--->!<--->!<--->
    System.out.println("Instructor : " + cs.getInstructor());
    System.out.println("LinkedIN : " + cs.getLinkedIn());

    // !<--->!<--->!<--->!<--->!<--->!<---> 2. print the course price of SoapUI WebServices Testing !<--->!<--->!<--->!<--->!<--->
    List<api> apiCoursesList = cs.getCourses().getApi();

    for (api a : apiCoursesList) {
        if (a.getCourseTitle().equalsIgnoreCase("soapUI WebServices Testing")) {
            System.out.println("Course Titile : " + a.getCourseTitle() + " Course Price : " + a.getPrice());
        }
    }

    // !<--->!<--->!<--->!<--->!<--->!<---> 2. print all the course title of WebAutomation Courses !<--->!<--->!<--->!<--->!<--->

    List<webAutomation> wcList = cs.getCourses().getWebAutomation();
    ArrayList<String> actualCourseTitle = new ArrayList<String>();
    System.out.println("Below are the available courses for WebAutomation");
    for (webAutomation wa : wcList){
        System.out.println(wa.getCourseTitle());

        // !<--->!<--->!<--->!<--->!<--->!<---> Apply Assertion & Validate the Expected & actual  !<--->!<--->!<--->!<--->!<--->

        actualCourseTitle.add(wa.getCourseTitle());
    }
    List<String > expectedList = Arrays.asList(expectedWebAutomationCoursesList);

    Assert.assertEquals(expectedList, actualCourseTitle, "List Does not matches");


}


