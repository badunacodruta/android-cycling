package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.Activity;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.ActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

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
    public Response create(Activity activity, @Context HttpServletRequest request) {
        logger.debug("create -- activity:{}", activity);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        activity.setOwner(user);
        activity.setCreatedDate(new Date());

        activityService.saveActivity(activity);

        return Response.status(Response.Status.OK).build();
    }
}
