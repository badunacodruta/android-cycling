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

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
            userRecord = new UserRecord(user.getEmail(), user.getImageUrl(), now, now);
        } else {
            userRecord.setImageUrl(user.getImageUrl());
            userRecord.setUpdatedDate(now);
        }

        userRecord = userRepository.save(userRecord);
        return modelMapper.map(userRecord, User.class);
    }
}
