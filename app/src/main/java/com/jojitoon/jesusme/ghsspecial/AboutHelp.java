package com.jojitoon.jesusme.ghsspecial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

public class AboutHelp extends AppCompatActivity {
    int pos;

    public static final String TAG="THEMES";
    private boolean isLight;


    private int currentTheme;
    private int oldTheme;
    private int currentColor;
    private int oldColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String lister = sharedPref.getString("themeChange", "1");
        oldTheme = Integer.parseInt(lister);
        String listercolor = sharedPref.getString("themeColor", "1");
        oldColor = Integer.parseInt(listercolor);
        toggleTheme();

        Intent p = getIntent();
        pos = p.getExtras().getInt("callno");

        if (pos == 1){
            setContentView(R.layout.activity_help);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Help");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else{
            setContentView(R.layout.activity_about);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("About");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggleTheme();

    }



    private void toggleTheme() {

        // Following options to change the Theme must precede setContentView().

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String lister = sharedPref.getString("themeChange", "1");
        String listercolor = sharedPref.getString("themeColor", "1");

        currentTheme = Integer.parseInt(lister);
        currentColor = Integer.parseInt(listercolor);

//       light themes
        if (currentTheme == 1 && currentColor == 1) {
            setTheme(R.style.AppTheme_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 2) {
            setTheme(R.style.FireOrange_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 3) {
            setTheme(R.style.SkyBlue_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 4) {
            setTheme(R.style.PurpleGrapes_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 5) {
            setTheme(R.style.MintBlue_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 6) {
            setTheme(R.style.SweetPink_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 7) {
            setTheme(R.style.GreenApple_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 8) {
            setTheme(R.style.LakeTeal_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 9) {
            setTheme(R.style.HeavyGreen_NoActionBar);
        } else if (currentTheme == 1 && currentColor == 10) {
            setTheme(R.style.DarkL_AppTheme_NoActionBar);
//            dark themes
        } else if (currentTheme == 2 && currentColor == 1) {
            setTheme(R.style.AppThemeDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 2) {
            setTheme(R.style.FireOrangeDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 3) {
            setTheme(R.style.SkyBlueDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 4) {
            setTheme(R.style.PurpleGrapesDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 5) {
            setTheme(R.style.MintBlueDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 6) {
            setTheme(R.style.SweetPinkDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 7) {
            setTheme(R.style.GreenAppleDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 8) {
            setTheme(R.style.LakeTealDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 9) {
            setTheme(R.style.HeavyGreenDark_NoActionBar);
        } else if (currentTheme == 2 && currentColor == 10) {
            setTheme(R.style.Dark_AppTheme_NoActionBar);
        }


        if (oldTheme != currentTheme || oldColor != currentColor) {

            oldTheme = currentTheme;
            oldColor = currentColor;

            Intent k = new Intent(this, MainActivity.class);

            // Following flag clears the activity with old theme from the stack so an exit from the
            // activity with new theme will not take you back to the version with the old theme.

            k.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(k);
        }

    }


}
