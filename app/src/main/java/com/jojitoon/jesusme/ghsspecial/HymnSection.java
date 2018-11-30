package com.jojitoon.jesusme.ghsspecial;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jojitoon.jesusme.ghsspecial.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HymnSection extends Fragment implements SortListener, SearchView.OnQueryTextListener {


    public RecyclerView recyclerView;
    public HymnAdapter adapter;
    private ArrayList<HymnData> hymn;
    private HymnSorter hymnSorter = new HymnSorter();
    public static boolean state = true;

    private FloatingActionButton fab = null;

    public HymnSection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_hymn_section, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.hymnlist);
        fab = (FloatingActionButton) rootview.findViewById(R.id.fabscroll);

        adapter = new HymnAdapter(getActivity(), myHymnList());

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int pos) {

                if (state) {
                    Intent intent = new Intent(getContext(), Hymn.class);

                    intent.putExtra("id", pos);
                    startActivity(intent);

                }
            }

            @Override
            public void onLongClick(View v, int pos) {
                int num = pos + 1;


                Toast.makeText(getActivity(), "copied ", Toast.LENGTH_LONG).show();
            }
        }));
        return rootview;
    }


    private ArrayList<HymnData> myHymnList() {
        Hymnparser parser = new Hymnparser();

        hymn = (ArrayList<HymnData>) parser.parseXML(getActivity());

        return hymn;
    }


    @Override
    public void onSortByTitle() {
        hymnSorter.sortHymnByName(hymn);
        PlaceholderFragment.state = false;
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onSortById() {
        hymnSorter.sortHymnById(hymn);
        PlaceholderFragment.state = true;
        adapter.notifyDataSetChanged();
    }

    //implementing touch listener
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
           // Log.d("joji", "RecyclerTouchListener: invoked");
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());

            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }

//            Log.d("joji", "onInterceptTouchEvent: " + gestureDetector.onTouchEvent(e) + "  " + e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//            Log.d("joji", "onTouchEvent: " + e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener {
        public void onClick(View v, int pos);

        public void onLongClick(View v, int pos);
    }

    @Override
    public void onResume() {
        super.onResume();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

            }
        });


        recyclerView.addOnScrollListener(new HideShowScrollListener() {
            @Override
            public void onHide() {
                fab.hide();
            }

            @Override
            public void onShow() {
                fab.show();
            }
        });


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {// Do something when collapsed
                state = true;
                HymnAdapter.state = false;
                getActivity().finish();
                Intent k = new Intent(getActivity(), MainActivity.class);

                startActivity(k);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                state = false;
                HymnAdapter.state = true;
                onSortById();
                MainActivity.setClosefab();
                fab.hide();
                return true; // Return true to expand action view
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<HymnData> filteredModelList = filter(hymn, newText);
        adapter.setFilter(filteredModelList);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<HymnData> filter(ArrayList<HymnData> models, String query) {
        query = query.toLowerCase();
        final ArrayList<HymnData> filteredModelList = new ArrayList<>();
        for (HymnData model : models) {
            final String text = model.getTitle().toLowerCase();
            final String text1 = model.getStanza().toLowerCase();
            final int text3 =  model.getId();

            if (text.contains(query) || text1.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
