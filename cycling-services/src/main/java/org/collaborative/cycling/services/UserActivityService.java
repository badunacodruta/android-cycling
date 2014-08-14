package org.collaborative.cycling.services;

import org.collaborative.cycling.models.ActivityAccessType;
import org.collaborative.cycling.models.JoinRequest;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.records.ActivityRecord;
import org.collaborative.cycling.records.UserActivityRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.ActivityRepository;
import org.collaborative.cycling.repositories.UserActivityRepository;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserActivityService {

    private ActivityRepository activityRepository;
    private UserRepository userRepository;
    private UserActivityRepository userActivityRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public UserActivityService(ActivityRepository activityRepository, UserRepository userRepository, UserActivityRepository userActivityRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.userActivityRepository = userActivityRepository;
    }

    public JoinRequest joinActivity(User user, long activityId) {
        if (user == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return null;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return null;
        }

        UserActivityRecord userActivityRecord = null;

        List<UserActivityRecord> joinedUserActivityRecordList = userRecord.getJoinedUserActivityRecordList();
        for (UserActivityRecord joinedUserActivityRecord : joinedUserActivityRecordList) {
            if (joinedUserActivityRecord.getActivity().getId() == activityId) {
                userActivityRecord = joinedUserActivityRecord;
                break;
            }
        }

        if (userActivityRecord == null) {
            userActivityRecord = new UserActivityRecord();
            userActivityRecord.setCreatedDate(new Date());
        }

        userActivityRecord.setUser(userRecord);
        userActivityRecord.setActivity(activityRecord);

        boolean waitingForAcceptance = true;
        if (activityRecord.getActivityAccessType() == ActivityAccessType.PUBLIC) {
            waitingForAcceptance = false;
        } else {
            userActivityRecord.setJoinRequest(true);
            userActivityRecord.setJoinAccept(false);
        }

        userActivityRecord = userActivityRepository.save(userActivityRecord);
        return new JoinRequest(userActivityRecord.getId(), user, activityId, waitingForAcceptance);
    }

    public boolean acceptJoinRequest(User user, long userActivityId) {
        return setJoinAcceptRequest(user, userActivityId, true);
    }

    public boolean declineJoinRequest(User user, long userActivityId) {
        return setJoinAcceptRequest(user, userActivityId, false);
    }

    private boolean setJoinAcceptRequest(User user, long userActivityId, boolean accept) {
        if (user == null) {
            return false;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return  false;
        }

        UserActivityRecord userActivityRecord = userActivityRepository.findOne(userActivityId);
        if (userActivityRecord == null) {
            return false;
        }

        userActivityRecord.setJoinAccept(accept);
        userActivityRepository.save(userActivityRecord);

        return true;
    }

    public List<JoinRequest> getJoinRequestsForUser(User user) {
        List<JoinRequest> joinRequestList = new ArrayList<>();

        if (user == null) {
            return joinRequestList;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return joinRequestList;
        }

        List<ActivityRecord> createdActivityRecordList = userRecord.getCreatedActivityRecordList();
        for (ActivityRecord createdActivityRecord : createdActivityRecordList) {

            List<UserActivityRecord> joinedUserActivityRecordList = createdActivityRecord.getJoinedUserActivityRecordList();
            for (UserActivityRecord joinedUserActivityRecord : joinedUserActivityRecordList) {

                if (joinedUserActivityRecord.isJoinRequest() && !joinedUserActivityRecord.isJoinAccept()) {
                    User joinedUser = modelMapper.map(joinedUserActivityRecord.getUser(), User.class);
                    long activityId = joinedUserActivityRecord.getActivity().getId();
                    JoinRequest joinRequest = new JoinRequest(joinedUserActivityRecord.getId(), joinedUser, activityId, true);
                    joinRequestList.add(joinRequest);
                }
            }
        }

        return joinRequestList;
    }

    public List<JoinRequest> getJoinRequestsCreatedByUser(User user) {
        List<JoinRequest> joinRequestList = new ArrayList<>();

        if (user == null) {
            return joinRequestList;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return joinRequestList;
        }

        List<UserActivityRecord> joinedUserActivityRecordList = userRecord.getJoinedUserActivityRecordList();
        for (UserActivityRecord joinedUserActivityRecord : joinedUserActivityRecordList) {

            if (joinedUserActivityRecord.isJoinRequest() && !joinedUserActivityRecord.isJoinAccept()) {
                User joinedUser = modelMapper.map(joinedUserActivityRecord.getUser(), User.class);
                long activityId = joinedUserActivityRecord.getActivity().getId();
                JoinRequest joinRequest = new JoinRequest(joinedUserActivityRecord.getId(), joinedUser, activityId, true);
                joinRequestList.add(joinRequest);
            }
        }

        return joinRequestList;
    }
}
