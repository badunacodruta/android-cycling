package org.collaborative.cycling.services;

import org.collaborative.cycling.models.Activity;
import org.collaborative.cycling.records.ActivityRecord;
import org.collaborative.cycling.repositories.ActivityRepository;
import org.modelmapper.ModelMapper;

public class ActivityService {
    private ActivityRepository activityRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public ActivityService(ActivityRepository userRepository) {
        this.activityRepository = userRepository;
    }

    public Activity saveActivity(Activity activity) {
        ActivityRecord activityRecord = modelMapper.map(activity, ActivityRecord.class);
        activityRecord = activityRepository.save(activityRecord);
        return modelMapper.map(activityRecord, Activity.class);
    }
}
