package com.java.user.parkd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import java.util.List;
/**
 * Created by Paul on 17/04/2017.
 */

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {
    private Context context;
    private List<CardDetailsData> my_data;

    public PaymentsAdapter(Context context, List<CardDetailsData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public PaymentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payments_cardview,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentsAdapter.ViewHolder holder, int position) {

        holder.card_type.setText(my_data.get(position).getType());
        holder.last_digits.setText("Ending in " + my_data.get(position).getDigits());
        Glide.with(context).load(my_data.get(position).getUrl()).into(holder.card_image);

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView card_type;
        public TextView last_digits;
        public ImageView card_image;


        public ViewHolder(View itemView) {
            super(itemView);
            card_type = (TextView) itemView.findViewById(R.id.cardType);
            card_image = (ImageView) itemView.findViewById(R.id.cardIcon);
            last_digits = (TextView) itemView.findViewById(R.id.cardDigits);

        }
    }
}
