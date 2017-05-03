package com.java.user.parkd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
Adapter to handle RentActivity
 */

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.ViewHolder> {
    private Context context;
    private List<CardDetailsData> my_data;

    public RentAdapter(Context context, List<CardDetailsData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public RentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rent_spaces_cardview,
                parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RentAdapter.ViewHolder holder, int position) {
        //TODO

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        //TODO
        public ViewHolder(View itemView) {
            super(itemView);

            //TODO

        }
    }
}
