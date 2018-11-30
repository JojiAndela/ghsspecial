package com.jojitoon.jesusme.ghsspecial;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Doctrine extends Fragment {
    RecyclerView rv;
    public DocADapt aDapt;

    public Doctrine() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctrine, container, false);
        rv = (RecyclerView) view.findViewById(R.id.docrv);

        DocParser parser = new DocParser();

        ArrayList<DocData> doc = (ArrayList<DocData>) parser.parseXML(getActivity());


        aDapt = new DocADapt(getActivity(), doc);
        rv.setAdapter(aDapt);


        return view;
    }



}
