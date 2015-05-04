package org.collaborative.cycling.services;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ActivityService activityService;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, ActivityService activityService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.activityService = activityService;
    }

    public User login(User user) {
        if (user == null || user.getEmail() == null) {
            return null;
        }

        Long id = user.getId();
        String email = user.getEmail();
        Date now = new Date();
        UserRecord userRecord = null;

        if (id != null) {
            userRecord = userRepository.findOne(id);
        }

        if (userRecord == null) {
            userRecord = userRepository.findByEmail(email);
        }

        if (userRecord == null) {
            userRecord = new UserRecord(user.getEmail(), user.getImageUrl(),user.getFirstName(), user.getLastName(), now, now);
            userRecord = userRepository.save(userRecord);

            //TODO this is hardcoded, should be removed after prima evadare
            activityService.addUserToDefaultActivity(userRecord, null);
        } else {
            userRecord.setImageUrl(user.getImageUrl());
            userRecord.setUpdatedDate(now);
            userRecord = userRepository.save(userRecord);
        }

        return modelMapper.map(userRecord, User.class);
    }
}
