package br.francischini.gourmetstrike.ui;

import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.pixplicity.easyprefs.library.Prefs;

import br.francischini.gourmetstrike.R;
import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by gabriel on 11/30/15.
 */
public abstract class LanguageActivity extends BaseActivity {
    public interface LanguageListener {
        public void onLanguageChanged(String language);
    };

    /**
     * The language listener
     */
    LanguageListener languageListener;

    @Bind(R.id.enButton)
    RadioButton enButton;

    @Bind(R.id.ptButton)
    RadioButton ptButton;

    protected void configureLanguage() {
        if (this.getCurrentLanguage().equalsIgnoreCase("en")) {
            enButton.setChecked(true);
            changeRadioButtonTypeface(enButton, true);
        } else {
            ptButton.setChecked(true);
            changeRadioButtonTypeface(ptButton, true);
        }

        enButton.setOnCheckedChangeListener(onCheckedChangeListener);
        ptButton.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            changeRadioButtonTypeface(buttonView, isChecked);

            String language = (String) buttonView.getTag();
            if (language != getCurrentLanguage() && isChecked) {
                saveLanguage(language);
            }
        }
    };

    /**
     * @param buttonView
     * @param isChecked
     */
    private void changeRadioButtonTypeface(CompoundButton buttonView, boolean isChecked) {
        String font = "ProximaNova-Regular.ttf";
        if (isChecked) {
            font = "ProximaNova-Bold.ttf";
        }
        buttonView.setTypeface(TypefaceUtils.load(getAssets(), font));
    }

    /**
     * Save the language to shared pref
     *
     * @param language the language to save
     */
    protected void saveLanguage(String language) {
        Prefs.putString(LANGUAGE_PREFS, language);
        if(languageListener != null) {
            languageListener.onLanguageChanged(language);
        }
    }

    /**
     * Set a new language listener
     * @param languageListener the language listener
     */
    protected void setLanguageListener(LanguageListener languageListener) {
        this.languageListener = languageListener;
    }

}
