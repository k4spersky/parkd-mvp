package com.java.user.parkd;

/**
 * Created by Paul on 17/04/2017.
 */

public class CardDetailsData {
    public CardDetailsData(String card_type, String digits, String url) {

        this.type = card_type;
        this.digits = digits;
        this.url = url;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    private String type;
    private String digits;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
