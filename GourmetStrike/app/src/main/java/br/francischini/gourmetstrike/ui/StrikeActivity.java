package br.francischini.gourmetstrike.ui;

import android.os.Bundle;

import br.francischini.gourmetstrike.R;
import butterknife.ButterKnife;

/**
 * Created by gabriel on 11/24/15.
 */
public class StrikeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_strike);

        ButterKnife.bind(this);
    }
}
