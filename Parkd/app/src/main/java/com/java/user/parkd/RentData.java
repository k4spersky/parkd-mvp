package com.java.user.parkd;

import android.content.Intent;

/**
 * Created by Paul on 03/05/2017.
 */

public class RentData {
    private String image_source;
    private String address;
    private String postcode;
    private String desc;
    private Double price;
    private String show;
    private String type;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }



    public RentData(String image_source, String address, String postcode, Double price, String desc, String show, String type) {


        this.image_source = image_source;
        this.address = address;
        this.desc = desc;
        this.postcode = postcode;
        this.price = price;
        this.type = type;
        this.show = show;
    }
}
