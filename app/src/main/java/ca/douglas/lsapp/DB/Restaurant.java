package ca.douglas.lsapp.DB;

import android.location.Location;

import java.io.Serializable;

import ca.douglas.lsapp.Shared.Commom;

public class Restaurant implements Comparable<Restaurant>, Serializable {
    private int id;
    private String LogoUrl;
    private String phone;
    private String name;
    private double latitude;
    private double longitude;
    private float distanceFromUser;

    public Restaurant(int id, String LogoUrl, String phone, String name, double latitude, double longitude) {
        this.id = id;
        this.LogoUrl = LogoUrl;
        this.name = name;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String LogoUrl) {
        this.LogoUrl = LogoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getDistanceFromUser() {
        return distanceFromUser;
    }

    public void setDistanceFromUser(Location userLocation) {
        Location restaurantLocation = new Location("restaurant_" + id);
        restaurantLocation.setLatitude(latitude);
        restaurantLocation.setLongitude(longitude);

        //Distance is get in meters, it will be transformed to kms
        float distanceFromUserKms = userLocation.distanceTo(restaurantLocation) / 1000;
        //Round it to two decimal
        distanceFromUser = Commom.roundToDigits(distanceFromUserKms, 2);
    }

    public int compareTo(Restaurant r) {
        if (distanceFromUser > r.getDistanceFromUser())
            return 1;
        else if (distanceFromUser < r.getDistanceFromUser())
            return -1;
        else
            return 0;
    }
}
