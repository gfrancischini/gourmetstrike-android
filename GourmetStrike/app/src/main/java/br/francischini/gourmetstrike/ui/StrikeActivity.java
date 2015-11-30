package br.francischini.gourmetstrike.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;

import br.francischini.gourmetstrike.R;
import br.francischini.gourmetstrike.model.Strike;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
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

            if(buttonView == enButton && isChecked == true) {
                changeTextLanguage("en");
            }
            else if (buttonView == ptButton && isChecked == true){
                changeTextLanguage("pt");
            }
        }
    };

    private void changeTextLanguage(String language) {
        strike = new Strike(language, this);
        generatePhrase();
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, getCurrentText());
        sendIntent.setType("text/plain");
        // share directly to WhatsApp and bypass the system picker, you can do so by using setPackage in your intent:
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
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
