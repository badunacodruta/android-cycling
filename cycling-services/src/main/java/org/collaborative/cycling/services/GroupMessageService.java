package org.collaborative.cycling.services;

import org.collaborative.cycling.models.GroupMessage;
import org.collaborative.cycling.records.GroupMessageRecord;
import org.collaborative.cycling.records.GroupRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.GroupMessageRepository;
import org.collaborative.cycling.repositories.GroupRepository;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class GroupMessageService {
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupMessageRepository groupMessageRepository;

    //TODO add pagination

    @Autowired
    public GroupMessageService(ModelMapper modelMapper,
                               GroupRepository groupRepository, UserRepository userRepository,
                               GroupMessageRepository groupMessageRepository) {
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupMessageRepository = groupMessageRepository;

    }

    @Transactional
    public List<GroupMessage> getMessages(Long groupId, Integer size) {
        if (groupId == null) {
            return null;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return null;
        }

        List<GroupMessageRecord> groupMessageRecords = groupRecord.getReceivedMessages();

        List<GroupMessageRecord> groupMessageRecordsToReturn;
        if (size == null || size < 0) {
            groupMessageRecordsToReturn = groupMessageRecords;
        } else {
            groupMessageRecordsToReturn = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                groupMessageRecordsToReturn.add(groupMessageRecords.get(i));
            }
        }

        Collections.reverse(groupMessageRecordsToReturn);

        Type groupMessageListType = new TypeToken<List<GroupMessage>>() {
        }.getType();
        return modelMapper.map(groupMessageRecordsToReturn, groupMessageListType);
    }

    public GroupMessage addMessage(Long groupId, String message, Long senderId) {
        if (groupId == null || message == null || senderId == null) {
            return null;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(senderId);
        if (userRecord == null) {
            return null;
        }

        GroupMessageRecord groupMessageRecord = new GroupMessageRecord(
                message, new Date(), userRecord, groupRecord);
        groupMessageRecord = groupMessageRepository.save(groupMessageRecord);

        return modelMapper.map(groupMessageRecord, GroupMessage.class);
    }
}
