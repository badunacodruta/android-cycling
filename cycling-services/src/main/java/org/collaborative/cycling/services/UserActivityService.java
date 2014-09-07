package org.collaborative.cycling.services;

import org.collaborative.cycling.models.*;
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

        Date currentDate = new Date();
        if (userActivityRecord == null) {
            userActivityRecord = new UserActivityRecord();
            userActivityRecord.setCreatedDate(currentDate);
        }

        userActivityRecord.setUser(userRecord);
        userActivityRecord.setActivity(activityRecord);
        if (activityRecord.getActivityAccessType() == ActivityAccessType.PUBLIC) {
            userActivityRecord.setJoinedStatus(JoinedStatus.ACCEPTED);
        }
        userActivityRecord.setUpdatedDate(currentDate);

        userActivityRecord = userActivityRepository.save(userActivityRecord);
        return new JoinRequest(userActivityRecord.getId(), user, activityId, userActivityRecord.getJoinedStatus());
    }

    public boolean acceptJoinRequest(User user, long userActivityId) {
        return setJoinAcceptRequest(user, userActivityId, JoinedStatus.ACCEPTED);
    }

    public boolean declineJoinRequest(User user, long userActivityId) {
        return setJoinAcceptRequest(user, userActivityId, JoinedStatus.DECLINED);
    }

    private boolean setJoinAcceptRequest(User user, long userActivityId, JoinedStatus joinedStatus) {
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

        userActivityRecord.setJoinedStatus(joinedStatus);
        userActivityRepository.save(userActivityRecord);

        return true;
    }

    public List<JoinRequest> getPendingJoinRequestsForUser(User user) {
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

                if (joinedUserActivityRecord.getJoinedStatus() == JoinedStatus.PENDING) {
                    User joinedUser = modelMapper.map(joinedUserActivityRecord.getUser(), User.class);
                    long activityId = joinedUserActivityRecord.getActivity().getId();
                    JoinedStatus joinedStatus = joinedUserActivityRecord.getJoinedStatus();
                    JoinRequest joinRequest = new JoinRequest(joinedUserActivityRecord.getId(), joinedUser, activityId, joinedStatus);
                    joinRequestList.add(joinRequest);
                }
            }
        }

        return joinRequestList;
    }

    public List<JoinRequest> getPendingJoinRequestsCreatedByUser(User user) {
        return getJoinRequestsCreatedByUser(user, JoinedStatus.PENDING);
    }

    public List<JoinRequest> getJoinRequestsCreatedByUser(User user, JoinedStatus filterJoinedStatus) {
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

            if (filterJoinedStatus != null && joinedUserActivityRecord.getJoinedStatus() != filterJoinedStatus) {
                continue;
            }

            User joinedUser = modelMapper.map(joinedUserActivityRecord.getUser(), User.class);
            long activityId = joinedUserActivityRecord.getActivity().getId();
            JoinedStatus joinedStatus = joinedUserActivityRecord.getJoinedStatus();
            JoinRequest joinRequest = new JoinRequest(joinedUserActivityRecord.getId(), joinedUser, activityId, joinedStatus);
            joinRequestList.add(joinRequest);
        }

        return joinRequestList;
    }

    public boolean saveJoinedUser(User user, long activityId, ProgressStatus progressStatus, JoinedStatus joinedStatus, String coordinates) {
        if (user == null) {
            return false;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return false;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return false;
        }

        UserActivityRecord userActivityRecord = activityRecord.updateJoinedUser(userRecord, progressStatus, joinedStatus, coordinates);
        userActivityRepository.save(userActivityRecord);
        return true;
    }

    public boolean saveJoinedUser(User user, long activityId, ProgressStatus progressStatus, JoinedStatus joinedStatus, Coordinates coordinates) {
        if (user == null) {
            return false;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return false;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return false;
        }

        UserActivityRecord userActivityRecord = activityRecord.updateJoinedUser(userRecord, progressStatus, joinedStatus, coordinates);
        userActivityRepository.save(userActivityRecord);
        return true;
    }

    public boolean saveJoinedUser(User user, long activityId, ProgressStatus progressStatus, JoinedStatus joinedStatus) {
        if (user == null) {
            return false;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return false;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return false;
        }

        UserActivityRecord userActivityRecord = activityRecord.updateJoinedUser(userRecord, progressStatus, joinedStatus);
        userActivityRepository.save(userActivityRecord);
        return true;
    }

    public ProgressStatus getProgressStatusForUser(User user, long activityId) {
        if (user == null) {
            return ProgressStatus.NOT_STARTED;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return ProgressStatus.NOT_STARTED;
        }

        return userRecord.getProgressStatusForActivity(activityId);
    }

    public boolean isJoinedUser(User user, long activityId) {
        if (user == null) {
            return false;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return false;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return false;
        }

        List<UserActivityRecord> joinedUsers = activityRecord.getJoinedUserActivityRecordList();
        for (UserActivityRecord joinedUser : joinedUsers) {
            if (joinedUser.getActivity().getId() == activityId) {
                return joinedUser.getJoinedStatus() == JoinedStatus.ACCEPTED ? true : false;
            }
        }

        return false;
    }
}
