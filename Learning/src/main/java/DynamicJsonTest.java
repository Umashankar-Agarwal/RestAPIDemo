import Utilities.ReUsablesMethods;
import Utilities.dataprovider;
import Utilities.payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


public class DynamicJsonTest {

    @Test(dataProvider = "LibraryAPIData" , dataProviderClass = dataprovider.class)
    public void addBookLibrary(String isbn , String aisle){

        RestAssured.baseURI= ("http://216.10.245.166/");

        //Add Book --> Post API
        String addBookPostResponse =  given().log().all().header("Content-Type", "application/json")
                .body(payloads.addBookLibraryPayload(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(addBookPostResponse);

        String bookID =  js.get("ID").toString();
        System.out.println("ID : " + bookID );

        // Delete Book --> Delete API

        String deleteResponse = given().header("Content-Type", "application/json").body(payloads.deleteBookPayload(bookID))
                .when().post("Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = ReUsablesMethods.jsonConverter(deleteResponse);
        String deleteMsg = js1.getString("msg");
        Assert.assertEquals(deleteMsg, "book is successfully deleted");


    }

}
