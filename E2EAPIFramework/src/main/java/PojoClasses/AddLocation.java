package PojoClasses;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocation {

    @JsonProperty("lat") // Serializes as "lat"
    @JsonAlias("latitude") // Deserializes "lat" OR "latitude"
    private double lat;

    @JsonProperty("lng") // Serializes as "lng"
    @JsonAlias("longitude") // Deserializes "lng" OR "longitude"
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}