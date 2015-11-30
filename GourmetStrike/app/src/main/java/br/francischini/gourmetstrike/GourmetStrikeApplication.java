package br.francischini.gourmetstrike;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by gabriel on 11/24/15.
 */
public class GourmetStrikeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the calligraphy class
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("ProximaNova-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


    }
}