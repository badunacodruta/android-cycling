package org.collaborative.cycling.services;

import org.collaborative.cycling.models.Coordinates;
import org.collaborative.cycling.models.Group;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.records.ActivityRecord;
import org.collaborative.cycling.records.GroupRecord;
import org.collaborative.cycling.records.UserActivityRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.ActivityRepository;
import org.collaborative.cycling.repositories.UserActivityRepository;
import org.collaborative.cycling.repositories.UserRepository;
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

    private final ModelMapper modelMapper;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final UserActivityRepository userActivityRepository;
    private final CoordinatesService coordinatesService;

    @Autowired
    public ActivityService(ModelMapper modelMapper, ActivityRepository activityRepository, UserRepository userRepository, UserActivityRepository userActivityRepository, CoordinatesService coordinatesService) {
        this.modelMapper = modelMapper;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.userActivityRepository = userActivityRepository;
        this.coordinatesService = coordinatesService;
    }

    public Coordinates getUserLocation(Long userId, Long activityId) {
        if (userId == null || activityId == null) {
            return null;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(userId);
        if (userRecord == null) {
            return null;
        }

        UserActivityRecord userActivityRecord = userActivityRepository.findByUserIdAndActivityId(userId, activityId);
        if (userActivityRecord == null) {
            return null;
        }

        return modelMapper.map(userActivityRecord.getCurrentCoordinates(), Coordinates.class);
    }

    public Group getUserGroup(Long userId, Long activityId) {
        if (userId == null || activityId == null) {
            return null;
        }

        ActivityRecord activityRecord = activityRepository.findOne(activityId);
        if (activityRecord == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(userId);
        if (userRecord == null) {
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
            double distance = coordinatesService.computeDistance(currentUserCoordinates, userCoordinates);
            if (distance < DISTANCE_THRESHOLD) {
                nearbyUsers.add(user);
            }
        }

        return nearbyUsers;
    }
}
