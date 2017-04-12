package com.java.user.parkd;

/**
 * Created by Paul on 11/04/2017.
 */

public class ActiveBookingsData {
    private String date;
    private String image_source;
    private String address;
    private String postcode;
    private String endtime;
    private String starttime;
    private Double price;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public ActiveBookingsData(String date, String image_source, String address, String postcode, Double price, String starttime, String endtime, String type) {

        this.date = date;
        this.image_source = image_source;
        this.address = address;
        this.postcode = postcode;
        this.endtime = endtime;
        this.price = price;
        this.starttime = starttime;
        this.type = type;
    }
}
