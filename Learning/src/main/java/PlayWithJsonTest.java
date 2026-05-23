import Utilities.payloads;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayWithJsonTest {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(payloads.mockJson());

        //1. Print No of courses returned by API
        int course_size = js.getInt("courses.size()");
        System.out.println("Number of Courses : " + course_size);

        //2.Print Purchase Amount
        int purchasedAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount : " + purchasedAmount);
        //3. Print Title of the first course

        String title_firstCourse = js.getString("courses[0].title");
        System.out.println("Title of the first course : " + title_firstCourse);

        //4. Print All course titles and their respective Prices
        for (int i = 0; i < course_size; i++) {
            String courses_title = js.get("courses[" + i + "].title");
            int course_price = js.get("courses[" + i + "].price");
            System.out.println("course Title: " + courses_title + "Price : " + course_price);
        }

        //5. Print no of copies sold by RPA Course
        int noOfCopiesRPA = 0;
        for (int i = 0; i < course_size; i++) {
            String title = js.get("courses[" + i + "].title");
            if (title.equalsIgnoreCase("rpa")) {
                noOfCopiesRPA = js.get("courses[" + i + "].copies");
                break;
            }
        }
        System.out.println("No of copies sold by RPA Course : " + noOfCopiesRPA);
    }

    //6. Verify if Sum of all Course prices matches with Purchase Amount
    @Test
    public void sumCoursePriceValidation() {
        int totalPrice = 0;
        JsonPath js = new JsonPath(payloads.mockJson());
        int course_size = js.getInt("courses.size()");
        int purchasedAmount = js.getInt("dashboard.purchaseAmount");

        for (int i = 0; i < course_size; i++) {
            int price = js.get("courses[" + i + "].price");
            int copies = js.get("courses[" + i + "].copies");
            totalPrice += (price * copies);
        }
        if (totalPrice == purchasedAmount) {
            System.out.println("Price matches !!!! ");
        } else {
            System.out.println("Price does not matches");
        }
        Assert.assertEquals(totalPrice, purchasedAmount);


    }
}
