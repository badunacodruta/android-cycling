package org.collaborative.cycling.services;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;

public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(User user) {
        UserRecord userRecord = userRepository.findOne(user.getEmail());
        if (userRecord == null) {
            userRecord = new UserRecord(user.getEmail(), user.getImageUrl());
        } else {
            userRecord.setImageUrl(user.getImageUrl());
        }

        userRepository.save(userRecord);
        return modelMapper.map(userRecord, User.class);
    }
}
