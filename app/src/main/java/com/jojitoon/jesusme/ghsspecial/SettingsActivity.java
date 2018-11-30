package com.jojitoon.jesusme.ghsspecial;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    private int currentTheme;
    private int oldTheme;
    private int currentColor;
    private int oldColor;


    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);


            addPreferencesFromResource(R.xml.pref_main);

            // theme preference change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.themechangeMode)));
            // theme color preference change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.themechangeColor)));

            // font size preference change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.fontSizeChangeName)));


        }
    }

    private void initwork() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String lister = sharedPref.getString("themeChange", "1");
        oldTheme = Integer.parseInt(lister);
        String listercolor = sharedPref.getString("themeColor", "1");
        oldColor = Integer.parseInt(listercolor);
    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            }  else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };


    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initwork();
        toggleTheme();
        super.onCreate(savedInstanceState);

        setupActionBar();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
//            setTheme(R.style.Dark_AppTheme);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }




    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        toggleTheme();
        super.onResume();


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
            setTheme(R.style.AppTheme);
        } else if (currentTheme == 1 && currentColor == 2) {
            setTheme(R.style.FireOrange);
        } else if (currentTheme == 1 && currentColor == 3) {
            setTheme(R.style.SkyBlue);
        } else if (currentTheme == 1 && currentColor == 4) {
            setTheme(R.style.PurpleGrapes);
        } else if (currentTheme == 1 && currentColor == 5) {
            setTheme(R.style.MintBlue);
        } else if (currentTheme == 1 && currentColor == 6) {
            setTheme(R.style.SweetPink);
        } else if (currentTheme == 1 && currentColor == 7) {
            setTheme(R.style.GreenApple);
        } else if (currentTheme == 1 && currentColor == 8) {
            setTheme(R.style.LakeTeal);
        } else if (currentTheme == 1 && currentColor == 9) {
            setTheme(R.style.HeavyGreen);
        } else if (currentTheme == 1 && currentColor == 10) {
            setTheme(R.style.DarkL_AppTheme);
//            dark themes
        } else if (currentTheme == 2 && currentColor == 1) {
            setTheme(R.style.AppThemeDark);
        } else if (currentTheme == 2 && currentColor == 2) {
            setTheme(R.style.FireOrangeDark);
        } else if (currentTheme == 2 && currentColor == 3) {
            setTheme(R.style.SkyBlueDark);
        } else if (currentTheme == 2 && currentColor == 4) {
            setTheme(R.style.PurpleGrapesDark);
        } else if (currentTheme == 2 && currentColor == 5) {
            setTheme(R.style.MintBlueDark);
        } else if (currentTheme == 2 && currentColor == 6) {
            setTheme(R.style.SweetPinkDark);
        } else if (currentTheme == 2 && currentColor == 7) {
            setTheme(R.style.GreenAppleDark);
        } else if (currentTheme == 2 && currentColor == 8) {
            setTheme(R.style.LakeTealDark);
        } else if (currentTheme == 2 && currentColor == 9) {
            setTheme(R.style.HeavyGreenDark);
        } else if (currentTheme == 2 && currentColor == 10) {
            setTheme(R.style.Dark_AppTheme);
        }


        if (oldTheme != currentTheme || oldColor != currentColor) {

            oldTheme = currentTheme;
            oldColor = currentColor;

            Intent k = new Intent(this, SettingsActivity.class);

            // Following flag clears the activity with old theme from the stack so an exit from the
            // activity with new theme will not take you back to the version with the old theme.

            k.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(k);
        }

    }

}
