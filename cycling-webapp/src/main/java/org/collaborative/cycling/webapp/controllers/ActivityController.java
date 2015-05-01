package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.ActivityUser;
import org.collaborative.cycling.models.Coordinates;
import org.collaborative.cycling.models.Group;
import org.collaborative.cycling.models.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path(ActivityController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivityController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MAPPING = "/activity";

    private final UserMessageService userMessageService;
    private final GroupService groupService;
    private final ActivityService activityService;

    @Autowired
    public ActivityController(UserMessageService userMessageService, GroupService groupService, ActivityService activityService) {
        this.userMessageService = userMessageService;
        this.groupService = groupService;
        this.activityService = activityService;
    }

    @GET
    @Path("/{activityId}/users")
    public Response getUsers(@PathParam("activityId") Long activityId,
                             @QueryParam("group") boolean getFromGroup,
                             @QueryParam("nearby") boolean getNearby,
                             @Context HttpServletRequest request) {
        if (activityId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        Set<ActivityUser> activityUserSet = new HashSet<>();

        if (getFromGroup) {
            List<User> groupUsers = null;
            Group userGroup = activityService.getUserGroup(currentUser.getId(), activityId);
            if (userGroup != null) {
                groupUsers = groupService.getUsers(userGroup.getId());
            }

            if (groupUsers != null) {
                for (User user : groupUsers) {
                    Coordinates userCoordinates = activityService.getUserLocation(user.getId(), activityId);
                    ActivityUser activityUser = new ActivityUser(user, userCoordinates, true);
                    activityUserSet.add(activityUser);
                }
            }
        }

        if (getNearby) {
            List<User> nearbyUsers = activityService.getNearbyUsers(currentUser.getId(), activityId);
            if (nearbyUsers != null) {
                for (User user : nearbyUsers) {
                    Coordinates userCoordinates = activityService.getUserLocation(user.getId(), activityId);
                    ActivityUser activityUser = new ActivityUser(user, userCoordinates, false);
                    activityUserSet.add(activityUser);
                }
            }
        }

        return Response.status(Response.Status.OK).entity(activityUserSet).build();
    }

    @POST
    @Path("/{activityId}")
    public Response updateUserLocation(@PathParam("activityId") Long activityId,
                                       Coordinates coordinates,
                                       @Context HttpServletRequest request) {
        if (activityId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));

        if (activityService.updateUserLocation(currentUser.getId(), activityId, coordinates)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
