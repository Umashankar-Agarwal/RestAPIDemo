import Utilities.ReUsablesMethods;
import Utilities.payloads;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PlayWithJiraBug {

    // Here we are going to play with
    // Creating the bug in jira (real time api )
    // adding the attachement to the bug created

    private static int issueID;
    private static final String JIRA_URL = System.getenv("JIRA_BASE_URL"); // Jira URL
    private static final String JIRA_EMAIL = System.getenv("JIRA_EMAIL"); // Jira Email
    private static final String JIRA_TOKEN = System.getenv("JIRA_TOKEN"); // Jira Token
    private static final String ATTACHMENT_PATH = System.getenv("JIRA_ATTACHMENT_PATH"); // Attachement Path

    @Test(priority = 1)
    public void bugCreation() {


        // Creating the base URL
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = JIRA_URL;

        String responseBugCreation = given().log().all().header("Content-Type", "application/json")
                .auth().preemptive()
                .basic(JIRA_EMAIL, JIRA_TOKEN)
                .contentType(ContentType.JSON)
                .body(payloads.createBug())
                .when().post("rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(responseBugCreation);
        issueID = js.getInt("id");
        System.out.println(js.getInt("id"));
    }

    @Test(priority = 2, dependsOnMethods = "bugCreation")
    public void attachmentsIssue() {

        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://umashankarqa.atlassian.net/";

        String responseAttachment = given().log().all().header("X-Atlassian-Token", "no-check").pathParam("Key", issueID)
                .auth().preemptive()
                .basic(JIRA_EMAIL, JIRA_TOKEN)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", new File(ATTACHMENT_PATH))
                .when().post("rest/api/3/issue/{Key}/attachments")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(responseAttachment);
        String filename = js.getString("filename");
        System.out.println(filename);
    }

}