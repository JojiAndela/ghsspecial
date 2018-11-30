package com.jojitoon.jesusme.ghsspecial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JESUS ME on 7/10/2017.
 */


public class PlaceholderFragment extends Fragment implements SortListener {

    public static final String ARG_ITEM_ID = "item_id";
    private HymnSorter hymnSorter = new HymnSorter();
    ArrayList<HymnData> hymn;
    HymnData hymnData;
    private int fontsize;
    public static boolean state = true;
    private int colorchange;
    LinearLayout layer;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String lister = sharedPref.getString("fontSize", "18");
        String color = sharedPref.getString("themeColor", "1");

        fontsize = Integer.parseInt(lister);
        colorchange = Integer.parseInt(color);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hymn, container, false);
        layer = (LinearLayout) rootView.findViewById(R.id.lay);
//            changelaycolor(colorchange);


        Hymnparser parser = new Hymnparser();
        hymn = (ArrayList<HymnData>) parser.parseXML(getActivity());
        if (state) {
            setSortTypeId(state);
            setData(rootView);
            return rootView;
        } else {
            setSortTypeId(state);
            setData(rootView);
            //state = true;
            return rootView;

        }

    }

    public void setData(View rootView) {
        int num = getArguments().getInt(ARG_SECTION_NUMBER);
        hymnData = hymn.get(num);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        TextView title = (TextView) rootView.findViewById(R.id.section_title);
        changelaycolor(colorchange);
        textView.setTextSize(fontsize);
        textView.setText(hymnData.getStanza());

        //textView.setTextSize(Float.parseFloat(pref.getString("fontSize", "18")));
        title.setText(hymnData.getId() + ". " + hymnData.getTitle());
    }

    @Override
    public void onSortByTitle() {
        hymnSorter.sortHymnByName(hymn);
        //hymnData.notifyAll();
    }

    @Override
    public void onSortById() {

        hymnSorter.sortHymnById(hymn);
        //hymnData.notifyAll();
    }

    public void setSortTypeId(boolean s) {
        if (s) {
            onSortById();
        } else {
            onSortByTitle();
        }
    }

    private void changelaycolor(int color) {


        if (color == 1)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else if (color == 2)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFO));
        else if (color == 3)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimarySB));
        else if (color == 4)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryPG));
        else if (color == 5)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryMB));
        else if (color == 6)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimarySP));
        else if (color == 7)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryGA));
        else if (color == 8)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLT));
        else if (color == 9)
            layer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryHG));
        else if (color == 10)
            layer.setBackgroundColor(getResources().getColor(android.support.design.R.color.primary_material_dark));

    }
}


