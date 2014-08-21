package org.collaborative.cycling.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class JoinedActivity extends Activity implements Serializable {
    private JoinedStatus joinedStatus;

    public JoinedStatus getJoinedStatus() {
        return joinedStatus;
    }

    public void setJoinedStatus(JoinedStatus joinedStatus) {
        this.joinedStatus = joinedStatus;
    }
}
