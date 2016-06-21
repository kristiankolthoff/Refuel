package com.roquahacks.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.roquahacks.refuel.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kolti on 20.06.2016.
 */
public class LogoHolder {

    private static  Map<String, Integer> drawablesLogo;
    private static Map<String, Integer> drawablesStation;

    static {
        drawablesLogo = new HashMap<>();
        drawablesLogo.put("aral", R.drawable.aral);
        drawablesLogo.put("agip", R.drawable.agip);
        drawablesLogo.put("avia", R.drawable.avia);
        drawablesLogo.put("bft", R.drawable.bft);
        drawablesLogo.put("esso", R.drawable.esso);
        drawablesLogo.put("freietankstelle", R.drawable.freietankstelle);
        drawablesLogo.put("hem", R.drawable.hem);
        drawablesLogo.put("jet", R.drawable.jet);
        drawablesLogo.put("mtb", R.drawable.mtb);
        drawablesLogo.put("omv", R.drawable.omv);
        drawablesLogo.put("sb", R.drawable.sbtank);
        drawablesLogo.put("shell", R.drawable.shell);
        drawablesLogo.put("tankpoint", R.drawable.tankpoint);
        drawablesLogo.put("total", R.drawable.total);

        drawablesStation = new HashMap<>();
        drawablesStation.put("aral", R.drawable.aral_station);
        drawablesStation.put("agip", R.drawable.agip_station);
        drawablesStation.put("avia", R.drawable.avia_station);
        drawablesStation.put("bft", R.drawable.bft_station);
        drawablesStation.put("esso", R.drawable.esso_station);
        drawablesStation.put("freietankstelle", R.drawable.freietankstelle_station);
        drawablesStation.put("hem", R.drawable.hem_station);
        drawablesStation.put("jet", R.drawable.jet_station);
        drawablesStation.put("mtb", R.drawable.mtb_station);
        drawablesStation.put("omv", R.drawable.omv_station);
        drawablesStation.put("sb", R.drawable.sbtank_station);
        drawablesStation.put("shell", R.drawable.shell_station);
        drawablesStation.put("total", R.drawable.total_station);
    }

    public static Drawable getLogo(String brand, Context context) {
        final Drawable PLACEHOLDER = context.getDrawable(R.drawable.ic_menu_slideshow);
        if(brand == null) {
            return PLACEHOLDER;
        } else {
            for(String key : drawablesLogo.keySet()) {
                String brandSanitized = brand.toLowerCase().replace(" ", "");
                if(brandSanitized.contains(key)) {
                    return context.getDrawable(drawablesLogo.get(key));
                }
            }
        }
        return PLACEHOLDER;
    }

    public static int getLogoID(String brand) {
        final int PLACEHOLDER = R.drawable.station;
        if(brand == null) {
            return PLACEHOLDER;
        } else {
            for(String key : drawablesLogo.keySet()) {
                String brandSanitized = brand.toLowerCase().replace(" ", "");
                if(brandSanitized.contains(key)) {
                    return drawablesLogo.get(key);
                }
            }
        }
        return PLACEHOLDER;
    }

    public static int getStationBackgroundID(String brand) {
        final int PLACEHOLDER = R.drawable.default_station;
        if(brand == null) {
            return PLACEHOLDER;
        } else {
            for(String key : drawablesStation.keySet()) {
                String brandSanitized = brand.toLowerCase().replace(" ", "");
                if(brandSanitized.contains(key)) {
                    return drawablesStation.get(key);
                }
            }
        }
        return PLACEHOLDER;
    }
}
