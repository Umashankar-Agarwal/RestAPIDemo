package files;

public class payloads {

    public static String addPlace(){

        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Test Basics - Rest API Automation \",\n" +
                "  \"phone_number\": \"(+91) 984 893 1232\",\n" +
                "  \"address\": \"Learning API Automation\",\n" +
                "  \"types\": [\n" +
                "    \"Residential\",\n" +
                "    \"commercial\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"English\"\n" +
                "}";
    }

    public static String putPlace(String placeID, String newAddress){

        return "{\n" +
                "\"place_id\":\""+placeID+"\",\n" +
                "\"address\":\""+newAddress+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}\n";
    }
}
