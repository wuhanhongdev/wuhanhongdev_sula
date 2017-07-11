package com.sula.omparator;

import com.jfinal.plugin.activerecord.Record;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Record> {
    @Override
    public int compare(Record record, Record record2) {
        double dist1 = record.get("distance");
        double dist2 = record2.get("distance");
        if(dist1 < dist2){
            return -1;
        }
        if(dist1 > dist2){
            return 1;
        }
        return 0;
    }
}
