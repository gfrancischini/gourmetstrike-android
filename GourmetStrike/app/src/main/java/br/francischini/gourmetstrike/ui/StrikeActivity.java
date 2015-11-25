package br.francischini.gourmetstrike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import br.francischini.gourmetstrike.R;
import br.francischini.gourmetstrike.model.Strike;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by gabriel on 11/24/15.
 */
public class StrikeActivity extends BaseActivity {
    Strike strike;

    @Bind(R.id.wordForFoodTextView)
    TextView wordForFoodTextView;

    @Bind(R.id.phraseTextView)
    TextView phraseTextView;

    @Bind(R.id.armImageView)
    ImageView armImageView;

    @Bind(R.id.enButton)
    RadioButton enButton;

    @Bind(R.id.ptButton)
    RadioButton ptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_strike);

        ButterKnife.bind(this);

        strike = new Strike("en", this);

        generatePhrase();


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        armImageView.startAnimation(animation);


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
        strike = new Strike(language, this);
    }

    private void generatePhrase() {
        wordForFoodTextView.setText(strike.getWordForFood());
        phraseTextView.setText(strike.getPhrase());
    }

    /**
     * On click new phrase button we will generate a new phrase
     *
     * @param view The view clicked
     */
    @OnClick(R.id.newPhraseButton)
    public void newPhraseButtonOnClick(View view) {
        generatePhrase();
        playShakeAnimation(phraseTextView);
    }

    /**
     * On click new phrase button we will generate a new phrase
     *
     * @param view The view clicked
     */
    @OnClick(R.id.whatsappButton)
    public void whatsappButtonOnClick(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, wordForFoodTextView.getText() + " " + phraseTextView.getText());
        sendIntent.setType("text/plain");
        // share directly to WhatsApp and bypass the system picker, you can do so by using setPackage in your intent:
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
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

    /**
     * Play Shake Animation
     * @param v
     *      View element to be shaken
     */
    private void playShakeAnimation(View v) {
        // Create shake effect from xml resource
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        // Perform animation
        v.startAnimation(shake);
    }

}
