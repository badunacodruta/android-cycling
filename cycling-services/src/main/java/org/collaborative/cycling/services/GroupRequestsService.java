package org.collaborative.cycling.services;

import org.collaborative.cycling.models.GroupInviteUserRequest;
import org.collaborative.cycling.models.UserJoinGroupRequest;
import org.collaborative.cycling.models.UserResponseToRequests;
import org.collaborative.cycling.records.GroupRecord;
import org.collaborative.cycling.records.UserGroupRecord;
import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.records.requests.GroupInviteUserRequestRecord;
import org.collaborative.cycling.records.requests.UserJoinGroupRequestRecord;
import org.collaborative.cycling.repositories.GroupRepository;
import org.collaborative.cycling.repositories.UserGroupRepository;
import org.collaborative.cycling.repositories.UserRepository;
import org.collaborative.cycling.repositories.requests.GroupInviteUserRequestRepository;
import org.collaborative.cycling.repositories.requests.UserJoinGroupRequestRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Service
public class GroupRequestsService {
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserJoinGroupRequestRepository userJoinGroupRequestRepository;
    private final GroupInviteUserRequestRepository groupInviteUserRequestRepository;
    private final GroupService groupService;
    private final ActivityService activityService;

    //TODO add pagination

    @Autowired
    public GroupRequestsService(ModelMapper modelMapper,
                                GroupRepository groupRepository, UserRepository userRepository,
                                UserGroupRepository userGroupRepository,
                                UserJoinGroupRequestRepository userJoinGroupRequestRepository,
                                GroupInviteUserRequestRepository groupInviteUserRequestRepository, GroupService groupService, ActivityService activityService) {
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.userJoinGroupRequestRepository = userJoinGroupRequestRepository;
        this.groupInviteUserRequestRepository = groupInviteUserRequestRepository;
        this.groupService = groupService;
        this.activityService = activityService;
    }

