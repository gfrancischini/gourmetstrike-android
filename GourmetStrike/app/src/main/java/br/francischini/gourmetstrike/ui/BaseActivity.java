package br.francischini.gourmetstrike.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final String LANGUAGE_PREFS = "LANGUAGE";

    /**
     * Current app language
     */
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.language = loadLanguage();
    }

    /**
     * changeLanguageHack("pt", "BR");
     * @param language your language
     * @param country  your country
     */
    public void changeLanguageHack(String language, String country) {
        Locale locale = new Locale(language, country);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    /**
     * Attach the calligraphy library (new fonts)
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * @return the language from shared pref
     */
    protected String loadLanguage() {
        String language = Prefs.getString(LANGUAGE_PREFS, "en");
        return language;
    }

    /**
     *
     * @return get the current language
     */
    protected String getCurrentLanguage() {
        return language;
    }
}
