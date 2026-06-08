import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class graphQlProject {


    String characterName = "Rama Krishna";
    String locationName = "Delhi";
    String episodeName = "Indian Mans - 1";
    int locationId;
    int characterId;
    int episodeId;

    @Test
    public void graphQL_Mutation() {
        RestAssured.useRelaxedHTTPSValidation();
        String MutationResponse = given().log().all().header("content-type", "application/json")
                .body(PayloadGraphQL.mutationPayload(characterName, locationName, episodeName))
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println("Mutation Response :: " + MutationResponse);

        JsonPath js = JsonCoverter.jsonConverter(MutationResponse);

        characterId = js.getInt("data.createCharacter.id");
        locationId = js.getInt("data.createLocation.id");
        episodeId = js.getInt("data.createEpisode.id");

        System.out.println(("Location ID ::  " + locationId + " Character ID ::  " + characterId + " Episode ID ::  " + episodeId));


    }

    @Test()
    public void graphQL_Query() {

        String Response = given().log().all().header("content-type", "application/json")
                .body(PayloadGraphQL.queryPayload(characterId, locationId, episodeId))
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        JsonPath js = JsonCoverter.jsonConverter(Response);

        String actualCharacterName = js.get("data.character.name");
        String actualLocationName = js.get("data.location.name");
        String actualEpisodeName = js.get("data.episode.name");
        int actualCharacterId = js.getInt("data.character.id");
        int actualLocationId = js.getInt("data.location.id");
        int actualEpisodeId = js.getInt("data.episode.id");

        System.out.println("Query Response :: " + Response);
        System.out.println(("Location ID ::  " + actualLocationId + " Character ID ::  " + actualCharacterId + " Episode ID ::  " + actualEpisodeId));


        Assert.assertEquals(actualCharacterName, characterName);
        Assert.assertEquals(actualLocationName, locationName);
        Assert.assertEquals(actualEpisodeName, episodeName);
        Assert.assertEquals(actualCharacterId, characterId);
        Assert.assertEquals(actualLocationId, locationId);
        Assert.assertEquals(actualEpisodeId, episodeId);

    }
}
