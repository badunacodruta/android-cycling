package org.collaborative.cycling.webapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.collaborative.cycling.Utilities;
import org.collaborative.cycling.models.*;
import org.collaborative.cycling.services.ActivityService;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Path(TrackingController.MAPPING)
public class TrackingController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TrackingController.class);

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private ActivityService activityService;

    public static final String MAPPING = "/tracking";
    public static final String MAPPING_VERSION = "/";

    public TrackingController() {
    }

//    TODO: update path and params
    @POST
    @Path(MAPPING_VERSION + "position/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updatePosition(@PathParam("activityId") long activityId,
                                  Coordinates coordinates,
                                  @Context HttpServletRequest request) {
        logger.debug("update position -- activityId:{}, position:{}", activityId, coordinates);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        if (coordinates.getDate() == null) {
            coordinates.setDate(new Date());
        }
        return userActivityService.saveJoinedUser(user, activityId, null, null, coordinates);
    }

//    TODO: maybe add pagination for the users
    @GET
    @Path(MAPPING_VERSION + "users")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JoinedUser> getUsersForActivity(@QueryParam("activityId") long activityId,
                                   @Context HttpServletRequest request) {
        logger.debug("get users for activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        Activity activity = activityService.getActivity(user, activityId);
        List<JoinedUser> joinedUsers = activity.getJoinedUsers();

        //remove current user
        for (JoinedUser joinedUser : joinedUsers) {
            if (joinedUser.getUser().getEmail().equals(user.getEmail())) {
                joinedUsers.remove(joinedUser);
                return joinedUsers;
            }
        }

        return joinedUsers;
    }
}

