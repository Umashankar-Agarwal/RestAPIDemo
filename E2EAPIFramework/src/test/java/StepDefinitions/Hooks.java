package StepDefinitions;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Hooks {

    @Before("@deletePlace")
    public void beforeScenario() throws IOException {

        AddPlaceStep m = new AddPlaceStep();
        m.baseURIConfig();

        if (AddPlaceStep.extractedId == null) {
            Map<String, String> randomMap = new HashMap<>();
            randomMap.put("lat", String.format("%.6f", ThreadLocalRandom.current().nextDouble(-90.0, 90.0)));
            randomMap.put("lng", String.format("%.6f", ThreadLocalRandom.current().nextDouble(-180.0, 180.0)));
            randomMap.put("accuracy", String.valueOf(ThreadLocalRandom.current().nextInt(10, 100)));
            randomMap.put("name", "Place-" + UUID.randomUUID().toString().substring(0, 8));
            randomMap.put("phone_number", "(+91) 9876543210");
            randomMap.put("address", "Random St 123");
            randomMap.put("types", "shoe_store,shop");
            randomMap.put("website", "https://test.com");
            randomMap.put("language", "en-IN");

            // Call the helper directly, bypassing the DataTable entirely!
            m.applyPayload(randomMap);

            // 7. sending the Post Request
            m.sendingHTTPRequest("POST" ,"addPlaceAPI");

            // 8. getting the place ID
            m.getPlaceID("place_id");
        }

    }
}
