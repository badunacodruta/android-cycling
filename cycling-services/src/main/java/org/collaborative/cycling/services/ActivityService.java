package org.collaborative.cycling.services;

import org.collaborative.cycling.models.*;
import org.collaborative.cycling.records.*;
import org.collaborative.cycling.repositories.ActivityRepository;
import org.collaborative.cycling.repositories.CoordinatesHistoryRepository;
import org.collaborative.cycling.repositories.GroupActivityRepository;
import org.collaborative.cycling.repositories.UserActivityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {
    private static final double DISTANCE_THRESHOLD = 2000;
    private static final long DEFAULT_ACTIVITY_ID = 1;

    private final ModelMapper modelMapper;
    private final ActivityRepository activityRepository;
    private final UserActivityRepository userActivityRepository;
    private final CoordinatesService coordinatesService;
    private final GroupActivityRepository groupActivityRepository;
    private final CoordinatesHistoryRepository coordinatesHistoryRepository;

    @Autowired
    public ActivityService(ModelMapper modelMapper, ActivityRepository activityRepository, UserActivityRepository userActivityRepository, CoordinatesService coordinatesService, GroupActivityRepository groupActivityRepository, CoordinatesHistoryRepository coordinatesHistoryRepository) {
        this.modelMapper = modelMapper;
        this.activityRepository = activityRepository;
        this.userActivityRepository = userActivityRepository;
        this.coordinatesService = coordinatesService;
        this.groupActivityRepository = groupActivityRepository;
        this.coordinatesHistoryRepository = coordinatesHistoryRepository;
    }

    public Coordinates getUserLocation(Long userId, Long activityId) {
        if (userId == null || activityId == null) {
            return null;
        }

        UserActivityRecord userActivityRecord = userActivityRepository.findByUserIdAndActivityId(userId, activityId);
        if (userActivityRecord == null) {
            return null;
        }

        return userActivityRecord.getCurrentCoordinates() == null ?
                null : modelMapper.map(userActivityRecord.getCurrentCoordinates(), Coordinates.class);
    }

    public Group getUserGroup(Long userId, Long activityId) {
        if (userId == null || activityId == null) {
            return null;
        }

        UserActivityRecord userActivityRecord = userActivityRepository.findByUserIdAndActivityId(userId, activityId);
        if (userActivityRecord == null) {
            return null;
        }

        GroupRecord groupRecord = userActivityRecord.getGroup();
        if (groupRecord == null) {
            return null;
        }

        return modelMapper.map(groupRecord, Group.class);
    }

    @Transactional
    public List<User> getUsers(Long activityId) {
        if (activityId == null) {
            return null;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return null;
        }

        List<UserActivityRecord> userActivityRecords = activityRecord.getUsers();
        List<User> users = new ArrayList<>(userActivityRecords.size());
        for (UserActivityRecord userActivityRecord : userActivityRecords) {
            users.add(modelMapper.map(userActivityRecord.getUser(), User.class));
        }

        return users;
    }

    @Transactional
    public List<User> getNearbyUsers(Long userId, Long activityId) {
        if (userId == null || activityId == null) {
            return null;
        }

        Coordinates currentUserCoordinates = getUserLocation(userId, activityId);
        if (currentUserCoordinates == null) {
            return null;
        }

        List<User> users = getUsers(activityId);
        if (users == null) {
            return null;
        }

        List<User> nearbyUsers = new ArrayList<>();
        for (User user : users) {
            Coordinates userCoordinates = getUserLocation(user.getId(), activityId);
            if (userCoordinates == null) {
                continue;
            }
            double distance = coordinatesService.computeDistance(currentUserCoordinates, userCoordinates);
            if (distance < DISTANCE_THRESHOLD) {
                nearbyUsers.add(user);
            }
        }

        return nearbyUsers;
    }

    public boolean updateUserLocation(Long userId, Long activityId, Coordinates coordinates) {
        if (userId == null || activityId == null || coordinates == null) {
            return false;
        }

        UserActivityRecord userActivityRecord = userActivityRepository.findByUserIdAndActivityId(userId, activityId);
        if (userActivityRecord == null) {
            return false;
        }

        userActivityRecord.setCurrentCoordinates(modelMapper.map(coordinates, CoordinatesRecord.class));
        userActivityRepository.save(userActivityRecord);

        CoordinatesHistoryRecord coordinatesHistoryRecord = new CoordinatesHistoryRecord(coordinates, userId, activityId);
        coordinatesHistoryRepository.save(coordinatesHistoryRecord);

        return true;
    }


    public void addUserToDefaultActivity(UserRecord userRecord, GroupRecord groupRecord) {
        Date now = new Date();

        ActivityRecord activityRecord = activityRepository.findOne(DEFAULT_ACTIVITY_ID);
        if (activityRecord == null) {
            activityRecord = new ActivityRecord("prima evadare", ActivityAccessType.PUBLIC, null, now, now, now);
            activityRecord = activityRepository.save(activityRecord);
        }

        UserActivityRecord userActivityRecord = userActivityRepository.findByUserIdAndActivityId(userRecord.getId(), DEFAULT_ACTIVITY_ID);
        if (userActivityRecord == null) {
            userActivityRecord = new UserActivityRecord(
                    null, ProgressStatus.NOT_STARTED, now, now, userRecord, activityRecord, null);
        }

        if (groupRecord != null) {
            userActivityRecord.setGroup(groupRecord);
        }

        userActivityRepository.save(userActivityRecord);
    }

    public void addGroupToDefaultActivity(GroupRecord groupRecord) {
        Date now = new Date();

        ActivityRecord activityRecord = activityRepository.findOne(DEFAULT_ACTIVITY_ID);
        if (activityRecord == null) {
            activityRecord = new ActivityRecord("prima evadare", ActivityAccessType.PUBLIC, null, now, now, now);
            activityRecord = activityRepository.save(activityRecord);
        }

        GroupActivityRecord groupActivityRecord = groupActivityRepository.findByGroupIdAndActivityId(groupRecord.getId(), DEFAULT_ACTIVITY_ID);
        if (groupActivityRecord == null) {
            groupActivityRecord = new GroupActivityRecord(
                    now, groupRecord, activityRecord);
            groupActivityRepository.save(groupActivityRecord);
        }
    }
}
