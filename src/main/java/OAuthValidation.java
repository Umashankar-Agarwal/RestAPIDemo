import Utilities.ReUsablesMethods;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class OAuthValidation {

    public static void main(String[] args) {

        useRelaxedHTTPSValidation();
        baseURI = "https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";

        String authorizationResponse = given().log().all().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post()
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(authorizationResponse);
        String accessToken = js.get("access_token");
        System.out.println(accessToken);

        // !<--->!<--->!<--->!<---> This below we have just used the normal way to extract the response & work on it !<--->!<--->!<--->!<--->
        // !<--->!<--->!<--->!<---> & for Deserialization we have the seperate class !<--->!<--->!<--->!<--->!<--->!<--->!<--->!<--->!<--->!<--->

        String courseDetailsResponse = given().queryParam("access_token", accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
        JsonPath js1 = ReUsablesMethods.jsonConverter(courseDetailsResponse);

        System.out.println(courseDetailsResponse);
    }
}
