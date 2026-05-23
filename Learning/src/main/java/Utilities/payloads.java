package Utilities;

public class payloads {

    public static String addPlace(){

        return """
                {
                  "location": {
                    "lat": -38.383494,
                    "lng": 33.427362
                  },
                  "accuracy": 50,
                  "name": "Test Basics - Rest API Automation ",
                  "phone_number": "(+91) 984 893 1232",
                  "address": "Learning API Automation",
                  "types": [
                    "Residential",
                    "commercial"
                  ],
                  "website": "http://google.com",
                  "language": "English"
                }""";
    }

    public static String putPlace(String placeID, String newAddress){

        return "{\n" +
                "\"place_id\":\""+placeID+"\",\n" +
                "\"address\":\""+newAddress+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}\n";
    }

    public static String mockJson(){
        return """
                {
                
                "dashboard": {
                
                "purchaseAmount": 910,
                
                "website": "rahulshettyacademy.com"
                
                },
                
                "courses": [
                
                {
                
                "title": "Selenium Python",
                
                "price": 50,
                
                "copies": 6
                
                },
                
                {
                
                "title": "Cypress",
                
                "price": 40,
                
                "copies": 4
                
                },
                
                {
                
                "title": "RPA",
                
                "price": 45,
                
                "copies": 10
                
                }
                
                ]
                
                }""";
    }

    public static String addBookLibraryPayload(String isbn , String aisle){

        return "{\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n";
    }

    public static String deleteBookPayload(String ID){
        return "{\n" +
                "\n" +
                "\"ID\" : \""+ID+"\"\n" +
                "\n" +
                "} \n";
    }

    public static String createBug(){

        return"{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       {\n" +
                "          \"key\": \"TEST\"\n" +
                "       },\n" +
                "       \"summary\": \"REST ye merry gentlemen.\",\n" +
                "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" +
                "       \"issuetype\": {\n" +
                "          \"name\": \"Bug\"\n" +
                "       }\n" +
                "   }\n" +
                "}\n";

    }
}
