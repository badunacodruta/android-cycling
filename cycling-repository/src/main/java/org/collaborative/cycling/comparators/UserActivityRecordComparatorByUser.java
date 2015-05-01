package org.collaborative.cycling.comparators;

import org.collaborative.cycling.records.UserActivityRecord;

import java.util.Comparator;

public class UserActivityRecordComparatorByUser implements Comparator<UserActivityRecord> {
    @Override
    public int compare(UserActivityRecord o1, UserActivityRecord o2) {
        return o1.getUser().getEmail().compareTo(o2.getUser().getEmail());
    }
}
