package enums;

public enum APIResources {

    // !<----->!!<-----> !<----->!!<-----> enums are the special classes that contains the collection of Constants or Methods
    AddPlaceAPI("maps/api/place/add/json"),
    getPlaceAPI("maps/api/place/get/json"),
    putPlaceAPI("maps/api/place/update/json"),
    deletePlaceAPI("maps/api/place/delete/json");

    private  final String resource;
    APIResources (String resource) {
        this.resource = resource;
    }

    public String getResource(){
        return resource;
    }
}


