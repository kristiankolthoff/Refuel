package com.roquahacks.refuel.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.roquahacks.model.Station;
import com.roquahacks.refuel.Application;
import com.roquahacks.refuel.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class StationDetailActivity extends AppCompatActivity {

    private Station mStation;
    private ImageView mImageViewBackground;
    private TextView mTextViewBrand;
    private TextView mTextViewDistance;
    private TextView mTextViewOpenClosed;
    private TextView mTextViewPriceE5;
    private TextView mTextViewPriceE10;
    private TextView mTextViewPriceDiesel;
    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
//        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        mStation = getIntent().getParcelableExtra(Application.STATION_INTENT_ID);

        mImageViewBackground = (ImageView) findViewById(R.id.imageView_background_detail);
        mTextViewBrand = (TextView) findViewById(R.id.textView_brand_detail);
        mTextViewDistance = (TextView) findViewById(R.id.textView_distance_detail);
        mTextViewOpenClosed = (TextView) findViewById(R.id.textView_open_closed_detail);
        mTextViewPriceE5 = (TextView) findViewById(R.id.textView_priceE5_detail);
        mTextViewPriceE10 = (TextView) findViewById(R.id.textView_priceE10_detail);
        mTextViewPriceDiesel = (TextView) findViewById(R.id.textView_priceDiesel_detail);


        Picasso.with(this)
                .load(mStation.getBackgroundID())
                .into(mImageViewBackground);
        final SpannableStringBuilder str = new SpannableStringBuilder(String.format(this.getString(R.string.station_name),
                mStation.getRank(), mStation.getBrand()));
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewBrand.setText(str);
        mTextViewDistance.setText(String.format(this.getString(R.string.distance), mStation.getDist()));
        if(mStation.isOpen()) {
            mTextViewOpenClosed.setTextColor(ContextCompat.getColor(this, R.color.opened));
            mTextViewOpenClosed.setText(this.getString(R.string.opened));
        } else {
            mTextViewOpenClosed.setTextColor(ContextCompat.getColor(this, R.color.closed));
            mTextViewOpenClosed.setText(this.getString(R.string.closed));
        }
        final SpannableStringBuilder strE5 = new SpannableStringBuilder(String.format(this.getString(R.string.priceE5),
                mStation.getPriceE5()));
        strE5.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewPriceE5.setText(strE5);

        final SpannableStringBuilder strE10 = new SpannableStringBuilder(String.format(this.getString(R.string.priceE10),
                mStation.getPriceE10()));
        strE10.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewPriceE10.setText(strE10);

        final SpannableStringBuilder strDiesel = new SpannableStringBuilder(String.format(this.getString(R.string.priceDiesel),
                mStation.getPriceDiesel()));
        strDiesel.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewPriceDiesel.setText(strDiesel);
    }

}
