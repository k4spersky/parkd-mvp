package com.java.user.parkd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

/*
Adapter to handle RentActivity
 */

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.ViewHolder> {
    private Context context;
    private List<RentData> my_data;

    public RentAdapter(Context context, List<RentData> my_data) {
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
        DecimalFormat df = new DecimalFormat("0.00##");
        String result = df.format(my_data.get(position).getPrice());

        holder.address.setText(my_data.get(position).getAddress());
        holder.post.setText(my_data.get(position).getPostcode());
        holder.type.setText(my_data.get(position).getType() + " Car Park");
        Glide.with(context).load(my_data.get(position).getImage_source()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView type;
        public TextView address;
        public TextView post;
        public Switch show;
        //Think you will need to add price
        //TODO
        public ViewHolder(View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.space_address);
            imageView = (ImageView) itemView.findViewById(R.id.space_image);
            post= (TextView) itemView.findViewById(R.id.space_postcode);
            type = (TextView) itemView.findViewById(R.id.space_type);


            //TODO

        }
    }
}
