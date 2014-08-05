package org.collaborative.cycling.services;

import org.collaborative.cycling.User;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email) {
        UserRecord userRecord = userRepository.findOne(email);
        if (userRecord == null) {
            userRecord = new UserRecord(email);
            userRepository.save(userRecord);
        }

        return modelMapper.map(userRecord, User.class);
    }
}
