package br.francischini.gourmetstrike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import br.francischini.gourmetstrike.R;
import br.francischini.gourmetstrike.model.Strike;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by gabriel on 11/24/15.
 */
public class LaunchScreenActivity extends BaseActivity {
    @Bind(R.id.strikeButton)
    Button strikeButton;

    @Bind(R.id.enButton)
    RadioButton enButton;

    @Bind(R.id.ptButton)
    RadioButton ptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch_screen);

        // user butter knife bind
        ButterKnife.bind(this);


        enButton.setOnCheckedChangeListener(onCheckedChangeListener);
        ptButton.setOnCheckedChangeListener(onCheckedChangeListener);
        enButton.setChecked(true);
        onCheckedChangeListener.onCheckedChanged(enButton, true);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String font = "ProximaNova-Regular.ttf";
            if (isChecked) {
                font = "ProximaNova-Bold.ttf";
            }
            buttonView.setTypeface(TypefaceUtils.load(getAssets(), font));

            changeTextLanguage("en");
        }
    };

    private void changeTextLanguage(String language) {
        // save to preferences
        //strike = new Strike(language, this);
    }

    /**
     * Setup view (if needed)
     */
    private void setupView() {

    }

    /**
     * On click strike button we will navigate to next screen
     *
     * @param view The view clicked
     */
    @OnClick(R.id.strikeButton)
    public void strikeButtonOnClick(View view) {
        Intent intent = new Intent(this, StrikeActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * On click about button we will navigate to about screen
     *
     * @param view The view clicked
     */
    @OnClick(R.id.aboutButton)
    public void aboutButtonOnClick(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }


}
