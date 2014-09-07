package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.*;
import org.collaborative.cycling.services.ActivityService;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

//    TODO: update path and params
    @POST
    @Path(MAPPING_VERSION + "solo/activity/{distance}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean saveSoloActivity(@PathParam("distance") String distance,
                                 String coordinates,
                                 @Context HttpServletRequest request) {
        logger.debug("save solo activity -- distance:{}, coordinates:{}", distance, coordinates);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        Activity activity = new Activity();
        activity.setStartDate(new Date());
        Activity savedActivity = activityService.saveActivity(user, activity);
        return userActivityService.saveJoinedUser(user, savedActivity.getId(), ProgressStatus.FINISHED, JoinedStatus.MINE, coordinates);
    }

    @POST
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Activity saveActivity(Activity activity, @Context HttpServletRequest request) {
        logger.debug("save activity -- activity:{}", activity);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.saveActivity(user, activity);
    }

//    TODO: limit the page size
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
    @Path(MAPPING_VERSION + "summary")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ActivitySummary> getActivitiesSummary(@QueryParam("pageNumber") int pageNumber,
                                                      @QueryParam("pageSize") int pageSize,
                                                      @Context HttpServletRequest request) {
        logger.debug("get activities summary");

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.getActivitiesSummary(user, pageNumber, pageSize);
    }

    @GET
    @Path(MAPPING_VERSION + "joined")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JoinedActivity> getJoinedActivities(@Context HttpServletRequest request) {
        logger.debug("get joined activities");

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.getJoinedActivities(user);
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
    @Path(MAPPING_VERSION + "join")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JoinRequest joinActivity(long activityId, @Context HttpServletRequest request) {
        logger.debug("join activity -- activityId:{}", activityId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return userActivityService.joinActivity(user, activityId);
    }

    @GET
    @Path(MAPPING_VERSION + "search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ActivitySearchResult> searchActivitiesToJoin(@QueryParam("query") String query,
                                                        @Context HttpServletRequest request) {
        logger.debug("search activities to join -- query:{}", query);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return activityService.searchActivitiesToJoin(user, query);
    }
}
