package org.collaborative.cycling.comparators;

import org.collaborative.cycling.records.UserRecord;

import java.util.Comparator;

public class UserRecordComparator implements Comparator<UserRecord> {

    @Override
    public int compare(UserRecord o1, UserRecord o2) {
        return o1.getEmail().compareTo(o2.getEmail());
    }
}
