package com.roquahacks.refuel.activity;

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

import com.roquahacks.model.Station;
import com.roquahacks.refuel.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class StationDetailActivity extends AppCompatActivity {

    private Station mStation;
    private ImageView mImageViewBackground;
    private TextView mTextViewBrand;
    private TextView mTextViewDistance;
    private TextView mTextViewOpenClosed;

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

        mStation = getIntent().getParcelableExtra(RefuelActivity.STATION);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mImageViewBackground = (ImageView) findViewById(R.id.imageView_background_detail);
        mTextViewBrand = (TextView) findViewById(R.id.textView_brand_detail);
        mTextViewDistance = (TextView) findViewById(R.id.textView_distance_detail);
        mTextViewOpenClosed = (TextView) findViewById(R.id.textView_open_closed_detail);

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
    }

}
