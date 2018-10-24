package com.map.hack.api.v1.type;

public class Location {
    private double latitude;
    private double longitude;
    private String city;
    private String country;
    private String isoCode;

    public Location(double latitude, double longitude, String city, String country, String isoCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
        this.isoCode = isoCode;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
