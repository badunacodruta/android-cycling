package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.GroupInviteUserRequest;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.models.UserJoinGroupRequest;
import org.collaborative.cycling.models.UserResponseToRequests;
import org.collaborative.cycling.services.GroupRequestsService;
import org.collaborative.cycling.services.GroupService;
import org.collaborative.cycling.webapp.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(GroupRequestsController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupRequestsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MAPPING = "/request/group";

    private final GroupRequestsService groupRequestsService;
    private final GroupService groupService;

    @Autowired
    public GroupRequestsController(GroupRequestsService groupRequestsService, GroupService groupService) {
        this.groupRequestsService = groupRequestsService;
        this.groupService = groupService;
    }

    @POST
    @Path("/{groupId}")
    public Response userJoinGroup(@PathParam("groupId") Long groupId,
                                  @Context HttpServletRequest request) {
        if (groupId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        if (groupService.hasUser(groupId, currentUser.getId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        UserJoinGroupRequest userJoinGroupRequest = groupRequestsService.userJoinGroup(currentUser.getId(), groupId);

        if (userJoinGroupRequest == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(userJoinGroupRequest).build();
    }

    @POST
    @Path("/{groupId}/invitation")
    public Response groupInviteUser(@PathParam("groupId") Long groupId,
                                    String email,
                                    @Context HttpServletRequest request) {
        if (groupId == null || email == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        if (!groupService.hasUser(groupId, currentUser.getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        GroupInviteUserRequest groupInviteUserRequest = groupRequestsService.groupInviteUser(groupId, email, currentUser.getId());

        if (groupInviteUserRequest == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(groupInviteUserRequest).build();
    }


    @GET
    @Path("/{groupId}")
    public Response getUserJoinGroupRequests(@PathParam("groupId") Long groupId,
                                             @Context HttpServletRequest request) {
        if (groupId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        if (!groupService.hasUser(groupId, currentUser.getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<UserJoinGroupRequest> userJoinGroupRequestList = groupRequestsService.getUserJoinGroupRequests(groupId);

        if (userJoinGroupRequestList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(userJoinGroupRequestList).build();
    }

    @GET
    @Path("/invitations")
    public Response getInvitationsForUser(@Context HttpServletRequest request) {
        User currentUser = Utils.getUser(request.getSession(false));

        List<GroupInviteUserRequest> groupInviteUserRequestList = groupRequestsService.getGroupInviteUserRequests(currentUser.getId());

        if (groupInviteUserRequestList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(groupInviteUserRequestList).build();
    }


    @POST
    @Path("/requests/{requestId}")
    public Response updateJoinRequest(@PathParam("requestId") Long requestId,
                                      UserResponseToRequests userResponseToRequests,
                                      @Context HttpServletRequest request) {
        if (requestId == null || userResponseToRequests == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        if (groupRequestsService.updateUserJoinRequest(requestId, userResponseToRequests, currentUser.getId())) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/invitations/{invitationId}")
    public Response updateInvitation(@PathParam("invitationId") Long invitationId,
                                     UserResponseToRequests userResponseToRequests,
                                     @Context HttpServletRequest request) {
        if (invitationId == null || userResponseToRequests == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        if (groupRequestsService.updateUserInvitation(invitationId, userResponseToRequests, currentUser.getId())) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @DELETE
    @Path("/{groupId}")
    public Response leaveGroup(@PathParam("groupId") Long groupId,
                               @Context HttpServletRequest request) {
        if (groupId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        if (groupRequestsService.removeUserFromGroup(currentUser.getId(), groupId)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
