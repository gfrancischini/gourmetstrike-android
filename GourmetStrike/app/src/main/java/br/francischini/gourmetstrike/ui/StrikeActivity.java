package br.francischini.gourmetstrike.ui;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import br.francischini.gourmetstrike.R;
import br.francischini.gourmetstrike.model.Strike;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by gabriel on 11/24/15.
 */
public class StrikeActivity extends LanguageActivity {
    Strike strike;

    @Bind(R.id.wordForFoodTextView)
    TextView wordForFoodTextView;

    @Bind(R.id.phraseTextView)
    TextView phraseTextView;

    @Bind(R.id.armImageView)
    ImageView armImageView;

    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @Bind(R.id.copyTextView)
    TextView copyTextView;

    @Bind(R.id.bitmapFrameLayout)
    RelativeLayout bitmapFrameLayout;

    @Bind(R.id.bitmapImageView)
    ImageView bitmapImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_strike);

        ButterKnife.bind(this);

        strike = new Strike(this.getCurrentLanguage(), this);

        configureLanguage();

        generatePhrase();
        runEnterAnimation();

        this.setLanguageListener(new LanguageListener() {
            @Override
            public void onLanguageChanged(String language) {
                changeTextLanguage(language);
            }
        });
    }

    /**
     *
     */
    private void runEnterAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        armImageView.startAnimation(animation);
    }


    /**
     * @param language
     */
    private void changeTextLanguage(String language) {
        strike = new Strike(language, this);
        generatePhrase();
    }


    /**
     *
     */
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, getCurrentText());
        sendIntent.setType("text/plain");
        // share directly to WhatsApp and bypass the system picker, you can do so by using setPackage in your intent:
        sendIntent.setPackage("com.whatsapp");

        try {
            startActivity(sendIntent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(this, "Whatsapp not installed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @return the current text on screen
     */
    private String getCurrentText() {
        return wordForFoodTextView.getText() + " " + phraseTextView.getText();
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
     * On long click entire layout button we will copy the text
     *
     * @param view The view clicked
     */
    @OnLongClick(R.id.relativeLayout)
    public boolean relativeLayoutOnLongClick(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Gourmet Strike", getCurrentText());
        clipboard.setPrimaryClip(clip);

        copyTextView.setVisibility(View.VISIBLE);

        showBlurrImage();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                copyTextView.setVisibility(View.GONE);
                bitmapImageView.setVisibility(View.GONE);
            }
        }, 2000);

        return true;
    }


    /**
     * Play Shake Animation
     *
     * @param v View element to be shaken
     */
    private void playShakeAnimation(View v) {
        // Create shake effect from xml resource
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        // Perform animation
        v.startAnimation(shake);
    }

    /**
     * Compute and show the blurr image
     */
    private void showBlurrImage() {
        bitmapFrameLayout.setDrawingCacheEnabled(true);
        bitmapFrameLayout.buildDrawingCache();
        Bitmap bmp = bitmapFrameLayout.getDrawingCache();

        bitmapImageView.setVisibility(View.VISIBLE);

        //set the background image with the video frame grabbed while we do the blur transformation
        bitmapImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bitmapImageView.setImageBitmap(bmp);

        //convert the image into a byte array used by Glide
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Glide.with(this).load(byteArray).bitmapTransform(new BlurTransformation(this, 25))
                .into(bitmapImageView);

        bitmapImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

}
