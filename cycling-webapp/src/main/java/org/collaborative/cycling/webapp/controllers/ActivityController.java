package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.Activity;
import org.collaborative.cycling.models.ActivityInfo;
import org.collaborative.cycling.models.JoinRequest;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.ActivityService;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path(ActivityController.MAPPING)
public class ActivityController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserActivityService userActivityService;

    public static final String MAPPING = "/activities";
    public static final String MAPPING_VERSION = "/";

    public ActivityController() {
    }

    @POST
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Activity createActivity(Activity activity, @Context HttpServletRequest request) {
        logger.debug("create activity -- activity:{}", activity);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.saveActivity(user, activity);
    }

    @GET
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Activity> getActivities(@QueryParam("pageNumber") int pageNumber,
                                        @QueryParam("pageSize") int pageSize,
                                        @Context HttpServletRequest request) {
        logger.debug("get activities");

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.getActivities(user, pageNumber, pageSize);
    }

    @GET
    @Path(MAPPING_VERSION + "info")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ActivityInfo> getActivitiesInfo(@QueryParam("pageNumber") int pageNumber,
                                                @QueryParam("pageSize") int pageSize,
                                                @Context HttpServletRequest request) {
        logger.debug("get activities info");

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.getActivitiesInfo(user, pageNumber, pageSize);
    }

    @GET
    @Path(MAPPING_VERSION + "count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int getActivitiesCount(@Context HttpServletRequest request) {
        logger.debug("get activities count");

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.getActivitiesCount(user);
    }

    @GET
    @Path(MAPPING_VERSION + "activity")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Activity getActivity(@QueryParam("id") long activityId,
                                @Context HttpServletRequest request) {
        logger.debug("get activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.getActivity(user, activityId);
    }

    @DELETE
    @Path(MAPPING_VERSION + "activity")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean deleteActivity(@QueryParam("id") long activityId,
                                  @Context HttpServletRequest request) {
        logger.debug("delete activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.deleteActivity(user, activityId);
    }

    @POST
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JoinRequest joinActivity(long activityId, @Context HttpServletRequest request) {
        logger.debug("join activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return userActivityService.joinActivity(user, activityId);
    }
}
