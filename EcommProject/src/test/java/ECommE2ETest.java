import Pojo.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class ECommE2ETest {

    private final String endPoint = "https://rahulshettyacademy.com/api/ecom/";
    private String token;
    private String userID;
    private String productId;
    private ArrayList<String> orderID;
    private final String userEmail = "umarestapi@gmail.com";

    @Test(priority = 1)
    public void Login_TS01() {
        useRelaxedHTTPSValidation();
        // <------>!<------>!<------>! Used Spec Builders for Request & Response <------>!<------>!<------>!<------>!
        RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri(endPoint).setContentType(ContentType.JSON).build();

        // <------>!<------>!<------>! Used Pojo Classes to set the request parameters <------>!<------>!<------>!
        LoginRequest login = new LoginRequest();
        login.setUserEmail(userEmail);
        login.setUserPassword("Shankar@123");

        RequestSpecification reqLogin = given().spec(reqSpec).log().all().body(login);

        // <------>!<------>!<------>! Used Pojo Classes to parse the response <------>!<------>!<------>!
        LoginResponse loginResponse = reqLogin.when().post("auth/login").
                then().log().all().assertThat().statusCode(200).extract().response().as(LoginResponse.class);
        token = loginResponse.getToken();
        userID = loginResponse.getUserId();
        String msg = loginResponse.getMessage();

        System.out.println("Response from LoginRequest : " + "token : " + token + " userID : " + userID + "Message : " + msg);
    }

    @Test(priority = 2, dependsOnMethods = "Login_TS01")
    public void AddProduct_TS02() {

        RequestSpecification addProductBase = new RequestSpecBuilder().setBaseUri(endPoint).addHeader("Authorization", token).build();

        // <------>!<------>!<------>!Used form Parameters & Multipart for attaching the files <------>!<------>!<------>!<------>!
        RequestSpecification reqAddProduct = given().log().all().spec(addProductBase)
                .formParam("productName", "GooglePixel 5G")
                .formParam("productAddedBy", userID)
                .formParam("productCategory", "Mobiles")
                .formParam("productSubCategory", "Google Mobiles")
                .formParam("productPrice", "15000")
                .formParam("productDescription", "mobiles of India")
                .formParam("productFor", "Unisex")
                .multiPart("productImage", new File("C:/Users/335418/IdeaProjects/GooglePixel.jpg"));

        String addProductResponse = reqAddProduct.when().post("product/add-product")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        // <------>!<------>!<------>!<------>! Used JSON Parsing <------>!<------>!<------>!<------>!
        JsonPath js = new JsonPath(addProductResponse);
        productId = js.get("productId");
        String message = js.get("message");

        System.out.println("Product ID : " + productId + " message : " + message);
    }

    @Test(priority = 3, dependsOnMethods = "AddProduct_TS02")
    public void CreateOrder() {
        RequestSpecification createOrderBase = new RequestSpecBuilder().setBaseUri(endPoint)
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        // <------>!<------>!<------>!<------>! Used dynamic json payload concept <------>!<------>!<------>!<------>!

        RequestSpecification reqCreateOrder = given().log().all().spec(createOrderBase).body(JsonPayloads.createOrderPayload(productId));

        String responseCreateOrder = reqCreateOrder.when().post("order/create-order")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        // <------>!<------>!<------>!<------>! Used Json Converter class for conversion of Response to String<------>!<------>!<------>!<------>!
        JsonPath js = ReUsablesMethods.jsonConverter(responseCreateOrder);
        orderID = js.get("orders");
        String messageCreateOrder = js.getString("message");
        ArrayList<String> actualProductID = js.get("productOrderId");

        System.out.println("Message from Create Order API : " + messageCreateOrder);
        Assert.assertEquals(actualProductID.getFirst(), productId);
    }

    @Test(priority = 4, dependsOnMethods = "AddProduct_TS02")
    public void DeleteProduct() {

        RequestSpecification deleteProductBase = new RequestSpecBuilder().setBaseUri(endPoint)
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBase).pathParam("productId", productId);

        // <------>!<------>! Apply Path Parameter for product ID <------>!<------>!
        String deleteProductRes = deleteProductReq.when().delete("product/delete-product/{productId}")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(deleteProductRes);
        System.out.println(js.getString("message"));
        Assert.assertEquals(js.getString("message"), "Product Deleted Successfully");
    }

    @Test(priority = 5)
    public void ViewOrder() {

        RequestSpecification viewOrderBase = new RequestSpecBuilder().setBaseUri(endPoint)
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        // <------>!<------>! Apply Query Parameters !<------>!<------>!
        RequestSpecification viewOrderReq = given().log().all().spec(viewOrderBase).queryParam("id", orderID.getFirst());

        ViewOrderResponse viewOrderResponse = viewOrderReq.when().get("order/get-orders-details")
                .then().log().all().assertThat().statusCode(200).extract().response().as(ViewOrderResponse.class);

        ViewOrderData viewOrderData = viewOrderResponse.getData();


        System.out.println(viewOrderData.getId());
        Assert.assertEquals(viewOrderResponse.getMessage(), "Orders fetched for customer Successfully");
        Assert.assertEquals(viewOrderData.getId(), orderID.getFirst());
        Assert.assertEquals(viewOrderData.getOrderBy(), userEmail);
        Assert.assertEquals(viewOrderData.getOrderById(), userID);
    }

    @Test(priority = 6, dependsOnMethods = "CreateOrder")
    public void DeleteOrder() {

        RequestSpecification deleteOrderBase = new RequestSpecBuilder().setBaseUri(endPoint)
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        RequestSpecification deleteOrderReq = given().log().all().spec(deleteOrderBase).pathParam("OrderId", orderID.getFirst());

        // <------>!<------>! Apply Path Parameter for product ID <------>!<------>!
        String deleteOrderRes = deleteOrderReq.when().delete("order/delete-order/{OrderId}")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsablesMethods.jsonConverter(deleteOrderRes);
        System.out.println(js.getString("message"));
        Assert.assertEquals(js.getString("message"), "Orders Deleted Successfully");

    }

}


