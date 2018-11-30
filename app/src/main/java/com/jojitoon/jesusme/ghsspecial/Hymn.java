package com.jojitoon.jesusme.ghsspecial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Hymn extends AppCompatActivity {

    int pos;
    Toolbar toolbar;


    public static final String TAG = "THEMES";
    private boolean isLight;
    private boolean isChecked;
    private boolean checkstate;
    private int currentTheme;
    private int oldTheme;
    private int currentColor;
    private int oldColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        isChecked = sharedPref.getBoolean("screen_on", true);
        checkstate = isChecked;
        String lister = sharedPref.getString("themeChange", "1");
        oldTheme = Integer.parseInt(lister);
        String listercolor = sharedPref.getString("themeColor", "1");
        oldColor = Integer.parseInt(listercolor);
        toggleTheme();
        setContentView(R.layout.activity_hymn);
        toogleScreenOn();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getTitle());
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        Intent p = getIntent();
        pos = p.getExtras().getInt("id");

        mViewPager.setCurrentItem(pos);


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 260;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hymn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
            return true;
        }
        if (id == R.id.action_share) {
            Hymnparser parser = new Hymnparser();
            ArrayList<HymnData> hymn = (ArrayList<HymnData>) parser.parseXML(this);
            HymnSorter hymnSorter = new HymnSorter();
            if (PlaceholderFragment.state == true) {
                hymnSorter.sortHymnById(hymn);
            } else {
                hymnSorter.sortHymnByName(hymn);
            }

            HymnData hymnData = hymn.get(pos);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, hymnData.getId() + ". " + hymnData.getTitle() + "\n" + hymnData.getStanza());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggleTheme();
        toogleScreenOn();
    }

    private void toggleTheme() {

        // Following options to change the Theme must precede setContentView().

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        isChecked = sharedPref.getBoolean("screen_on", true);
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

    private void toogleScreenOn() {
        if (isChecked) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (checkstate != isChecked) {

            checkstate = isChecked;
            Intent ne = new Intent(this, MainActivity.class);

            ne.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ne);
        }
    }
}
