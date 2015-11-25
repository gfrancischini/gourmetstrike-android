package br.francischini.gourmetstrike.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import br.francischini.gourmetstrike.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gabriel on 11/24/15.
 */
public class AboutActivity extends BaseActivity {
    @Bind(R.id.closeImageButton)
    ImageButton closeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
    }

    /**
     * On click close button we will finish this activity
     *
     * @param view The view clicked
     */
    @OnClick(R.id.closeImageButton)
    public void closeImageButtonOnClick(View view) {
        this.finish();
    }

}
