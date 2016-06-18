package com.roquahacks.refuel;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roquahacks.model.Station;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Studio on 15.06.2016.
 */
public class StationRankAdapter extends RecyclerView.Adapter<StationItemViewHolder>{

    private List<Station> stations;
    private Context context;

    public StationRankAdapter(List<Station> stations, Context context) {
        this.stations = stations;
        this.context = context;
    }

    @Override
    public StationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_item_layout, parent, false);
        StationItemViewHolder holder = new StationItemViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(StationItemViewHolder holder, int position) {
        Station s = this.stations.get(position);
        holder.getTextView_title().setText(String.format(String.valueOf(this.context.getText(R.string.stationRankTitle)), (position + 1), s.getBrand()));
        holder.getTextView_distance().setText(String.format(String.valueOf(this.context.getText(R.string.distance)), s.getDist()));
        //TODO manage hard coded strings properly
        if(s.isOpen()) {
            holder.getTextView_isOpen().setText(this.context.getText(R.string.opened));
            holder.getTextView_isOpen().setTextColor(ContextCompat.getColor(this.context, R.color.opened));
        } else {
            holder.getTextView_isOpen().setText(this.context.getText(R.string.closed));
            holder.getTextView_isOpen().setTextColor(ContextCompat.getColor(this.context, R.color.closed));
        }
//        Picasso.with(this.context)
//                .load(R.drawable.aral)
//                .into(holder.getImageView_logo());
        holder.getImageView_logo().setImageDrawable(this.context.getDrawable(R.drawable.aral));
        holder.getTextView_address().setText(String.format(String.valueOf(this.context.getText(R.string.address)), s.getName(), s.getStreet(), s.getHouseNumber(), s.getPostCode()));
        holder.getTextView_priceE5().setText(String.format(String.valueOf(this.context.getText(R.string.priceE5)), s.getPriceE5()));
        holder.getTextView_priceE10().setText(String.format(String.valueOf(this.context.getText(R.string.priceE10)), s.getPriceE10()));
        holder.getTextView_priceDiesel().setText(String.format(String.valueOf(this.context.getText(R.string.priceDiesel)), s.getPriceDiesel()));
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }
}

