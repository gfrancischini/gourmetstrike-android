package br.francischini.gourmetstrike;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by gabriel on 11/24/15.
 */
public class GourmetStrikeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("ProximaNova-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }
}