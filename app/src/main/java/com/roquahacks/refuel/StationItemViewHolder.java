package com.roquahacks.refuel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Studio on 15.06.2016.
 */
public class StationItemViewHolder extends RecyclerView.ViewHolder{

    private TextView textView_title;
    private TextView textView_distance;
    private TextView textView_isOpen;
    private TextView textView_address;
    private TextView textView_priceE5;
    private TextView textView_priceE10;
    private TextView textView_priceDiesel;

    public StationItemViewHolder(View itemView) {
        super(itemView);
        this.textView_title = (TextView) itemView.findViewById(R.id.textView_title);
        this.textView_distance = (TextView) itemView.findViewById(R.id.textView_distance);
        this.textView_isOpen = (TextView) itemView.findViewById(R.id.textView_isOpen);
        this.textView_address = (TextView) itemView.findViewById(R.id.textView_address);
        this.textView_priceE5 = (TextView) itemView.findViewById(R.id.textView_PriceE5);
        this.textView_priceE10 = (TextView) itemView.findViewById(R.id.textView_priceE10);
        this.textView_priceDiesel = (TextView) itemView.findViewById(R.id.textView_priceDiesel);
    }

    public TextView getTextView_title() {
        return textView_title;
    }

    public void setTextView_title(TextView textView_title) {
        this.textView_title = textView_title;
    }

    public TextView getTextView_distance() {
        return textView_distance;
    }

    public void setTextView_distance(TextView textView_distance) {
        this.textView_distance = textView_distance;
    }

    public TextView getTextView_isOpen() {
        return textView_isOpen;
    }

    public void setTextView_isOpen(TextView textView_isOpen) {
        this.textView_isOpen = textView_isOpen;
    }

    public TextView getTextView_address() {
        return textView_address;
    }

    public void setTextView_address(TextView textView_address) {
        this.textView_address = textView_address;
    }

    public TextView getTextView_priceE5() {
        return textView_priceE5;
    }

    public void setTextView_priceE5(TextView textView_priceE5) {
        this.textView_priceE5 = textView_priceE5;
    }

    public TextView getTextView_priceE10() {
        return textView_priceE10;
    }

    public void setTextView_priceE10(TextView textView_priceE10) {
        this.textView_priceE10 = textView_priceE10;
    }

    public TextView getTextView_priceDiesel() {
        return textView_priceDiesel;
    }

    public void setTextView_priceDiesel(TextView textView_priceDiesel) {
        this.textView_priceDiesel = textView_priceDiesel;
    }
}
