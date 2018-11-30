package com.jojitoon.jesusme.ghsspecial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JESUS ME on 7/7/2017 . for GHSspecial
 */

public class HymnAdapter extends RecyclerView.Adapter<HymnAdapter.HymnViewHolder> {

    private LayoutInflater inflater;
    public static boolean state = false;

    private ArrayList<HymnData> hymnList;
    private Context context;

    public HymnAdapter(Context context, List<HymnData> myHymnList) {
        inflater = LayoutInflater.from(context);
        //this.data = data;
        this.context = context;
        this.hymnList = (ArrayList<HymnData>) myHymnList;
    }

    @Override
    public HymnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hymn_row, parent, false);

        HymnViewHolder holder = new HymnViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(HymnViewHolder holder, final int position) {
        final HymnData hymnData = hymnList.get(position);
        holder.hymnTitle.setText(hymnData.getTitle());
        holder.hymnNo.setText(hymnData.getId() + ".");
        if (state == true) {
            holder.clicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Hymn.class);
                    int pos = hymnData.getId() - 1;
                    intent.putExtra("id", pos);
                    context.startActivity(intent);


                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return hymnList.size();//data.size();
    }

    class HymnViewHolder extends RecyclerView.ViewHolder {
        TextView hymnNo, hymnTitle;
        LinearLayout clicker;


        public HymnViewHolder(View itemView) {
            super(itemView);
            clicker = (LinearLayout) itemView.findViewById(R.id.clicker);
            hymnNo = (TextView) itemView.findViewById(R.id.hno);
            hymnTitle = (TextView) itemView.findViewById(R.id.hti);
        }


    }


    public void setFilter(List<HymnData> hymns) {
        hymnList = new ArrayList<>();
        hymnList.addAll(hymns);
        notifyDataSetChanged();
    }
}
