package com.java.user.parkd;

/**
Retrieving CarPark object from Firebase db
 */

public class CarParkData {

    private String mTitle;
    private String mSponsor;
    private String mAddress;
    private String mDesc;
    private String mLocation;
    private String mOwner;
    private String mPostcode;
    private String mCity;

    // default parameters constructor required for Firebase for data retrieval
    public CarParkData() {

    }
    // constructor
    public CarParkData(String title, String sponsor, Object schedule, Object pricing, int parking_capacity,
                       String owner, String location, String image_url, String description, String city_picker,
                       String address1, String city, String postCode) {

        mTitle = title;
        mSponsor = sponsor;
        mAddress = address1;
        mDesc = description;
        mLocation = location;
        mOwner = owner;
        mCity = city;
        mPostcode = postCode;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSponsor() {
        return mSponsor;
    }

    public void setSponsor(String sponsor) {
        mSponsor = sponsor;
    }

    public String getAddress1() {
        return mAddress;
    }

    public void setAddress1(String address1) {
        mAddress = address1;
    }

    public String getDescription() {
        return mDesc;
    }

    public void setDescription(String description) {
        mDesc = description;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mDesc = location;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getPostCode() {
        return mPostcode;
    }

    public void setPostCode(String postCode) {
        mPostcode = postCode;
    }
}
