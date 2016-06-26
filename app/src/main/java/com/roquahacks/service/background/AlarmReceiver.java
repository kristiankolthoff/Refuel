package com.roquahacks.service.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.roquahacks.refuel.Application;

/**
 * Created by Kolti on 24.06.2016.
 */
public class AlarmReceiver extends BroadcastReceiver{

    public static final int UPDATE_FREQ = 3600000;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show();
        Intent updaterIntent = new Intent(context, PriceUpdateService.class);
        context.startService(updaterIntent);
    }
}
