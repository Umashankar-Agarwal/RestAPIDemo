package utils;

import PojoClasses.AddLocation;
import PojoClasses.AddPlace;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestData {

    // !<----->!!<----->! Serialization :  Wrapped the method to create the request Payload seperately   !<----->!!<----->!

    public static AddPlace addPlacePayload(Map<String, String> row){

        AddLocation addLocation = new AddLocation();
        addLocation.setLat(Double.parseDouble(row.get("lat")));
        addLocation.setLng(Double.parseDouble(row.get("lng")));

        AddPlace addPlace = new AddPlace();
        addPlace.setLocation(addLocation);
        addPlace.setAccuracy(Integer.parseInt(row.get("accuracy")));
        addPlace.setAddress(row.get("address"));
        addPlace.setLanguage(row.get("language"));
        addPlace.setName(row.get("name"));
        addPlace.setPhone_number(row.get("phone_number"));
        addPlace.setWebsite(row.get("website"));
        List<String> typesList = Arrays.asList(row.get("types").split(","));
        addPlace.setTypes(typesList);

        return addPlace;
    }

}
