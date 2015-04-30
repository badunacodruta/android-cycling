package org.collaborative.cycling.services;

import org.collaborative.cycling.models.Group;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.records.GroupRecord;
import org.collaborative.cycling.records.UserGroupRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.GroupRepository;
import org.collaborative.cycling.repositories.UserGroupRepository;
import org.collaborative.cycling.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GroupService {
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    //TODO add pagination

    @Autowired
    public GroupService(ModelMapper modelMapper,
                        GroupRepository groupRepository, UserRepository userRepository, UserGroupRepository userGroupRepository) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public boolean exists(String groupName) {
        if (groupName == null) {
            return false;
        }
        return groupRepository.findByName(groupName) != null;
    }

    @Transactional
    public Group save(Group group, Long userId) {
        if (group == null || group.getName() == null || userId == null) {
            return null;
        }

        Long id = group.getId();
        Date now = new Date();
        GroupRecord groupRecord;

        if (id == null) {
            //TODO investigate if it's possible to avoid this extra call to db or change the records to store only the ids
            UserRecord userRecord = userRepository.findOne(userId);
            if (userRecord == null) {
                return null;
            }

            groupRecord = new GroupRecord(group.getName(), now, now, userRecord);
        } else {
            groupRecord = groupRepository.findOne(id);

            if (groupRecord == null) {
                return null;
            }

            groupRecord.setName(group.getName());
            groupRecord.setUpdatedDate(now);
        }

        groupRecord = groupRepository.save(groupRecord);

        addUserToGroup(userId, groupRecord.getId());

        return modelMapper.map(groupRecord, Group.class);
    }

    public List<Group> getFiltered(String filter) {
        List<GroupRecord> groupRecords;
        if (filter == null) {
            groupRecords = groupRepository.findAllByOrderByNameAsc();
        } else {
            groupRecords = groupRepository.findByNameContainingOrderByNameAsc(filter);
        }

        Type groupListType = new TypeToken<List<Group>>() {
        }.getType();
        return modelMapper.map(groupRecords, groupListType);
    }

    @Transactional
    public List<Group> getForUser(Long userId) {
        if (userId == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(userId);
        if (userRecord == null) {
            return null;
        }

        //TODO pagination for oneToMany fields
        List<UserGroupRecord> userGroupRecords = userRecord.getGroups();
        List<Group> groups = new ArrayList<>(userGroupRecords.size());
        for (UserGroupRecord userGroupRecord : userGroupRecords) {
            Group group = modelMapper.map(userGroupRecord.getGroup(), Group.class);
            groups.add(group);
        }

        return groups;
    }

    @Transactional
    public List<User> getUsers(Long groupId) {
        if (groupId == null) {
            return null;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return null;
        }

        List<UserGroupRecord> userGroupRecords = groupRecord.getUsers();
        List<User> users = new ArrayList<>(userGroupRecords.size());
        for (UserGroupRecord userGroupRecord : userGroupRecords) {
            User user = modelMapper.map(userGroupRecord.getUser(), User.class);
            users.add(user);
        }

        return users;
    }

    @Transactional
    public boolean hasUser(Long groupId, Long userId) {
        if (groupId == null || userId == null) {
            return false;
        }

        List<User> users = getUsers(groupId);

        if (users == null) {
            return false;
        }

        for (User user : users) {
            if (user.getId() == userId) {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public boolean addUserToGroup(Long userId, Long groupId) {
        if (userId == null || groupId == null) {
            return false;
        }

        if (hasUser(groupId, userId)) {
            return true;
        }

        UserRecord userRecord = userRepository.findOne(userId);
        if (userRecord == null) {
            return false;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return false;
        }

        Date now = new Date();
        UserGroupRecord userGroupRecord = new UserGroupRecord(
                now, now, userRecord, groupRecord);

        userGroupRepository.save(userGroupRecord);
        return true;
    }
}
