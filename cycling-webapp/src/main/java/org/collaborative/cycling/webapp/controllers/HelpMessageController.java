package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.*;
import org.collaborative.cycling.services.ActivityService;
import org.collaborative.cycling.services.GroupService;
import org.collaborative.cycling.services.UserMessageService;
import org.collaborative.cycling.webapp.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path(HelpMessageController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelpMessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MAPPING = "/message/help";

    private final UserMessageService userMessageService;
    private final GroupService groupService;
    private final ActivityService activityService;

    @Autowired
    public HelpMessageController(UserMessageService userMessageService, GroupService groupService, ActivityService activityService) {
        this.userMessageService = userMessageService;
        this.groupService = groupService;
        this.activityService = activityService;
    }

    @GET
    @Path("/activity/{activityId}")
    public Response getMessages(@PathParam("activityId") Long activityId,
                                @Context HttpServletRequest request) {
        if (activityId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));

        List<UserMessage> messages = userMessageService.getMessages(currentUser.getId());
        if (messages == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<HelpMessage> helpMessages = new ArrayList<>(messages.size());
        for (UserMessage userMessage : messages) {
            Coordinates lastLocation = activityService.getUserLocation(userMessage.getSender().getId(), activityId);
            HelpMessage helpMessage = new HelpMessage(userMessage.getMessage(), userMessage.getSender(), lastLocation);
            helpMessages.add(helpMessage);
        }

        return Response.status(Response.Status.OK).entity(helpMessages).build();
    }

    @POST
    @Path("/activity/{activityId}")
    public Response sendMessage(@PathParam("activityId") Long activityId,
                                boolean nearby,
                                boolean group,
                                String text,
                                @Context HttpServletRequest request) {
        if (activityId == null || text == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));

        List<User> nearbyUsers = activityService.getNearbyUsers(currentUser.getId(), activityId);

        List<User> groupUsers = null;
        Group userGroup = activityService.getUserGroup(currentUser.getId(), activityId);
        if (userGroup != null) {
            groupUsers = groupService.getUsers(userGroup.getId());
        }

        if (nearby && nearbyUsers != null) {
            userMessageService.sendMessage(nearbyUsers, text, currentUser.getId());
            if (groupUsers != null) {
                groupUsers.removeAll(nearbyUsers);
            }
        }

        if (group && groupUsers != null) {
            userMessageService.sendMessage(groupUsers, text, currentUser.getId());
        }

        return Response.status(Response.Status.OK).build();
    }
}
