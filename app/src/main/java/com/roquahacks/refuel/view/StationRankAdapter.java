package com.roquahacks.refuel.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roquahacks.model.station.Station;
import com.roquahacks.refuel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Studio on 15.06.2016.
 */
public class StationRankAdapter extends RecyclerView.Adapter<StationRankAdapter.StationItemViewHolder>{

    private List<Station> mStations;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public StationRankAdapter(List<Station> stations, Context context) {
        this.mStations = stations;
        this.mContext = context;
    }

    @Override
    public StationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_item_layout, parent, false);
        StationItemViewHolder holder = new StationItemViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StationItemViewHolder holder, int position) {
        final Station s = mStations.get(position);
        final SpannableStringBuilder str = new SpannableStringBuilder(String.format(mContext.getString(R.string.station_name),
                s.getRank(), s.getBrand()));
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.textViewBrand.setText(str);
        holder.textViewDistance.setText(String.format(mContext.getString(R.string.distance), s.getDist()));
        final int backgroundID = s.getBackgroundID();
        Picasso.with(mContext)
                .load(backgroundID)
                .into(holder.imageViewBackground);
        if(s.isOpen()) {
            holder.textViewOpenClosed.setTextColor(ContextCompat.getColor(mContext, R.color.opened));
            holder.textViewOpenClosed.setText(mContext.getString(R.string.opened));
        } else {
            holder.textViewOpenClosed.setTextColor(ContextCompat.getColor(mContext, R.color.closed));
            holder.textViewOpenClosed.setText(mContext.getString(R.string.closed));
        }
//        Bitmap backgroundImage = BitmapFactory.decodeResource(mContext.getResources(), backgroundID);
//        Palette.generateAsync(backgroundImage, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                int bgColor = palette.getLightVibrantColor(mContext.getResources().getColor(android.R.color.black));
//                holder.placeNameHolder.setBackgroundColor(bgColor);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    public class StationItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView textViewBrand;
        public TextView textViewDistance;
        private TextView textViewOpenClosed;
        public ImageView imageViewBackground;
        public ImageView imageViewLogo;

        public StationItemViewHolder(View itemView) {
            super(itemView);
            this.placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            this.placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            this.textViewBrand = (TextView) itemView.findViewById(R.id.textView_brand);
            this.textViewDistance = (TextView) itemView.findViewById(R.id.textView_distance);
            this.textViewOpenClosed = (TextView) itemView.findViewById(R.id.textView_open_closed);
            this.imageViewBackground = (ImageView) itemView.findViewById(R.id.imageView_background);
            this.imageViewLogo = (ImageView) itemView.findViewById(R.id.imageView_logo);
            this.placeHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mItemClickListener != null) {
                Toast.makeText(mContext, "Position", Toast.LENGTH_SHORT);
                mItemClickListener.onItemClick(itemView, mStations.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, Station station);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}

