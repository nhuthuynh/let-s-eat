package au.edu.csu.itc209.letseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import au.edu.csu.itc209.letseat.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void showLoading() {
        if(mProgressBar == null) {
            mProgressBar = new ProgressBar(this);
            mProgressBar.setIndeterminate(true);
        }
    }

    protected void hideLoading() {
        if(mProgressBar != null) {
            mProgressBar.setIndeterminate(false);
        }
    }
}
