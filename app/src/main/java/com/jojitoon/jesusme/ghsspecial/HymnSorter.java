package com.jojitoon.jesusme.ghsspecial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by JESUS ME on 7/13/2017.
 */

public class HymnSorter {
    public void sortHymnByName(ArrayList<HymnData> hymns){
        Collections.sort(hymns, new Comparator<HymnData>() {
            @Override
            public int compare(HymnData lhs, HymnData rhs) {
                return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
            }
        });
    }

    public void sortHymnById(ArrayList<HymnData> hymns){
        Collections.sort(hymns, new Comparator<HymnData>() {
            @Override
            public int compare(HymnData lhs, HymnData rhs) {
                int lhsId=lhs.getId();
                int rhsId=rhs.getId();
                if(lhsId<rhsId)
                {
                    return -1;
                }
                else if(lhsId>rhsId)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        });
    }
}
