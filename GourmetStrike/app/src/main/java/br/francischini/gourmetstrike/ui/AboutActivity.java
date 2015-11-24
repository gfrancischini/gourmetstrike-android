package br.francischini.gourmetstrike.ui;

import android.os.Bundle;

import br.francischini.gourmetstrike.R;
import butterknife.ButterKnife;

/**
 * Created by gabriel on 11/24/15.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
    }
}
