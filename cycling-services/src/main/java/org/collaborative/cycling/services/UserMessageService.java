package org.collaborative.cycling.services;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.records.UserMessageRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.UserMessageRepository;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserMessageService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserMessageRepository userMessageRepository;

    @Autowired
    public UserMessageService(ModelMapper modelMapper,
                              UserRepository userRepository,
                              UserMessageRepository userMessageRepository) {
        this.modelMapper = modelMapper;
        this.userMessageRepository = userMessageRepository;
        this.userRepository = userRepository;
    }

    public void sendMessage(List<User> users, String message, Long senderId) {
        if (users == null || message == null || senderId == null) {
            return;
        }

        UserRecord sender = userRepository.findOne(senderId);
        if (sender == null) {
            return;
        }

        for (User user : users) {
            UserRecord userRecord = userRepository.findOne(user.getId());
            if (userRecord == null) {
                continue;
            }

            UserMessageRecord userMessageRecord = new UserMessageRecord(
                    message.getBytes(), new Date(), sender, userRecord);
            userMessageRepository.save(userMessageRecord);
        }
    }
}
