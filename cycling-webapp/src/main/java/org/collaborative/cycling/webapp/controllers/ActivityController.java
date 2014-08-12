package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.Activity;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.ActivityService;
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

        Activity activityResponse = activityService.saveActivity(user, activity);

        return activityResponse;
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
    @Path(MAPPING_VERSION + "count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int getActivitiesCount(@Context HttpServletRequest request) {
        logger.debug("get activities count");

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.getActivitiesCount(user);
    }


}
