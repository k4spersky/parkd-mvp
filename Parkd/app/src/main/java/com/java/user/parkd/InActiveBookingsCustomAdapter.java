package com.java.user.parkd;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Paul on 13/04/2017.
 */

public class InActiveBookingsCustomAdapter extends RecyclerView.Adapter<InActiveBookingsCustomAdapter.ViewHolder> {

    private Context context;
    private List<InActiveBookingsData> my_data;

    public InActiveBookingsCustomAdapter(Context context, List<InActiveBookingsData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public InActiveBookingsCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inactive_bookings_cardview,parent,false);

        return new InActiveBookingsCustomAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InActiveBookingsCustomAdapter.ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.00##");
        String result = df.format(my_data.get(position).getPrice());

        holder.address.setText(my_data.get(position).getAddress());
        holder.postcode.setText(my_data.get(position).getPostcode());
        holder.time.setText(my_data.get(position).getStarttime() + " - " + my_data.get(position).getEndtime());
        holder.type.setText(my_data.get(position).getType() + " Car Park");
        holder.price.setText("£" + result);
        holder.datebooked.setText(my_data.get(position).getDate());
        Glide.with(context).load(my_data.get(position).getImage_source()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView address;
        public TextView postcode;
        public TextView datebooked;
        public ImageView imageView;
        public TextView time;
        public TextView price;
        public TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.Inactivecarpark_address);
            imageView = (ImageView) itemView.findViewById(R.id.InactivespacePhoto);
            postcode = (TextView) itemView.findViewById(R.id.Inactivecarpark_postcode);
            datebooked = (TextView) itemView.findViewById(R.id.Inactivecarpark_bookeddate);
            time = (TextView) itemView.findViewById(R.id.Inactivecarpark_bookedtimes);
            price = (TextView) itemView.findViewById(R.id.Inactivecarpark_cost);
            type = (TextView) itemView.findViewById(R.id.Inactivecarpark_name);
        }
    }
}
