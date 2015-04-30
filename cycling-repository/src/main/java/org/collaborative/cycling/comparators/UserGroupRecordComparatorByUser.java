package org.collaborative.cycling.comparators;

import org.collaborative.cycling.records.UserGroupRecord;

import java.util.Comparator;

public class UserGroupRecordComparatorByUser implements Comparator<UserGroupRecord> {
    @Override
    public int compare(UserGroupRecord o1, UserGroupRecord o2) {
        return o1.getUser().getEmail().compareTo(o2.getUser().getEmail());
    }
}
