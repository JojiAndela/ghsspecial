package com.jojitoon.jesusme.ghsspecial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JESUS ME on 7/14/2017
 */

public class DocADapt extends RecyclerView.Adapter<DocADapt.DocViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DocData> docList;


    public DocADapt(Context context, List<DocData> mydocList) {
        inflater = LayoutInflater.from(context);
        this.docList = (ArrayList<DocData>) mydocList;

    }

    @Override
    public DocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.docview, parent, false);
        DocViewHolder holder = new DocViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(DocViewHolder holder, final int position) {
        DocData docData = docList.get(position);
        holder.docNo.setText(docData.getId());
        holder.docTi.setText(docData.getTitle());
        holder.docCon.setText(docData.getStanza());

    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    class DocViewHolder extends RecyclerView.ViewHolder {
        TextView docNo, docTi, docCon;

        public DocViewHolder(View itemView) {
            super(itemView);

            docNo = (TextView) itemView.findViewById(R.id.doctId);
            docTi = (TextView) itemView.findViewById(R.id.doctTit);
            docCon = (TextView) itemView.findViewById(R.id.doctContent);

        }
    }
}
