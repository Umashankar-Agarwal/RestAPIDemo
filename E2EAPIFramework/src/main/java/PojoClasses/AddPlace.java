package PojoClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class AddPlace {

    private AddLocation location;
    private int accuracy;
    private String name;
    private String phone_number;
    private String address;
    private List<String> types;
    private String website;
    private String language;

    public AddLocation getLocation() {
        return location;
    }

    public void setLocation(AddLocation location) {
        this.location = location;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // Getter: Used for Serialization (Request) and your Assertion
    @JsonProperty("types")
    public List<String> getTypes() {
        return types;
    }

    // Setter 1: Standard setter used when constructing the Request payload
    public void setTypes(List<String> types) {
        this.types = types;
    }

    // Setter 2: Used for Deserialization (Response)
    // This intercepts the String, splits it, and saves it into your List
    @JsonProperty("types")
    public void setTypes(String typesString) {
        // split(",\\s*") handles both "a,b" and "a, b" safely
        this.types = Arrays.asList(typesString.split(",\\s*"));
    }
}