    public UserJoinGroupRequest userJoinGroup(Long userId, Long groupId) {
        if (userId == null || groupId == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(userId);
        if (userRecord == null) {
            return null;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return null;
        }

        UserJoinGroupRequestRecord userJoinGroupRequestRecord = new UserJoinGroupRequestRecord(
                new Date(), userRecord, groupRecord);
        userJoinGroupRequestRecord = userJoinGroupRequestRepository.save(userJoinGroupRequestRecord);

        return modelMapper.map(userJoinGroupRequestRecord, UserJoinGroupRequest.class);
    }

    public GroupInviteUserRequest groupInviteUser(Long groupId, String userEmail, Long senderId) {
        if (groupId == null || userEmail == null || senderId == null) {
            return null;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findByEmail(userEmail);
        if (userRecord == null) {
            return null;
        }

        UserRecord senderRecord = userRepository.findOne(senderId);
        if (senderRecord == null) {
            return null;
        }

        GroupInviteUserRequestRecord groupInviteUserRequestRecord = new GroupInviteUserRequestRecord(
                new Date(), userRecord, groupRecord, senderRecord);
        groupInviteUserRequestRecord = groupInviteUserRequestRepository.save(groupInviteUserRequestRecord);

        return modelMapper.map(groupInviteUserRequestRecord, GroupInviteUserRequest.class);
    }


    @Transactional
    public List<UserJoinGroupRequest> getUserJoinGroupRequests(Long groupId) {
        if (groupId == null) {
            return null;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return null;
        }

        List<UserJoinGroupRequestRecord> userJoinGroupRequests = groupRecord.getUserJoinGroupRequests();

        Type userJoinGroupRequestListType = new TypeToken<List<UserJoinGroupRequest>>() {
        }.getType();
        return modelMapper.map(userJoinGroupRequests, userJoinGroupRequestListType);
    }

    @Transactional
    public List<GroupInviteUserRequest> getGroupInviteUserRequests(Long userId) {
        if (userId == null) {
            return null;
        }

        UserRecord userRecord = userRepository.findOne(userId);
        if (userRecord == null) {
            return null;
        }

        List<GroupInviteUserRequestRecord> groupInviteUserRequestRecords = userRecord.getReceivedGroupInvitations();

        Type groupInviteUserRequestListType = new TypeToken<List<GroupInviteUserRequest>>() {
        }.getType();
        return modelMapper.map(groupInviteUserRequestRecords, groupInviteUserRequestListType);
    }


    @Transactional
    public boolean updateUserJoinRequest(Long requestId, UserResponseToRequests userResponseToRequests, Long currentUserId) {
        if (requestId == null || userResponseToRequests == null || currentUserId == null) {
            return false;
        }

        UserJoinGroupRequestRecord userJoinGroupRequestRecord = userJoinGroupRequestRepository.findOne(requestId);
        if (userJoinGroupRequestRecord == null) {
            return false;
        }

        //TODO consider moving this check out of here (in the controller)
        //check the current user is part of the group (has the right to update the request)
        if (!groupService.hasUser(userJoinGroupRequestRecord.getGroup().getId(), currentUserId)) {
            return false;
        }

        if (userResponseToRequests == UserResponseToRequests.DECLINE) {
            userJoinGroupRequestRepository.delete(requestId);
        } else {
            Date now = new Date();
            UserGroupRecord userGroupRecord = new UserGroupRecord(now, now,
                    userJoinGroupRequestRecord.getUser(), userJoinGroupRequestRecord.getGroup());
            userGroupRepository.save(userGroupRecord);

            //TODO this is hardcoded, should be removed after prima evadare
            activityService.addUserToDefaultActivity(userJoinGroupRequestRecord.getUser(), userJoinGroupRequestRecord.getGroup());

            userJoinGroupRequestRepository.delete(requestId);
        }

        return true;
    }

    public boolean updateUserInvitation(Long invitationId, UserResponseToRequests userResponseToRequests, Long currentUserId) {
        if (invitationId == null || userResponseToRequests == null || currentUserId == null) {
            return false;
        }

        GroupInviteUserRequestRecord groupInviteUserRequestRecord = groupInviteUserRequestRepository.findOne(invitationId);
        if (groupInviteUserRequestRecord == null) {
            return false;
        }

        //TODO consider moving this check out of here (in the controller)
        //check the current user is the one invited (has the right to update the request)
        if (!groupInviteUserRequestRecord.getUser().getId().equals(currentUserId)) {
            return false;
        }

        if (userResponseToRequests == UserResponseToRequests.DECLINE) {
            groupInviteUserRequestRepository.delete(invitationId);
        } else {
            Date now = new Date();
            UserGroupRecord userGroupRecord = new UserGroupRecord(now, now,
                    groupInviteUserRequestRecord.getUser(), groupInviteUserRequestRecord.getGroup());
            userGroupRepository.save(userGroupRecord);

            //TODO this is hardcoded, should be removed after prima evadare
            activityService.addUserToDefaultActivity(groupInviteUserRequestRecord.getUser(), groupInviteUserRequestRecord.getGroup());

            groupInviteUserRequestRepository.delete(invitationId);
        }

        return true;
    }


    @Transactional
    public boolean removeUserFromGroup(Long userId, Long groupId) {
        if (userId == null || groupId == null) {
            return false;
        }

        UserRecord userRecord = userRepository.findOne(userId);
        if (userRecord == null) {
            return false;
        }

        GroupRecord groupRecord = groupRepository.findOne(groupId);
        if (groupRecord == null) {
            return false;
        }

        List<UserGroupRecord> userGroupRecords = userRecord.getGroups();
        for (UserGroupRecord userGroupRecord : userGroupRecords) {
            if (userGroupRecord.getGroup().getId() == groupId) {
                userGroupRepository.delete(userGroupRecord.getId());
                return true;
            }
        }

        return false;
    }
}
