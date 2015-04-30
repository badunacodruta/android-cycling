package org.collaborative.cycling.comparators;

import org.collaborative.cycling.records.GroupRecord;

import java.util.Comparator;

public class GroupRecordComparator implements Comparator<GroupRecord> {
    @Override
    public int compare(GroupRecord o1, GroupRecord o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
