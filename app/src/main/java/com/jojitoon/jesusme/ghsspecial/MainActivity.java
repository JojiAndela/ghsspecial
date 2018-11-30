package com.jojitoon.jesusme.ghsspecial;


import android.animation.Animator;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import java.util.Calendar;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements SearchDialog.Communicator {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    static LinearLayout fabLayout1;
    static LinearLayout fabLayout2;
    static LinearLayout fabLayout3;
    static View fabBGLayout;
    static boolean isFABOpen = false;
    long time = (System.currentTimeMillis()) / (60 * 60 * 1000);
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public static final String TAG = "THEMES";
    private boolean isChecked;
    private boolean checkstate;
    private int currentTheme;
    private int oldTheme;
    private int currentColor;
    private int oldColor;
    private int fabColor = R.color.colorAccentFO;
    private PendingIntent pendingIntent;
    private Animation fabOpenAnimation;
    private static Animation fabCloseAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initwork();
        toggleTheme();
        toogleScreenOn();
        setContentView(R.layout.activity_main);
        initialize();
        createFab();


    }

    private void initwork() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        isChecked = sharedPref.getBoolean("screen_on", true);
        String lister = sharedPref.getString("themeChange", "1");
        oldTheme = Integer.parseInt(lister);
        String listercolor = sharedPref.getString("themeColor", "1");
        oldColor = Integer.parseInt(listercolor);
        checkstate = isChecked;
    }

    public void initialize() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        getAnimations();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            if (fragment instanceof SortListener) {
                ((SortListener) fragment).onSortById();
            }
            PlaceholderFragment.state = true;
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_help) {
            //createNotificate();
            Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            if (fragment instanceof SortListener) {
                ((SortListener) fragment).onSortById();
            }
            PlaceholderFragment.state = true;
            Intent intent = new Intent(this, AboutHelp.class);
            intent.putExtra("callno", 1);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_about) {
            Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            if (fragment instanceof SortListener) {
                ((SortListener) fragment).onSortById();
            }
            PlaceholderFragment.state = true;
            Intent intent = new Intent(this, AboutHelp.class);
            intent.putExtra("callno", 2);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_exit) {
            closeact();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    void closeact(){
        finish();
    }

    @Override
    public void onDialogMessage(int i) {
        Toast.makeText(this, "searching for hymn no: " + i, Toast.LENGTH_LONG).show();
    }


    private void createFab() {

        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fabBGLayout = findViewById(R.id.fabBGLayout);
        fab1.setBackgroundTintList(getResources().getColorStateList(fabColor));
        fab2.setBackgroundTintList(getResources().getColorStateList(fabColor));
        fab3.setBackgroundTintList(getResources().getColorStateList(fabColor));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });


        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
                if (fragment instanceof SortListener) {
                    ((SortListener) fragment).onSortById();
                }
                FragmentManager manager = getSupportFragmentManager();
                SearchDialog searchDialog = new SearchDialog();
                searchDialog.show(manager, "SearchDialog");
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
                if (fragment instanceof SortListener) {
                    ((SortListener) fragment).onSortByTitle();
                }
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
                if (fragment instanceof SortListener) {
                    ((SortListener) fragment).onSortById();
                }
            }
        });

    }

    private void getAnimations() {

        fabOpenAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_open);

        fabCloseAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_close);

    }
    private void showFABMenu() {
        isFABOpen = true;
        getAnimations();
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);


        fabLayout1.setAnimation(fabOpenAnimation);
        fabLayout2.setAnimation(fabOpenAnimation);
        fabLayout3.setAnimation(fabOpenAnimation);

        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));

    }

    private static void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayout1.setAnimation(fabCloseAnimation);
        fabLayout2.setAnimation(fabCloseAnimation);
        fabLayout3.setAnimation(fabCloseAnimation);

        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public static void setClosefab() {
        fab.hide();
        closeFABMenu();
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.`
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            HymnSection hymnSection = new HymnSection();

            IndexSection indexSection = new IndexSection();
            Doctrine doctrine = new Doctrine();
            switch (position) {
                case 0:

                    return hymnSection;
                case 1:

                    return indexSection;
                case 2:

                    return doctrine;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Hymn";
                case 1:
                    return "Content";
                case 2:
                    return "Doctrine";
            }
            return null;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {

        toggleTheme();
        toogleScreenOn();
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
            setTheme(R.style.AppTheme_NoActionBar);
            fabColor = R.color.colorPrimary;
        } else if (currentTheme == 1 && currentColor == 2) {
            setTheme(R.style.FireOrange_NoActionBar);
            fabColor = R.color.colorPrimaryFO;
        } else if (currentTheme == 1 && currentColor == 3) {
            setTheme(R.style.SkyBlue_NoActionBar);
            fabColor = R.color.colorPrimarySB;
        } else if (currentTheme == 1 && currentColor == 4) {
            setTheme(R.style.PurpleGrapes_NoActionBar);
            fabColor = R.color.colorPrimaryPG;
        } else if (currentTheme == 1 && currentColor == 5) {
            setTheme(R.style.MintBlue_NoActionBar);
            fabColor = R.color.colorPrimaryMB;
        } else if (currentTheme == 1 && currentColor == 6) {
            setTheme(R.style.SweetPink_NoActionBar);
            fabColor = R.color.colorPrimarySP;
        } else if (currentTheme == 1 && currentColor == 7) {
            setTheme(R.style.GreenApple_NoActionBar);
            fabColor = R.color.colorPrimaryGA;
        } else if (currentTheme == 1 && currentColor == 8) {
            setTheme(R.style.LakeTeal_NoActionBar);
            fabColor = R.color.colorPrimaryLT;
        } else if (currentTheme == 1 && currentColor == 9) {
            setTheme(R.style.HeavyGreen_NoActionBar);
            fabColor = R.color.colorPrimaryHG;
        } else if (currentTheme == 1 && currentColor == 10) {
            setTheme(R.style.DarkL_AppTheme_NoActionBar);
            fabColor = R.color.colorPrimaryHG;
//            dark themes
        } else if (currentTheme == 2 && currentColor == 1) {
            setTheme(R.style.AppThemeDark_NoActionBar);
            fabColor = R.color.colorPrimary;
        } else if (currentTheme == 2 && currentColor == 2) {
            setTheme(R.style.FireOrangeDark_NoActionBar);
            fabColor = R.color.colorPrimaryFO;
        } else if (currentTheme == 2 && currentColor == 3) {
            setTheme(R.style.SkyBlueDark_NoActionBar);
            fabColor = R.color.colorPrimarySB;
        } else if (currentTheme == 2 && currentColor == 4) {
            setTheme(R.style.PurpleGrapesDark_NoActionBar);
            fabColor = R.color.colorPrimaryPG;
        } else if (currentTheme == 2 && currentColor == 5) {
            setTheme(R.style.MintBlueDark_NoActionBar);
            fabColor = R.color.colorPrimaryMB;
        } else if (currentTheme == 2 && currentColor == 6) {
            setTheme(R.style.SweetPinkDark_NoActionBar);
            fabColor = R.color.colorPrimarySP;
        } else if (currentTheme == 2 && currentColor == 7) {
            setTheme(R.style.GreenAppleDark_NoActionBar);
            fabColor = R.color.colorPrimaryGA;
        } else if (currentTheme == 2 && currentColor == 8) {
            setTheme(R.style.LakeTealDark_NoActionBar);
            fabColor = R.color.colorPrimaryLT;
        } else if (currentTheme == 2 && currentColor == 9) {
            setTheme(R.style.HeavyGreenDark_NoActionBar);
            fabColor = R.color.colorPrimaryHG;
        } else if (currentTheme == 2 && currentColor == 10) {
            setTheme(R.style.Dark_AppTheme_NoActionBar);
            fabColor = R.color.colorPrimaryHG;
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


    public void ghs1(View view) {
        clickit(0);
    }

    public void ghs2(View view) {
        clickit(7);
    }

    public void ghs3(View view) {
        clickit(15);
    }

    public void ghs4(View view) {
        clickit(36);
    }

    public void ghs5(View view) {
        clickit(40);
    }

    public void ghs6(View view) {
        clickit(52);
    }

    public void ghs7(View view) {
        clickit(63);
    }

    public void ghs8(View view) {
        clickit(75);
    }

    public void ghs9(View view) {
        clickit(95);
    }

    public void ghs10(View view) {
        clickit(101);
    }

    public void ghs11(View view) {
        clickit(103);
    }

    public void ghs12(View view) {
        clickit(122);

    }

    public void ghs13(View view) {
        clickit(136);
    }

    public void ghs14(View view) {
        clickit(149);
    }

    public void ghs15(View view) {
        clickit(154);
    }

    public void ghs16(View view) {
        clickit(162);
    }

    public void ghs17(View view) {
        clickit(171);
    }

    public void ghs18(View view) {
        clickit(176);
    }

    public void ghs19(View view) {
        clickit(192);
    }

    public void ghs20(View view) {

        clickit(195);
    }

    public void ghs21(View view) {
        clickit(203);
    }

    public void ghs22(View view) {
        clickit(208);
    }

    void clickit(int num) {
        Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 0);

        if (fragment instanceof SortListener) {
            ((SortListener) fragment).onSortById();
        }
        Intent intent = new Intent(this, Hymn.class);
        PlaceholderFragment.state = true;

        intent.putExtra("id", num);
        startActivity(intent);
    }

}
