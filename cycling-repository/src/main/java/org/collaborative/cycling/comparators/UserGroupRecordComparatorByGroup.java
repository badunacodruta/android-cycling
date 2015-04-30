package org.collaborative.cycling.comparators;

import org.collaborative.cycling.records.UserGroupRecord;

import java.util.Comparator;

public class UserGroupRecordComparatorByGroup implements Comparator<UserGroupRecord> {
    @Override
    public int compare(UserGroupRecord o1, UserGroupRecord o2) {
        return o1.getGroup().getName().compareTo(o2.getGroup().getName());
    }
}
