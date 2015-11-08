package ru.vlad805.guap.schedule.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.vlad805.guap.schedule.R;

/**
 * Created by arktic on 08.11.15.
 */
public abstract class BaseActivity extends AppCompatActivity{

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @LayoutRes
    protected abstract int getContentView();
}
