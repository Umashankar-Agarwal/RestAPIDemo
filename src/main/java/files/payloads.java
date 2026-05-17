package files;

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
}
