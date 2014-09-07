package org.collaborative.cycling.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.collaborative.cycling.Utilities;
import org.collaborative.cycling.models.*;
import org.collaborative.cycling.records.ActivityRecord;
import org.collaborative.cycling.records.UserActivityRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.ActivityRepository;
import org.collaborative.cycling.repositories.UserActivityRepository;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class ActivityService {

    private static final String UNTITLED_ACTIVITY_PREFIX = "Untitled activity ";
    private static final int DEFAULT_PAGE_SIZE = 10;

    private ActivityRepository activityRepository;
    private UserRepository userRepository;
    private UserActivityRepository userActivityRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository, UserActivityRepository userActivityRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.userActivityRepository = userActivityRepository;
    }

    public Activity saveActivity(User user, Activity activity) {
        if (user == null || activity == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return null;
        }

        Date currentDate = new Date();

        ActivityRecord activityRecord = activityRepository.findOne(activity.getId());
        if (activityRecord == null) {
            activityRecord = new ActivityRecord();
            activityRecord.setCreatedDate(currentDate);
        } else {
            if (!activityRecord.getOwner().getEmail().equals(user.getEmail())
                    || activityRecord.isDeleted()) {
                return null;
            }
        }

        activityRecord.setName(getActivityName(userRecord, activity));
        if (activity.getActivityAccessType() != null) {
            activityRecord.setActivityAccessType(activity.getActivityAccessType());
        }
        if (activity.getCoordinates() != null) {
            activityRecord.setCoordinates(activity.getCoordinates());
        }
        if (activity.getStartDate() != null) {
            activityRecord.setStartDate(activity.getStartDate());
        }

        activityRecord.setOwner(userRecord);
        activityRecord.setUpdatedDate(currentDate);

        activityRecord = activityRepository.save(activityRecord);
        userActivityRepository.save(activityRecord.updateJoinedUser(userRecord, JoinedStatus.MINE));

        return modelMapper.map(activityRecord, Activity.class);
    }

    public List<Activity> getActivities(User user, int pageNumber, int pageSize) {
        List<ActivityRecord> activityRecordList = getActivityRecords(user, pageNumber, pageSize);
        Type activityListType = new TypeToken<List<Activity>>(){}.getType();
        return modelMapper.map(activityRecordList, activityListType);
    }

    public List<ActivitySummary> getActivitiesSummary(User user, int pageNumber, int pageSize) {
        List<ActivityRecord> activityRecordList = getActivityRecords(user, pageNumber, pageSize);
        Type activityInfoListType = new TypeToken<List<ActivitySummary>>(){}.getType();
        return modelMapper.map(activityRecordList, activityInfoListType);
    }

//    TODO: maybe add pagination
    public List<ActivitySearchResult> searchActivitiesToJoin(User user, String query) {
        List<ActivityRecord> activityRecordList = activityRepository.getActivitiesByName(user.getEmail(), String.format("%%%s%%", query));

        Iterator<ActivityRecord> iterator = activityRecordList.iterator();
        while (iterator.hasNext()) {
            ActivityRecord activityRecord = iterator.next();

            if (activityRecord.getActivityAccessType() == ActivityAccessType.PRIVATE) {
                iterator.remove();
            }

            List<UserActivityRecord> joinedUsers = activityRecord.getJoinedUserActivityRecordList();
            if (joinedUsers == null) {
                continue;
            }

            for (UserActivityRecord joinedUser : joinedUsers) {
                if (joinedUser.getUser().getEmail().equals(user.getEmail())) {
                    iterator.remove();
                }
            }
        }

        Type activityInfoListType = new TypeToken<List<ActivitySearchResult>>(){}.getType();
        return modelMapper.map(activityRecordList, activityInfoListType);
    }

    public List<JoinedActivity> getJoinedActivities(User user, int pageNumber, int pageSize) {
        List<JoinedActivity> joinedActivityList = new ArrayList<>();
        if (user == null) {
            return joinedActivityList;
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "createdDate");
        List<UserActivityRecord> userActivityRecordList = userActivityRepository.getUserActivitiesByPage(user.getEmail(), pageRequest);

        for (UserActivityRecord userActivityRecord : userActivityRecordList) {
            JoinedActivity joinedActivity = modelMapper.map(userActivityRecord.getActivity(), JoinedActivity.class);
            joinedActivity.setJoinedStatus(userActivityRecord.getJoinedStatus());

            joinedActivityList.add(joinedActivity);
        }

        return joinedActivityList;
    }

    public List<JoinedActivity> getJoinedActivities(User user) {
        List<JoinedActivity> joinedActivityList = new ArrayList<>();
        if (user == null) {
            return joinedActivityList;
        }

        List<UserActivityRecord> userActivityRecordList = userActivityRepository.getUserActivities(user.getEmail());

        for (UserActivityRecord userActivityRecord : userActivityRecordList) {
            JoinedActivity joinedActivity = modelMapper.map(userActivityRecord.getActivity(), JoinedActivity.class);
            joinedActivity.setJoinedStatus(userActivityRecord.getJoinedStatus());

            joinedActivityList.add(joinedActivity);
        }

        return joinedActivityList;
    }

    public int getActivitiesCount(User user) {
        if (user == null) {
            return 0;
        }

        return activityRepository.getActivitiesCount(user.getEmail());
    }

    public Activity getActivity(User user, long activityId) {
        if (user == null) {
            return null;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null || activityRecord.isDeleted()) {
            return null;
        }

        if (activityRecord.getOwner().getEmail().equals(user.getEmail())) {
            return modelMapper.map(activityRecord, Activity.class);
        }

        return null;
    }


    public boolean deleteActivity(User user, long activityId) {
        if (user == null) {
            return false;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null || activityRecord.isDeleted()) {
            return false;
        }

        if (activityRecord.getOwner().getEmail().equals(user.getEmail())) {
            activityRecord.setDeleted(true);
            activityRecord.setDeletedDate(new Date());
            activityRepository.save(activityRecord);
            return true;
        }

        return false;
    }

    private List<ActivityRecord> getActivityRecords(User user, int pageNumber, int pageSize) {
        if (user == null) {
            return new ArrayList<>();
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "createdDate");
        return activityRepository.getActivitiesByPage(user.getEmail(), pageRequest);
    }

    private String getActivityName(UserRecord userRecord, Activity activity) {
        String name = activity.getName();

        if (Utilities.isNullOrEmpty(name)) {
            long untitledActivitiesIndex = userRecord.getUntitledActivitiesIndex();
            name = UNTITLED_ACTIVITY_PREFIX + untitledActivitiesIndex;
            untitledActivitiesIndex++;

            userRecord.setUntitledActivitiesIndex(untitledActivitiesIndex);
            userRepository.save(userRecord);
        }

        return name;
    }
}
