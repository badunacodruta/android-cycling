package org.collaborative.cycling.webapp.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.collaborative.cycling.models.Activity;
import org.collaborative.cycling.models.Coordinates;
import org.collaborative.cycling.models.JoinedStatus;
import org.collaborative.cycling.models.JoinedUser;
import org.collaborative.cycling.models.ProgressStatus;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.ActivityService;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


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
    public List<JoinedUser> updatePosition(@PathParam("activityId") long activityId,
                                           Coordinates coordinates,
                                           @Context HttpServletRequest request) throws IOException {
        logger.debug("update position -- activityId:{}, position:{}", activityId, coordinates);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        ProgressStatus progressStatus = userActivityService.getProgressStatusForUser(user, activityId);
        if (progressStatus != ProgressStatus.FINISHED) {
            if (coordinates.getDate() == null) {
                coordinates.setDate(new Date());
            }
            userActivityService.saveJoinedUser(user, activityId, null, null, coordinates);
        }

        return getUsersForActivityAndUser(activityId, user);
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

        return getUsersForActivityAndUser(activityId, user);
    }

    private List<JoinedUser> getUsersForActivityAndUser(long activityId, User user) {
        //TODO: it returns NULL
        Activity activity = activityService.getActivity(user, activityId);
        List<JoinedUser> joinedUsers = activity.getJoinedUsers();

        return joinedUsers;
    }

    @GET
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void start(long activityId,
                      @Context HttpServletRequest request) {
        logger.debug("start activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        if (userActivityService.isJoinedUser(user, activityId) &&
                userActivityService.getProgressStatusForUser(user, activityId) == ProgressStatus.NOT_STARTED &&
                activityService.isStartable(activityId)) {
            userActivityService.saveJoinedUser(user, activityId, ProgressStatus.ACTIVE, null);
        }
    }

    @GET
    @Path("/finish")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void finish(long activityId,
                       @Context HttpServletRequest request) {
        logger.debug("finish activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        ProgressStatus progressStatus = userActivityService.getProgressStatusForUser(user, activityId);
        if (userActivityService.isJoinedUser(user, activityId) &&
                (progressStatus == ProgressStatus.ACTIVE || progressStatus == ProgressStatus.PAUSED)) {
            userActivityService.saveJoinedUser(user, activityId, ProgressStatus.FINISHED, null);
        }
    }

    @GET
    @Path("/pause")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void pause(long activityId,
                      @Context HttpServletRequest request) {
        logger.debug("pause activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        if (userActivityService.isJoinedUser(user, activityId) &&
                userActivityService.getProgressStatusForUser(user, activityId) == ProgressStatus.ACTIVE) {
            userActivityService.saveJoinedUser(user, activityId, ProgressStatus.PAUSED, null);
        }
    }

    @GET
    @Path("/resume")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void resume(long activityId,
                       @Context HttpServletRequest request) {
        logger.debug("resume activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        if (userActivityService.isJoinedUser(user, activityId) &&
                userActivityService.getProgressStatusForUser(user, activityId) == ProgressStatus.PAUSED) {
            userActivityService.saveJoinedUser(user, activityId, ProgressStatus.ACTIVE, null);
        }
    }
}

