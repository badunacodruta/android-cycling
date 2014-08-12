package org.collaborative.cycling.services;

import org.collaborative.cycling.Utilities;
import org.collaborative.cycling.models.Activity;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.records.ActivityRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.ActivityRepository;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityService {

    private static final String UNTITLED_ACTIVITY_PREFIX = "Untitled activity ";
    private static final int DEFAULT_PAGE_SIZE = 10;

    private ActivityRepository activityRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public Activity saveActivity(User user, Activity activity) {
        if (activity == null || user == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            return  null;
        }
        user = modelMapper.map(userRecord, User.class);

        activity.setOwner(user);
        activity.setCreatedDate(new Date());

        if (Utilities.isNullOrEmpty(activity.getName())) {
            long untitledActivitiesIndex = userRecord.getUntitledActivitiesIndex();
            activity.setName(UNTITLED_ACTIVITY_PREFIX + untitledActivitiesIndex);
            untitledActivitiesIndex++;

            userRecord.setUntitledActivitiesIndex(untitledActivitiesIndex);
            userRepository.save(userRecord);
        }

        ActivityRecord activityRecord = modelMapper.map(activity, ActivityRecord.class);
        activityRecord = activityRepository.save(activityRecord);
        return modelMapper.map(activityRecord, Activity.class);
    }

    public List<Activity> getActivities(User user, int pageNumber, int pageSize) {
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
        List<ActivityRecord> activityRecordList = activityRepository.getActivitiesByPage(user.getEmail(), pageRequest);
        Type activityListType = new TypeToken<List<Activity>>(){}.getType();
        return modelMapper.map(activityRecordList, activityListType);
    }

    public int getActivitiesCount(User user) {
        if (user == null) {
            return 0;
        }

        return activityRepository.getActivitiesCount(user.getEmail());
    }
}
