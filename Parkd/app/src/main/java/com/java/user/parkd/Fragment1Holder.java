package com.java.user.parkd;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 *  mapping numeric IDs and casts into a nice, type-safe contract. by kkuczkowski on 14/04/2017.
 */

public class Fragment1Holder extends RecyclerView.ViewHolder {

    protected Fragment1Holder(View itemView) {
        super(itemView);
        mTitleField = (TextView) itemView.findViewById(R.id.carpark_name);
        mAddressField = (TextView) itemView.findViewById(R.id.carpark_detail1);
        mCityField = (TextView) itemView.findViewById(R.id.carpark_city);
        mPostcodeField = (TextView) itemView.findViewById(R.id.carpark_postcode);
        mAvailableSpacesField = (TextView) itemView.findViewById(R.id.spaces);
    }

    // passing spaces view to be used in fragment1
    protected View magaView() {
        //maga
    return itemView.findViewById(R.id.spaces);
    }

    protected void setTitle(String title) {
        mTitleField.setText(title);
    }

    protected void setAddress1(String address1) {
        mAddressField.setText(address1);
    }

    protected void setCity(String city) {
        mCityField.setText(city);
    }

    protected void setPostCode(String postCode) {
        mPostcodeField.setText(postCode);
    }

    protected void setAvailableSpaces(String availableSpaces) {
        mAvailableSpacesField.setText(availableSpaces);
    }

    private final TextView mTitleField;
    private final TextView mAddressField;
    private final TextView mCityField;
    private final TextView mPostcodeField;
    private final TextView mAvailableSpacesField;
}
