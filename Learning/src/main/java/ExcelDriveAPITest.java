import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelTestData;
import utils.ReUsablesMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ExcelDriveAPITest {

    @Test
    public void postDataXML() throws IOException {
        RestAssured.baseURI = ("http://216.10.245.166");

//        Derive the Data to HashMap from Excel
        ExcelTestData excelTestData = new ExcelTestData();
        ArrayList<String> data = excelTestData.getData("RestAssured API " , "RestAssuredLibraryAPI");
        HashMap<String , Object > jsonAsMap = new HashMap<>();

        jsonAsMap.put("name",data.get(1));
        jsonAsMap.put("isbn",data.get(2));
        jsonAsMap.put("aisle",data.get(3));
        jsonAsMap.put("author",data.get(4));

        // Nested HashMap if it is complex json

        //Add Book --> Post API
        String addBookPostResponse = given().log().all()
                .header("Content-Type", "application/json")
                .body(jsonAsMap)
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(addBookPostResponse);

        String bookID = js.get("ID").toString();
        System.out.println("ID : " + bookID);

        // Delete Book --> Delete API

        HashMap<String,Object> deleteMap = new HashMap<>();
        deleteMap.put("ID" , bookID);
        String deleteResponse = given().header("Content-Type", "application/json")
                .body(deleteMap)
                .when().post("Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = ReUsablesMethods.jsonConverter(deleteResponse);
        String deleteMsg = js1.getString("msg");
        Assert.assertEquals(deleteMsg, "book is successfully deleted");


    }
}

