package br.francischini.gourmetstrike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.francischini.gourmetstrike.R;
import br.francischini.model.Strike;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gabriel on 11/24/15.
 */
public class StrikeActivity extends BaseActivity {
    Strike strike;

    @Bind(R.id.wordForFoodTextView)
    TextView wordForFoodTextView;

    @Bind(R.id.phraseTextView)
    TextView phraseTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_strike);

        ButterKnife.bind(this);

        strike = new Strike(this);

        generatePhrase();
    }

    public void generatePhrase() {
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
    }


}
