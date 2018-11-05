package com.mh.android.photoviewer.ui;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mh.android.photoviewer.R;

/**
 * Base activity foa handle common functionality
 * Created by @author Mubarak Hussain.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = BaseActivity.class.getSimpleName();
    protected Resources mResources;
    protected FragmentManager mFragmentManager;
    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    private ProgressDialog mProgressDialog;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);

        mResources = getResources();
        mFragmentManager = getSupportFragmentManager();

        setupActionBar();

        txtError =  findViewById(R.id.textError);

    }


    protected void showErrorText(int stringId){
        txtError.setText(getString(stringId));
       txtError.setVisibility(View.VISIBLE);
    }
    protected void hideErrorText(){
        txtError.setVisibility(View.GONE);
    }

    /**
     * Method to enable disable home button
     *
     * @param isEnabled
     */
    protected void setHomeButtonEnabled(boolean isEnabled) {
        if (mActionBar != null) {
            if (isEnabled) {
                mActionBar.setHomeButtonEnabled(true);
            } else {
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * Method to setup action bar
     */
    protected void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setTitle(mResources.getString(R.string.app_name));
            }
        }
    }

    /**
     * Method to set toolbar title
     *
     * @param title
     */
    protected void setToolbarTitle(String title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    /**
     * Method to add layout with extended activity
     *
     * @param layoutId
     */
    protected void addLayout(int layoutId) {
        try {
            FrameLayout mainContainer = (FrameLayout) findViewById(R.id.main_container_layout);
            if (mainContainer != null) {
                mainContainer.addView(getLayoutInflater().inflate(layoutId, null));
            }
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
    }

    /**
     * Method to show progress bar
     *
     * @param title
     * @param message
     */
    protected void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    /**
     * Method hide progress bar
     */
    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

}
