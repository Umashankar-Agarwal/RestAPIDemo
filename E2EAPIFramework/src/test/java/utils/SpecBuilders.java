package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class SpecBuilders {

    protected static final String key = "key";
    protected static final String value = "qaclick123";

    // !<----->!!<----->! Applying the get value of BaseURL from the Global Properties file  !<----->!!<----->!
    public static String getGlobalValue(String key) throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/utils/global.properties");
        prop.load(fis);
        return prop.get(key).toString();
    }

    // !<----->!!<----->! Request Specification Builders to provide the common Request architecture !<----->!!<----->!
    public static RequestSpecification requestSpec() throws IOException {

        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        return new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addQueryParam(key, value)
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .build();
    }

    // !<----->!!<----->! Grouping the common Response !<----->!!<----->!
    public static ResponseSpecification responseSpec (){
        return  new ResponseSpecBuilder().expectStatusCode(200).build();

    }
}
